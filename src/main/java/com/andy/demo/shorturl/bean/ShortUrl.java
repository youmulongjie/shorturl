package com.andy.demo.shorturl.bean;

import lombok.Builder;
import lombok.Data;

/**
 * 短地址及二维码地址 Bean
 */
@Data
@Builder
public class ShortUrl {
    /**
     * 长地址（原URL地址）
     */
    private String urlLong;
    /**
     * 短地址（新浪接口生成 short_url）
     */
    private String shortUrl;
    /**
     * 二维码地址
     */
    private String qrCodeAddress;


}
