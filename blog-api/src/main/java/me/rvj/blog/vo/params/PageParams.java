package me.rvj.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: rv-blog
 * @description: PageParams
 * @author: Rv_Jiang
 * @date: 2022/5/21 17:05
 */

@Data
public class PageParams {

    private Integer page = 1;

    private Integer pageSize = 6;

    private Long categoryId;
    private Long[] tagId;
    private Long upperLimitTime;
    private Long lowerLimitTime;
    private String search;

}
