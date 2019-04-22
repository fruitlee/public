package com.haohiyo.smallfiles.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.haohiyo.smallfiles.common.ApiBaseResponse;
import com.haohiyo.smallfiles.common.ApiCodeEnum;
import com.haohiyo.smallfiles.utils.KeyGenerator;

/**
 * 基本上传/下载接口
 * @author zhenghongHYE
 */
@RestController
@RequestMapping(value = "/basic/{table}")
public class BasicFileController {

	@Autowired
	private Configuration configuration;
	private String columnFamily = "cf";
	private String columnQualifier = "byte";
	
	@Value("${file.maxsize}")
	int fileMaxSize;//in KB

	/**
	 * 文件上传
	 * @param table hbase表名
	 * @param in 文件流
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ApiBaseResponse<JSONObject> upload(@PathVariable() String table, InputStream in) throws IOException {
		
		return doUpload(table, KeyGenerator.genKey(), in);
	}

	/**
	 * 根据key文件下载
	 * @param table hbase表名
	 * @param key upload返回的data.path
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/{key}", method = RequestMethod.GET)
	public void download(@PathVariable() String table, @PathVariable() String key, HttpServletResponse response)
			throws IOException {
		HTable hTable = new HTable(configuration, table);
		Get g = new Get(Bytes.toBytes(key));
		Result r = hTable.get(g);
		byte[] value = r.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(columnQualifier));
		if (ArrayUtils.isNotEmpty(value)) {
			OutputStream outputStream = null;
			try {
				response.setContentType("application/octet-stream");
				response.setStatus(HttpServletResponse.SC_OK);
				outputStream = response.getOutputStream();
				outputStream.write(value);
			} finally {
				IOUtils.closeQuietly(outputStream);
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		IOUtils.closeQuietly(hTable);
	}

	/**
	 * 根据key删除
	 * @param table hbase表名
	 * @param key upload返回的data.path
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete/{key}")
	public ApiBaseResponse<JSONObject> delete(@PathVariable() String table, @PathVariable() String key,
			HttpServletRequest request) throws IOException {

		ApiBaseResponse<JSONObject> res = new ApiBaseResponse<>();

		HTable hTable = new HTable(configuration, table);
		Delete delete = new Delete(Bytes.toBytes(key));
		hTable.delete(delete);
		hTable.close();

		res.setByEnum(ApiCodeEnum.success);
		return res;
	}

	/*
	 * check and do upload process
	 */
	private ApiBaseResponse<JSONObject> doUpload(String table, String key, InputStream in) throws IOException {
		ApiBaseResponse<JSONObject> res = new ApiBaseResponse<>();

		HTable hTable = null;
		try {
			hTable = new HTable(configuration, table);
		} catch (TableNotFoundException e) {
			res.setByEnum(ApiCodeEnum.err_10001);
			res.setMsg(e.getMessage());
			return res;
		}

		byte[] byteArray = null;
		try {
			byteArray = IOUtils.toByteArray(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
		if (ArrayUtils.isEmpty(byteArray)) {
			res.setByEnum(ApiCodeEnum.err_param_40200);
			IOUtils.closeQuietly(hTable);
			return res;
		}
		
		if(byteArray.length/1024 > fileMaxSize) {
			res.setByEnum(ApiCodeEnum.err_param_40300);
			IOUtils.closeQuietly(hTable);
			return res;
		}

		Put put = new Put(Bytes.toBytes(key));
		put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(columnQualifier), byteArray);
		hTable.put(put);

		JSONObject data = new JSONObject();
		data.put("path", key);
		res.setData(data );
		res.setByEnum(ApiCodeEnum.success);
		IOUtils.closeQuietly(hTable);
		return res;

	}
	
}
