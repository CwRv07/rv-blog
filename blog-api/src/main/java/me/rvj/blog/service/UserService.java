package me.rvj.blog.service;

import me.rvj.blog.vo.Result;

public interface UserService {

    public Result listUser(Long page, Long pageSize);

    public Result listUserByCondition(Long page, Long pageSize, Long userId, String nickname);

    public Result insertUser(String nickname, String email, String website);

    public Result deleteUser(Long id);
}
