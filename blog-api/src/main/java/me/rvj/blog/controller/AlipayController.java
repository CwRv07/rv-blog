package me.rvj.blog.controller;

import me.rvj.blog.service.AlipayService;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.AlipayParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: rv-blog
 * @description: 支付宝支付业务
 * @author: Rv_Jiang
 * @date: 2022/6/29 9:27
 */

@RestController
@RequestMapping("alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @PostMapping("/page")
    public Result page(@RequestBody AlipayParams alipayParams) {
        return alipayService.page(alipayParams.getSubject(),alipayParams.getTotal());
    }
}
