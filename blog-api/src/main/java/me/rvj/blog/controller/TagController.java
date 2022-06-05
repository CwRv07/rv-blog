package me.rvj.blog.controller;

import me.rvj.blog.service.TagService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.TagParams;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/5 15:29
 */

@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("listTag")
    public Result listTag() {
        return tagService.listTag();
    }

    @GetMapping("listTagByCategory")
    public Result listTagByCategory(TagParams tagParams) {
        if (tagParams.getCategoryId() == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return tagService.listTagByCategory(tagParams);
    }

    @GetMapping("findTag")
    public Result findTag(TagParams tagParams) {
        if (ObjectUtils.allNull(tagParams.getId(), tagParams.getTagName())) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return tagService.findTag(tagParams);
    }

    @PostMapping("createTag")
    public Result createTag(@RequestBody TagParams tagParams) {
//        暂不实现avatar功能，因此不进行判断
        System.out.println(tagParams);
        if (ObjectUtils.anyNull(tagParams.getTagName(), tagParams.getCategoryId())) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return tagService.createTag(tagParams);
    }

    @PutMapping("updateTag")
    public Result updateTag(@RequestBody TagParams tagParams) {
//        暂不实现avatar功能，因此不进行判断
        if(ObjectUtils.anyNull(
                tagParams.getId(),
                tagParams.getTagName(),
                tagParams.getCategoryId()
        )){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return tagService.updateTag(tagParams);
    }

    @DeleteMapping("deleteTag/{tagId}")
    public Result deleteTag(@PathVariable("tagId") Long tagId){
        if(tagId==null){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return tagService.deleteTag(tagId);
    }

}
