package me.rvj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.entity.Tag;
import me.rvj.blog.mapper.TagMapper;
import me.rvj.blog.service.TagService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.TagVo;
import me.rvj.blog.vo.params.TagParams;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rv-blog
 * @description: TagServiceImpl
 * @author: Rv_Jiang
 * @date: 2022/6/5 14:09
 */

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public Result listTag() {

        List<Tag> taglist = tagMapper.selectList(null);
        return Result.success(taglist);
    }

    @Override
    public Result listTagByCategory(TagParams tagParams) {

        IPage<TagVo> tagPage = new Page<>(tagParams.getPage(), tagParams.getPageSize());
        Long categoryId = tagParams.getCategoryId();
        IPage<TagVo> taglist = tagMapper.listTagByCategory(tagPage, categoryId);

        return Result.success(taglist.getRecords());

    }

    @Override
    public Result findTag(TagParams tagParams) {
        System.out.println(tagParams);
        Long id = tagParams.getId();
        String tagName = StringUtils.defaultIfBlank(tagParams.getTagName(),null);
        TagVo tag = tagMapper.findTag(id, tagName);

        return Result.success(tag);
    }

    @Override
    public Result createTag(TagParams tagParams) {
//         检测是否重复
        LambdaQueryWrapper<Tag> tlqw = new LambdaQueryWrapper<>();
        tlqw.eq(Tag::getTagName, tagParams.getTagName());
        Tag tag = tagMapper.selectOne(tlqw);
        if (ObjectUtils.isNotEmpty(tag)) {
            Result.fail(ErrorCode.TAG_HAS_EXIST);
        }

//         新增标签操作
        tag = new Tag();
        tag.setAvatar(tagParams.getAvatar());
        tag.setTagName(tagParams.getTagName());
        tag.setCategoryId(tagParams.getCategoryId());
        if (tagMapper.insert(tag) == 0) {
            /* 新增失败 */
            log.error("[tab新增操作] tag新增操作失败", tag);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", tag.getId());
        return Result.success(result);

    }

    @Override
    public Result updateTag(TagParams tagParams) {
        Tag tag = new Tag();
        tag.setId(tagParams.getId());
        tag.setAvatar(tagParams.getAvatar());
        tag.setTagName(tagParams.getTagName());
        tag.setCategoryId(tagParams.getCategoryId());

        if (tagMapper.updateById(tag) == 0) {
            /* 更新失败 */
            log.error("[tab更新操作] tag更新操作失败", tag);
            return Result.fail(ErrorCode.TAG_NOT_EXIST);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", tag.getId());
        return Result.success(result);

    }

    @Override
    public Result deleteTag(Long id) {
        if(tagMapper.deleteById(id)==0){
            /* 删除失败 */
            log.error("[tab删除操作] tag删除操作失败", id);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return Result.success(result);
    }

    @Override
    public Result countTag() {
        Integer tagNumber = tagMapper.selectCount(null);
        return Result.success(tagNumber);
    }
}
