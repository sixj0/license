package com.sixj.license.model;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用户自定义密钥库参数类
 * @author sixiaojie
 * @date 2021-05-25-13:42
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {
    /**
     * 公钥/私钥在磁盘上的存储路径
     */
    private String storePath;
    private String alias;
    private String storePwd;
    private String keyPwd;

    public CustomKeyStoreParam(Class clazz, String resource,String alias,String storePwd,String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }


    /**
     * 复写de.schlichtherle.license.AbstractKeyStoreParam的getStream()方法<br/>
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(new File(storePath));
    }
}
