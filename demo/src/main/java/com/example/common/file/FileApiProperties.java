package com.example.common.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("file.api")
@Data
@Component
public class FileApiProperties {
    private String download;
    private String upload;
    private String delete;
}
