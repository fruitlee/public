package com.haohiyo.smallfiles.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HbaseFactoryBean implements FactoryBean<Configuration>, InitializingBean {

    private Configuration configuration;

    @Override
    public Configuration getObject() throws Exception {
        return configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
    
    @Value("${hbase.zookeeper.quorum}")
    private String quorum;
    @Value("${hbase.client.keyvalue.maxsize}")
    private String maxsize;

    @Override
    public void afterPropertiesSet() throws Exception {
    	
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", quorum);
        configuration.set("hbase.client.keyvalue.maxsize", maxsize);

    }
}
