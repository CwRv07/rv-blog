package me.rvj.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.service.ImageService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/20 23:13
 */
@RestController
@RequestMapping("image")
@Slf4j
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("uploadImage")
    public Result uploadImage(@RequestParam("file") MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return imageService.uploadImage(multipartFile);
    }
}
