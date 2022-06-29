package me.rvj.blog.vo.params;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: 阿里支付参数
 * @author: Rv_Jiang
 * @date: 2022/6/29 11:18
 */
@Data
public class AlipayParams {

    private String subject="打赏";

    private String total="10";
}
