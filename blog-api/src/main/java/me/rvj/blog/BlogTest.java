package me.rvj.blog;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.entity.SysUser;
import me.rvj.blog.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/2 11:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BlogTest {

    @Autowired SysUserMapper sysUserMapper;

    @Test
    public void exampleTest(){
//        SysUser sysUser = sysUserMapper.selectById(1404448588146192386L);
//        sysUser.setAvatar("\uD83D\uDE01");
//        sysUserMapper.updateById(sysUser);
        SysUser sysUser = sysUserMapper.selectById(1404448588146192386L);
        System.out.println(sysUser);
    }
}
