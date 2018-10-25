package com.andy.demo.shorturl.service;

import com.andy.demo.shorturl.constant.IConstant;

/**
 * URL 生成二维码接口
 * created by Andy.wang on 2018/10/25
 */
public interface IQrCode {
    /**
     * 二维码在服务器上生成的根目录
     */
    String DEST_PATH = "D://qrCode/picture";
    /**
     * logo文件路径
     */
    String LOGO_PATH = "D://qrCode/logo.jpg";

    /**
     * 根据长地址 生成二维码，返回生成文件地址
     * @param longUrl 长地址
     * @return 生成的二维码文件地址
     */
    String qrCode(String longUrl);

    /**
     * 根据 长连接 设置 redis 二维码的 key值
     * @param longUrl 长地址
     * @return redis 二维码的 key值
     */
    default String getKey(String longUrl){
        return longUrl + IConstant.QR_CODE_SUFFIX;
    }
}
