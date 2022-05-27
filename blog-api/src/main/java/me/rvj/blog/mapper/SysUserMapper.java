package me.rvj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.rvj.blog.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
