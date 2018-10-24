package com.andy.demo.shorturl.service.impl;

import com.andy.demo.shorturl.bean.ApiResultBean;
import com.andy.demo.shorturl.bean.ShortUrl;
import com.andy.demo.shorturl.bean.SinaConfig;
import com.andy.demo.shorturl.redis.RedisStringUtil;
import com.andy.demo.shorturl.service.IShortUrl;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 短地址接口实现类（依赖 新浪API）
 */
@Component
public class SinaShortUrlImpl implements IShortUrl{
    @Autowired
    private SinaConfig sinaConfig;

    @Autowired
    private RedisStringUtil redisStringUtil;

    /**
     * 转化 短地址
     *
     * @return
     */
    @Override
    public synchronized String shortUrl(String longUrl) {
        if(redisStringUtil.hasKey(longUrl)){
            System.out.println("22");
            return redisStringUtil.get(longUrl).toString();
        }
        System.out.println("11");
        callApi(longUrl);
        return redisStringUtil.get(longUrl).toString();
    }

    /**
     * 获取 API 访问的 url
     * @param longUrl 长地址
     * @return 访问API的URL地址
     */
    private String targetUrl(String longUrl){
        return sinaConfig.getApiUrl() + "?url_long=" + longUrl + "&access_token=" + sinaConfig.getAcessToken();
    }

    /**
     * 调 新浪API
     * @param longUrl
     */
    private void callApi(String longUrl){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(targetUrl(longUrl));
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == HttpStatus.SC_OK){
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity);
                resolveResult(longUrl, result);
            } else {
                throw new RuntimeException("API 接口返回状态码不正确：" + statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("API 接口调用出错！");
        }
    }

    /**
     * 存缓存
     * @param longUrl 长地址
     * @param result API 返回字符串
     */
    private void resolveResult(String longUrl, String result){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(result);
            String jsonBean = jsonNode.findPath("urls").toString();

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ApiResultBean.class);
            List<ApiResultBean> list = objectMapper.readValue(jsonBean, javaType );
            if(null != list && list.size() > 0){
                ApiResultBean bean = list.get(0);
                if(bean.isResult()){
                    ShortUrl shortUrl = ShortUrl.builder().urlLong(bean.getUrl_long()).shortUrl(bean.getUrl_short()).build();
                    redisStringUtil.set(longUrl, shortUrl, 1000*60*60*24*30);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("解析API返回结果出错：" + e.getMessage());
        }
    }
}
