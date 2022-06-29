package me.rvj.blog.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import me.rvj.blog.service.AlipayService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/29 9:39
 */

@Service
public class AlipayServiceImpl implements AlipayService {

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.errorUrl}")
    private String errorUrl;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Override
    public Result page(String subject, String total) {
        System.out.println(subject+"\t"+total+"\t"+returnUrl);
        try {
            AlipayTradePagePayResponse response = Factory.Payment
                    /** 选择电脑网站 **/
                    .Page()
                    /** 调用支付方法(订单名称, 商家订单号, 金额, 成功页面) **/
                    .asyncNotify(notifyUrl)
                    .pay(subject, getOrderNo(), total, returnUrl);



            return Result.success(response.body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String getOrderNo() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime localDateTime = Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneOffset.ofHours(8))
                .toLocalDateTime();
        return df.format(localDateTime);
    }
}
