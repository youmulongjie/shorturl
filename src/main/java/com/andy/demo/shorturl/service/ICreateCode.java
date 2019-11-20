package com.andy.demo.shorturl.service;

/**
 * URL 生成二维码接口
 * created by Andy.wang on 2018/10/25
 */
public interface ICreateCode {
    /**
     * 根据文本 生成二维码
     *
     * @param text 文本
     * @return 生成的二维码文件地址
     */
    String qrCode(String text) throws Exception;

    /**
     * 根据文本 生成二维码（中心带logo）
     *
     * @param text     文本
     * @param logoPath logo文件相对地址
     * @return 生成的二维码文件地址
     */
    String qrCodeWithLogo(String text, String logoPath) throws Exception;

    /**
     * 根据文本 生成二维码（中心带logo，默认在配置文件配置）
     *
     * @param text 文本
     * @return 生成的二维码文件地址
     */
    String qrCodeWithDefaultLogo(String text) throws Exception;

    /**
     * 根据文本 生成条形码
     *
     * @param text 文本内容
     * @return 生成的条形码文件地址
     */
    String barCode(String text) throws Exception;

    /**
     * 根据文本 生成条形码（底部带 文本内容）
     *
     * @param text 文本内容
     * @return 生成的条形码文件地址
     */
    String barCodeWithWord(String text) throws Exception;
}
