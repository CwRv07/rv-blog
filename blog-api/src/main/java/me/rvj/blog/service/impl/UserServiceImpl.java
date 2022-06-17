package me.rvj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.entity.SysUser;
import me.rvj.blog.entity.User;
import me.rvj.blog.mapper.UserMapper;
import me.rvj.blog.service.UserService;
import me.rvj.blog.util.JWTUtils;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: rv-blog
 * @description: 游客用户服务层实现类
 * @author: Rv_Jiang
 * @date: 2022/6/16 17:41
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 游客列表查询
     *
     * @param page
     * @param pageSize
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/16 17:44
     */
    @Override
    public Result listUser(Long page, Long pageSize) {
        Page<User> userPage = new Page<>(page, pageSize);
        Page<User> userList = userMapper.selectPage(userPage, null);

        return Result.success(userList);
    }

    /**
     * 游客列表条件查询
     *
     * @param page
     * @param pageSize
     * @param userId
     * @param nickname
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/16 18:10
     */
    @Override
    public Result listUserByCondition(Long page, Long pageSize, Long userId, String nickname) {
        Page<User> userPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> userLQW = new LambdaQueryWrapper<>();

        if (userId != null) {
            userLQW.like(User::getId, userId);
        }
        if (nickname != null) {
            userLQW.like(User::getNickname, nickname);
        }
        Page<User> userList = userMapper.selectPage(userPage, userLQW);

        return Result.success(userList);
    }


    /**
     * 新增游客
     *
     * @param nickname
     * @param email
     * @param website
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/16 18:13
     */
    @Override
    public Result insertUser(String nickname, String email, String website) {
        LambdaQueryWrapper<User> userLQW = new LambdaQueryWrapper<>();
//        检测用户是否已存在
        userLQW.eq(User::getEmail, email);
        User oldUser = userMapper.selectOne(userLQW);

        if (ObjectUtils.isEmpty(oldUser)) {
            /* 新增用户 */

//            检测昵称是否已存在
            userLQW = new LambdaQueryWrapper<>();
            userLQW.eq(User::getNickname, nickname);
            User user = userMapper.selectOne(userLQW);
            if (ObjectUtils.isNotEmpty(user)) {
                return Result.fail(ErrorCode.NICKNAME_HAS_EXIST);
            }

            User newUser = new User();
            newUser.setNickname(nickname);
            newUser.setEmail(email);
            newUser.setWebsite(website);
            int insert = userMapper.insert(newUser);

            if (insert != 0) {
                return Result.success(newUser);
            } else {
                log.error("[游客新增失败]", newUser);
                return Result.fail(ErrorCode.SERVER_BUSY);
            }


        } else {
            /* 更新用户 */
//            检测昵称是否已存在
            userLQW = new LambdaQueryWrapper<>();
            userLQW.eq(User::getNickname, nickname);
            User user = userMapper.selectOne(userLQW);
            if (ObjectUtils.isNotEmpty(user)) {
                return Result.fail(ErrorCode.NICKNAME_HAS_EXIST);
            }

            oldUser.setNickname(nickname);
            if (StringUtils.isNotBlank(website)) {
                oldUser.setNickname(website);
            }
            int update = userMapper.updateById(oldUser);
            if (update != 0) {
                return Result.success(oldUser);
            } else {
                log.error("[游客更新失败]", oldUser);
                return Result.fail(ErrorCode.SERVER_BUSY);
            }
        }
    }

    /**
     * 删除用户
     * @param id
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/16 19:09
     */
    @Override
    public Result deleteUser(Long id) {
        LambdaQueryWrapper<User> userLQW = new LambdaQueryWrapper<>();

//        检测用户是否存在
        userLQW.eq(User::getId, id);
        User user = userMapper.selectOne(userLQW);
        if(ObjectUtils.isEmpty(user)){
            return Result.fail(ErrorCode.USER_NOT_EXIXT);
        }

        int delete = userMapper.deleteById(id);
        if(delete!=0){
            return Result.success(id);
        }else{
            return Result.fail(ErrorCode.SERVER_BUSY);
        }


    }
}
