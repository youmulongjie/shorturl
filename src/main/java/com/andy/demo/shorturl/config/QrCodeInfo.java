package com.andy.demo.shorturl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 二维码参数信息
 * Author: Andy.wang
 * Date: 2019/11/20 09:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qrcodeinfo")
public class QrCodeInfo {
    /**
     * 默认的logo图片地址
     */
    private String logoPath;
    /**
     * 压缩logo时，最大宽度
     */
    private int logoWidth;
    /**
     * 压缩logo时，最大高度
     */
    private int logoHeight;

    /**
     * 生成的二维码尺寸
     */
    private int qrCodeSize;
    /**
     * 二维码内容的文本编码
     */
    private String charset;
    /**
     * 二维码图片格式
     */
    private String pictureFormat;

    /**
     * 二维码保存地址
     */
    private String createPath;

}
