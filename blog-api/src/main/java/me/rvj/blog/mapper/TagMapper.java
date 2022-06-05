package me.rvj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.rvj.blog.entity.Tag;
import me.rvj.blog.vo.ArticleVo;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 标签列表
     * @param page
     * @return IPage<TagVo>
     * @author Rv_Jiang
     * @date 2022/6/5 15:03
     */
    IPage<TagVo> listTag(IPage<TagVo> page);

    /**
     * 条件标签列表
     * @param page
     * @param categoryId
     * @return IPage<TagVo>
     * @author Rv_Jiang
     * @date 2022/6/5 15:03
     */
    IPage<TagVo> listTagByCategory(IPage<TagVo> page,
                                   Long categoryId);

    /**
     * 标签搜索
     * @param id
     * @param tagName
     * @return TagVo
     * @author Rv_Jiang
     * @date 2022/6/5 15:04
     */
    TagVo findTag(Long id,String tagName);
}
