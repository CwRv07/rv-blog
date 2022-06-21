package me.rvj.blog.service;

import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.TagParams;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TagService {

    /**
     * 标签列表
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 14:18
     */
    public Result listTag();

    /**
     * 条件标签列表
     * @param tagParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 14:18
     */
    public Result listTagByCategory(TagParams tagParams);

    /**
     * 标签搜索
     * @param tagParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 15:09
     */
    public Result findTag(TagParams tagParams);

    /**
     * 新建标签
     * @param tagParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 14:19
     */
    public Result createTag(TagParams tagParams);

    /**
     * 更新标签
     * @param tagParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 14:19
     */
    public Result updateTag(TagParams tagParams);

    /**
     * 删除标签
     * @param id
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 14:20
     */
    public Result deleteTag(Long id);

    /**
     * 标签总数
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/21 10:28
     */
    public Result countTag();

}
