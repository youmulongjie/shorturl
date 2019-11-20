package com.andy.demo.shorturl.service;

/**
 * 转化 短地址 接口
 */
public interface IShortUrl {
    /**
     * 转化 短地址
     *
     * @param longUrl 长地址
     * @return 转化后的短地址
     */
    String shortUrl(String longUrl);
}
