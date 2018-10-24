package com.andy.demo.shorturl.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 新浪应用 Token
 */
@Component
public class SinaConfig {
    /**
     * 新浪应用 转化短接口API
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
