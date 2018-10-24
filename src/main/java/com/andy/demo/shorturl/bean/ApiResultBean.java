package com.andy.demo.shorturl.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResultBean implements Serializable{
    private boolean result;
    private String url_short;
    private String url_long;
    private String object_type;
    private int type;
    private String object_id;
}

