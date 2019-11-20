package com.andy.demo.shorturl.service.impl;

import com.andy.demo.shorturl.component.BarCodeComponent;
import com.andy.demo.shorturl.component.QrCodeComponent;
import com.andy.demo.shorturl.config.BarCodeInfo;
import com.andy.demo.shorturl.config.QrCodeInfo;
import com.andy.demo.shorturl.service.ICreateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生成二维码接口实现类
 * created by Andy.wang on 2018/10/25
 */
@Slf4j
@Component
public class CreateCodeImpl implements ICreateCode {
    @Autowired
    private QrCodeInfo qrcodeInfo;
    @Autowired
    private QrCodeComponent qrCodeComponent;

    @Autowired
    private BarCodeInfo barCodeInfo;
    @Autowired
    private BarCodeComponent barCodeComponent;


    /**
     * 根据文本 生成二维码
     *
     * @param text 文本
     * @return 生成的二维码文件地址
     */
    @Override
    public String qrCode(String text) throws Exception {
        String path = qrCodeComponent.qrCode(text, null, qrcodeInfo.getCreatePath(), null);
        log.info("生成二维码文件成功，文件内容：{}，二维码地址：{}", text, path);
        return path;
    }

    /**
     * 根据文本 生成二维码（带logo）
     *
     * @param text     文本
     * @param logoPath logo文件相对地址
     * @return 生成的二维码文件地址
     */
    @Override
    public String qrCodeWithLogo(String text, String logoPath) throws Exception {
        String path = qrCodeComponent.qrCode(text, logoPath, qrcodeInfo.getCreatePath(), null);
        log.info("生成二维码文件（带logo）成功，文件内容：{}，二维码地址：{}", text, path);
        return path;
    }

    /**
     * 根据文本 生成二维码（带logo，默认在配置文件配置）
     *
     * @param text 文本
     * @return 生成的二维码文件地址
     */
    @Override
    public String qrCodeWithDefaultLogo(String text) throws Exception {
        String path = qrCodeComponent.qrCode(text, qrcodeInfo.getLogoPath(), qrcodeInfo.getCreatePath(), null);
        log.info("生成二维码文件（带logo）成功，文件内容：{}，二维码地址：{}", text, path);
        return path;
    }

    /**
     * 根据文本 生成条形码
     *
     * @param text 文本内容
     * @return 生成的条形码文件地址
     */
    @Override
    public String barCode(String text) throws Exception {
        String path = barCodeComponent.barCode(text, barCodeInfo.getCreatePath(), text, false);
        log.info("生成条形码文件成功，文件内容：{}，条形码地址：{}", text, path);
        return path;
    }

    /**
     * 根据文本 生成条形码（底部带 文本内容）
     *
     * @param text 文本内容
     * @return 生成的条形码文件地址
     */
    @Override
    public String barCodeWithWord(String text) throws Exception {
        String path = barCodeComponent.barCode(text, barCodeInfo.getCreatePath(), text, true);
        log.info("生成条形码文件（带底部文本）成功，文件内容：{}，条形码地址：{}", text, path);
        return path;
    }


}
