# license



### **license授权机制的原理**

（1）生成密钥对，包含私钥和公钥。（2）授权者保留私钥，使用私钥对授权信息诸如使用截止日期，mac 地址等内容生成 license 签名证书。（3）公钥给使用者，放在代码中使用，用于验证 license 签名证书是否符合使用条件。

#### 使用KeyTool生成密匙库：

1. 生成私钥库

   ```shell
   keytool -genkeypair -keysize 1024 -validity 3650 -alias SYSHLANG -keystore privateKeys.keystore -storepass 12345678A -keypass 12345678A -dname "CN=sixj, OU=runlion, O=redlion, L=HZ, ST=ZJ, C=CN"
   ```

   > **参数说明：**
   >
   > - keysize 密钥长度
   > - validity 私钥的有效期（单位：天）
   > - alias 私钥别称
   > - keystore 指定私钥库文件的名称 (生成在当前目录)
   > - storepass 指定私钥库的密码 (keystore 文件存储密码)
   > - keypass 指定别名条目的密码 (私钥加解密密码)
   > - dname 证书个人信息
   >   - CN 为你的姓名
   >   - OU 为你的组织单位名称
   >   - O 为你的组织名称
   >   - L 为你所在的城市名称
   >   - ST 为你所在的省份名称
   >   - C 为你的国家名称



2. 生成证书文件

   ```shell
   keytool -exportcert -alias SYSHLANG -keystore privateKeys.keystore -storepass 12345678A -file certfile.cer
   ```

   > **参数说明：**
   >
   > - alias 私钥别称
   > - keystore 指定私钥库文件的名称 (如果没有带路径，在当前目录查找)
   > - storepass 指定私钥库的密码
   > - file 导出证书文件名称

3. 生成公钥库

   ```shell
   keytool -import -alias SYSHLANG -file certfile.cer -keystore publicCerts.keystore  -storepass 12345678A
   ```

   > **参数说明：**
   >
   > - alias 公钥别称
   > - file 证书文件名称
   > - keystore 公钥文件名称
   > - storepass 指定私钥库的密码

看到以下三个文件：

- privateKeys.keystore（私钥）提供给生成证书使用
- publicCerts. keystore（公钥）提供给证书认证使用
- certfile.cer后续步骤用不到，可以删除。




#### License证书：

根据客户服务器硬件信息（MAC地址、IP地址、CPU序列号、主板序列号）生成授权证书，同时可以给授权证书设置生效时间与失效时间。

整个授权过程分为三步：

1. 获取客户端服务器信息（license-server-info服务）

   > 将license-server-info服务部署到客户服务器上
   >
   > 请求接口`/license/getServerInfo`
   >
   > 得到硬件信息：
   >
   > ```json
   > {
   >     "ipAddress": [ //授权的ip列表
   >         "172.17.0.8"
   >     ],
   >     "macAddress": [ //授权的mac地址列表
   >         "52-54-00-74-0B-D9"
   >     ],
   >     "cpuSerial": "55 06 05 00 FF FB 8B 0F", //cpu序列号
   >     "mainBoardSerial": "afb14aac-eccb-4a37-9c31-e7951ce73e0d"//主板序列号
   > }
   > ```

2. 生成授权证书（license-creator服务）

   > 我们自己部署生成证书的服务（license-creator）
   >
   > 请求接口`/license/generateLicense`
   >
   > 入参传入授权信息：
   >
   > ```json
   > {
   >     
   >     "expiryTime":"2021-05-25 19:07:59", //生效时间
   >     "issuedTime":"2021-04-25 19:07:00", //失效时间
   >     "keyPass":"12345678A", //密钥的密码
   >     "privateAlias":"SYSHLANG",
   >     "licensePath":"/Users/sixj/Desktop/license/license.lic", //证书生成地址
   >     "privateKeysStorePath":"/Users/sixj/Desktop/license/privateKeys.keystore",// 密钥文件地址
   >     "storePass":"12345678A", //密钥库的密码
   >     "subject":"pushi-kn-graph",
   >     "licenseCheckModel":{ //授权验证信息
   >         "cpuSerial":"47A8E193-23D4-5B93-92AB-4A96FBC0346F",//cpu序列号
   >         "ipAddress":[ //ip
   >             "192.168.174.107"
   >         ],
   >         "macAddress":[ //mac地址
   >             "F8-FF-C2-6A-3E-73"
   >         ],
   >         "mainBoardSerial":"C02C31HZMD6P"//主板序列号
   >     }
   > }
   > ```
   >
   > 会生成一个`license.lic`授权文件到执行目录

3. 授权验证（license-verify-starter）

   > 需要添加授权功能的服务需要依赖`license-verify-starter`
   >
   > 配置证书信息
   >
   > ```properties
   > license.subject: pushi-kn-graph
   > license.publicAlias: SYSHLANG
   > license.storePass: 12345678A
   > license.licensePath: /Users/sixj/Desktop/license/license.lic
   > license.publicKeysStorePath: /Users/sixj/Desktop/license/publicCerts.keystore
   > ```
   >
   > 项目启动的时候，会去验证授权证书的有效性，是否在有效期内，硬件信息是否匹配，如果授权证书无效，项目启动失败。
   >
   > 另外可以在一些核心接口，比如登陆接口，添加`@License`注解，请求该接口的时候，也会去验证授权证书的有效性，比如验证证书是否到期，如果失效，该接口将会拒绝访问。










