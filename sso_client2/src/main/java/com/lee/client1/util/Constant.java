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
    @Value("${sso-token-verify}")
    private String SSO_TOKEN_VERIFY;

    @Value("${client-logout-url}")
    private String CLIENT_LOGOUT_URL;

    @Value("${sso-logout-url}")
    private String SSO_LOGOUT_URL;

    public String getSERVER_HOST() {
        return SERVER_HOST;
    }

    public String getCLIENT_HOST() {
        return CLIENT_HOST;
    }

    public String getSSO_TOKEN_VERIFY() {
        return SSO_TOKEN_VERIFY;
    }

    public String getCLIENT_LOGOUT_URL() {
        return CLIENT_LOGOUT_URL;
    }

    public String getSSO_LOGOUT_URL() {
        return SSO_LOGOUT_URL;
    }
}

