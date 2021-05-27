package com.sixj.license.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义的可被允许的服务器硬件信息的实体类（如果校验其他参数，可自行补充）.备注：如果只需要检验文件的生效和过期时间无需创建此类。
 * @author sixiaojie
 * @date 2021-05-25-13:34
 */
@Data
public class LicenseCheckModel implements Serializable {
    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;
}
