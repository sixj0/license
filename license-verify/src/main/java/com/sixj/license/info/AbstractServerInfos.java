package com.sixj.license.info;

import com.sixj.license.model.LicenseCheckModel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 用户获取服务器的硬件信息
 * @author sixiaojie
 * @date 2021-05-25-13:38
 */
@Slf4j
public abstract class AbstractServerInfos {

    /**
     * 组装需要额外校验的License参数
     * @return
     */
    public LicenseCheckModel getServerInfos(){
        LicenseCheckModel result = new LicenseCheckModel();

        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
        }catch (Exception e){
            log.error("获取服务器硬件信息失败",e);
        }

        return result;
    }


    /**
     * 获取IP地址
     * @return
     * @throws Exception
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * 获取Mac地址
     * @return
     * @throws Exception
     */
    protected abstract List<String> getMacAddress() throws Exception;


    /**
     * 获取CPU序列号
     * @return
     * @throws Exception
     */
    protected abstract String getCPUSerial() throws Exception;


    /**
     * 获取主板序列号
     * @return
     * @throws Exception
     */
    protected abstract String getMainBoardSerial() throws Exception;


    /**
     * 获取当前服务器所有符合条件的InetAddress
     * @return
     * @throws Exception
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = (InetAddress) inetAddresses.nextElement();

                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if(!inetAddr.isLoopbackAddress()
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()){
                    result.add(inetAddr);
                }
            }
        }

        return result;
    }


    /**
     * 获取某个网络接口的Mac地址
     * @param inetAddr
     * @return
     */
    protected String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();

            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    stringBuffer.append("-");
                }

                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    stringBuffer.append("0" + temp);
                }else{
                    stringBuffer.append(temp);
                }
            }

            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
