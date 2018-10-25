package com.andy.demo.shorturl.service.impl;

import com.andy.demo.shorturl.bean.ShortUrlApiConfig;
import com.andy.demo.shorturl.bean.ShortUrlApiReturnBean;
import com.andy.demo.shorturl.redis.RedisStringUtil;
import com.andy.demo.shorturl.service.IShortUrl;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 短地址接口实现类（依赖 新浪API）
 */
@Component
@Slf4j
public class ShortUrlImpl implements IShortUrl{
    @Autowired
    private ShortUrlApiConfig shortUrlApiConfig;
    @Autowired
    private RedisStringUtil redisStringUtil;

    @Override
    public synchronized String shortUrl(String longUrl) {
        // 获取 redis 的 key
        String key = getKey(longUrl);

        // redis 不存在key，则先调用新浪接口后将值保存在 redis中
        if(!redisStringUtil.hasKey(key)){
            callApi(longUrl);
        }

        String value = redisStringUtil.get(key).toString();
        // redis 存在key，则直接返回 value
        log.info("取redis-短地址：key={}，value={}", key, value);
        return value;
    }


    /**
     * 调 新浪短链接API
     * @param longUrl 长连接
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
     * 获取 转短链接API 访问的 url
     * @param longUrl 长地址
     * @return 访问API的URL地址
     */
    private String targetUrl(String longUrl){
        return shortUrlApiConfig.getApiUrl() + "?url_long=" + longUrl + "&access_token=" + shortUrlApiConfig.getAcessToken();
    }

    /**
     * 存 redis 缓存
     * @param longUrl 长地址
     * @param result API 返回json字符串
     */
    private void resolveResult(String longUrl, String result){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(result);
            String jsonBean = jsonNode.findPath("urls").toString();

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ShortUrlApiReturnBean.class);
            List<ShortUrlApiReturnBean> list = objectMapper.readValue(jsonBean, javaType );
            if(null != list && list.size() > 0){
                ShortUrlApiReturnBean bean = list.get(0);
                // 短链接 可用则 存放在 redis 中（设置 有效期是 30天）
                if(bean.isResult()){
                    String key = getKey(longUrl);
                    String value = bean.getUrl_short();
                    log.info("存redis-短地址：key={}，value={}", key, value);
                    redisStringUtil.set(key, value, 60*60*24*30);
                } else {
                    throw new RuntimeException("API接口返回短链接不可用：" + bean.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("解析API返回结果出错：" + e.getMessage());
        }
    }
}
