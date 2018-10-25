package com.andy.demo.shorturl.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 短链接API 调用返回json 串对应的Bean</br>
 *
 * 参考 ${@link http://open.weibo.com/wiki/2/short_url/shorten}
 */
@Data
public class ShortUrlApiReturnBean implements Serializable{
    /**
     * 短链的可用状态，true：可用、false：不可用
     */
    private boolean result;
    /**
     * 短链接
     */
    private String url_short;
    /**
     * 原始长链接
     */
    private String url_long;
    /**
     * 链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5、投票
     */
    private int type;

    private String object_type;
    private String object_id;
}

