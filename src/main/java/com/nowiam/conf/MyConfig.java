package com.nowiam.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-env")
public class MyConfig {
    public String file_path;
    public String tmp_path;
    public String separator;
    //

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setTmp_path(String tmp_path) {
        this.tmp_path = tmp_path;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
