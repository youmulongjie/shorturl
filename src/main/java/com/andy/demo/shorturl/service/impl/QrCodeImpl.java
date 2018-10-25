package com.andy.demo.shorturl.service.impl;

import com.andy.demo.shorturl.redis.RedisStringUtil;
import com.andy.demo.shorturl.service.IQrCode;
import com.andy.demo.shorturl.util.QrCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生成二维码接口实现类
 * created by Andy.wang on 2018/10/25
 */
@Component
@Slf4j
public class QrCodeImpl implements IQrCode {
    @Autowired
    private RedisStringUtil redisStringUtil;

    @Override
    public synchronized String qrCode(String longUrl) {
        // 获取 redis 的 key
        String key = getKey(longUrl);

        // redis 不存在key，则先生成二维码，再将路径保存在 redis中
        if(!redisStringUtil.hasKey(key)){
            try {
                String path = QrCodeUtils.qrCode(longUrl, LOGO_PATH, DEST_PATH);
                log.info("放redis-二维码：key={}，value={}", key, path);
                redisStringUtil.set(key, path, 60*60*24*30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String value = redisStringUtil.get(key).toString();
        // redis 存在key，则直接返回 value
        log.info("取redis-二维码：key={}，value={}", key, value);
        return value;
    }
}
