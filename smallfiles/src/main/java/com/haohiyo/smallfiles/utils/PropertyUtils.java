/**
 * 分离的项目配置文件读取工具
 */
package com.haohiyo.smallfiles.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
/**
 * 配置分离。固定路径。
 * @author zhenghongHYE
 *
 */
public class PropertyUtils {
	
	private static Properties properties = new Properties();
	static {
		FileInputStream fis = null;
		try {
			fis = FileUtils.openInputStream(new File("/opt/config/smallfiles/config.properties"));
			properties.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("读取系统配置文件出错" , e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	public static String getProperty(String name, String defaultValue) {
		return properties.getProperty(name, defaultValue);
	}

	public static String getProperty(String name) {
		return properties.getProperty(name);
	}

}
