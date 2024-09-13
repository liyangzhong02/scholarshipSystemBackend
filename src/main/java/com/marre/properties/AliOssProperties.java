package com.marre.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Class: Properties
 * @ClassName: AliossProperties
 * @author: Marre
 * 阿里云属性
 */
@Component
@Data
@ConfigurationProperties("marre.alioss")
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
