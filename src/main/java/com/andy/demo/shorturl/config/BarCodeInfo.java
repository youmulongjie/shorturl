package com.andy.demo.shorturl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 条形码参数信息
 * Author: Andy.wang
 * Date: 2019/11/20 10:56
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "barcodeinfo")
public class BarCodeInfo {

    /**
     * 生成的二维码尺寸，最大宽度
     */
    private int width;
    /**
     * 生成的二维码尺寸，最大高度
     */
    private int height;

    private int wordHeight;

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
