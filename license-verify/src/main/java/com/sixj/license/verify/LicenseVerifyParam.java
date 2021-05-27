package com.sixj.license.verify;

import lombok.Data;

import java.io.Serializable;

/**
 * License认证需要的参数实体类
 * @author sixiaojie
 * @date 2021-05-25-15:15
 */
@Data
public class LicenseVerifyParam implements Serializable {
    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath;
}
