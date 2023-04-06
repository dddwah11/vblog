package com.xiaoliu.system.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 61分
 * @date: 2022/8/26 20:49
 * @description:
 */
@Component
@ConfigurationProperties(prefix = "alioss")
@Data
public class OssEntity {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
