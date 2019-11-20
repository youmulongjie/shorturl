package com.andy.demo.shorturl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 新浪开发者平台 账号配置信息
 * Author: Andy.wang
 * Date: 2019/11/20 08:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sinaopen")
public class SinaOpenConfig {
    /**
     * 新浪应用 转化短接口API
     */
    private String apiUrl;

    /**
     * 新浪应用Token
     */
    private String accessToken;
}
