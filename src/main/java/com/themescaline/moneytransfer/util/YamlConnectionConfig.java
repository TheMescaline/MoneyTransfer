package com.themescaline.moneytransfer.util;

import lombok.Data;

@Data
public class YamlConnectionConfig {
    private String url;
    private String driver;
    private String user;
    private String password;
    private int poolSize;
}
