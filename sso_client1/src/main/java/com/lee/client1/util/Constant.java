package com.lee.client1.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.validation.annotation.Validated;

/**
 * Created with : IntelliJ IDEA
 * User : KingIsHappy
 * Date : 2018/10/7
 * Time : 22:18
 * Description : 系统常量类
 */
@Configuration
@PropertySource("classpath:sso.properties")
public class Constant {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${server-url-prefix}")
    private String SERVER_HOST;
    @Value("${client-host-url}")
    private String CLIENT_HOST;

    public String getSERVER_HOST() {
        return SERVER_HOST;
    }

    public String getCLIENT_HOST() {
        return CLIENT_HOST;
    }
}

