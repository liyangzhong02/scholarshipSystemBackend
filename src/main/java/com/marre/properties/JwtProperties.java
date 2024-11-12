package com.marre.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "marre.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;
    /**
     * 用户端用户生成jwt令牌相关配置
     */
    private String stuSecretKey;
    private long stuTtl;
    private String stuTokenName;

}
