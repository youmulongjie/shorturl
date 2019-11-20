package com.andy.demo.shorturl.service.impl;

import com.andy.demo.shorturl.config.SinaOpenConfig;
import com.andy.demo.shorturl.service.IShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 短地址接口实现类（依赖 新浪API）
 */
@Slf4j
@Component
public class SinaShortUrlImpl implements IShortUrl {
    @Autowired
    private SinaOpenConfig sinaOpenConfig;

    /**
     * 转 短链接
     *
     * @param sourceUrl 原链接文本
     * @return 生成的短链接
     */
    @Override
    public String shortUrl(String sourceUrl) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiUrl(sourceUrl));
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity);

                return result;
            } else {
                throw new RuntimeException("API 接口返回状态码不正确：" + statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("API 接口调用出错！");
        }
    }

    /**
     * 封装 调用的API地址
     *
     * @param sourceUrl 原链接文本
     * @return 调用的API地址
     */
    private String apiUrl(String sourceUrl) {
        StringBuffer sb = new StringBuffer(16);
        sb.append(sinaOpenConfig.getApiUrl());
        sb.append("?url_long=");
        sb.append(sourceUrl);

        sb.append("&access_token=");
        sb.append(sinaOpenConfig.getAccessToken());

        return sb.toString();
    }

}
