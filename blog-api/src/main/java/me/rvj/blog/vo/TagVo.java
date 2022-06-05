package me.rvj.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import me.rvj.blog.entity.Category;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/27 20:26
 */
@Data
public class TagVo {

    private Long id;

    private String avatar;

    private String tagName;


    @TableField(exist = false)
    private CategoryVo category;


}
