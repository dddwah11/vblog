package com.xiaoliu.system.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 61分
 * @Date: 2022-11-04 20:07
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "myminio")
@Component
public class MinioEntity {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
