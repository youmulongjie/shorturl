package com.andy.demo.shorturl.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 新浪应用短连接API 配置
 */
@Component
public class ShortUrlApiConfig {
    /**
     * api url
     */
    @Getter
    @Value("${api_url}")
    private String apiUrl;

    /**
     * 新浪应用 Token值
     */
    @Getter
    @Value("${acess_token}")
    private String acessToken;
}
