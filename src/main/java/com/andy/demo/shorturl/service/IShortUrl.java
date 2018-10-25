package com.andy.demo.shorturl.service;

import com.andy.demo.shorturl.constant.IConstant;

/**
 * 转化 短地址 接口
 */
public interface IShortUrl {
    /**
     * 转化 短地址
     * @param longUrl 长地址
     * @return 转化后的短地址
     */
    String shortUrl(String longUrl);

    /**
     * 根据 长连接 设置 redis 短链接的 key值
     * @param longUrl 长地址
     * @return redis 短链接的 key值
     */
    default String getKey(String longUrl){
        return longUrl + IConstant.SHORT_URL_SUFFIX;
    }
}
