package me.rvj.blog.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rv-blog
 * @description: 支付宝支付配置
 * @author: Rv_Jiang
 * @date: 2022/6/29 9:34
 */
@Configuration
public class AlipayConfig implements ApplicationRunner {

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.privateKey}")
    private String privateKey;

    @Value("${alipay.publicKey}")
    private String publicKey;

    @Value("${alipay.gateway}")
    private String gateway;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Factory.setOptions(getOptions());
    }

    private Config getOptions() {
        /**这里省略了一些不必要的配置，可参考文档的说明**/
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = this.gateway;
        config.signType = "RSA2";
        config.appId = this.appId;
        /** 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中 **/
        config.merchantPrivateKey = this.privateKey;
        /** 注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可 **/
        config.alipayPublicKey = this.publicKey;
        /** 可设置异步通知接收服务地址（可选）**/
        config.notifyUrl = notifyUrl;
        return config;
    }
}
