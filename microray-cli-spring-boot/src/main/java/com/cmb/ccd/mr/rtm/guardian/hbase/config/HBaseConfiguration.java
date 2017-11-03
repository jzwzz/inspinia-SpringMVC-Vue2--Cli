package com.cmb.ccd.mr.rtm.guardian.hbase.config;

import com.cmb.ccd.mr.common.hbase.thrift.ThriftClientPool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HBaseConfiguration {

    @ConfigurationProperties(prefix = "hbase.thrift")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    ThriftClientPool thriftClientPool() {
        return new ThriftClientPool();
    }
}
