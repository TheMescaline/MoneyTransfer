package com.themescaline.moneytransfer.util;

import lombok.Data;

/**
 * POJO for mapping YAML db connection configuration
 *
 * @author lex.korovin@gmail.com
 */
@Data
public class YamlConnectionConfig {
    private String url;
    private String driver;
    private String user;
    private String password;
    private int poolSize;
}
