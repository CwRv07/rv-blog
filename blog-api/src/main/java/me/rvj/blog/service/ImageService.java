package me.rvj.blog.service;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/20 22:43
 */

@Service
@Slf4j
public class ImageService {

    private static final String[] uploadImageTypes = {"image/png", "image/jpg", "image/jpeg", "image/gif", "image/bmp"};

    @Value("${image.uploadImageSize}")
    private  double uploadImageSize;

    @Value("${image.baseUrl}")
    private  String baseUrl;
    @Value("${image.requestUrl}")
    private  String requestUrl;

    public Result uploadImage(MultipartFile multipartFile) {

        //检查格式
        String fileType = multipartFile.getContentType();
        log.info("文件类型：" + fileType);
        boolean flag = false;
        for (String type : uploadImageTypes) {
            if (fileType.equals(type)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return Result.fail(ErrorCode.INVALID_IMAGE_TYPES);
        }

        //检查文件大小
        double imageSize = (double) multipartFile.getSize() / 1024 / 1024;
        NumberFormat nf = new DecimalFormat("0.00");
        log.info("图片大小：" + Double.parseDouble(nf.format(imageSize)) + "M");
        if (imageSize > uploadImageSize) {
            return Result.fail(ErrorCode.INVAILD_IMAGE_SITE);
        }

        String fileName = multipartFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String format = simpleDateFormat.format(new Date());

        String path = baseUrl + format;
        fileName = UUID.randomUUID() + suffixName;
        String imageUrl = path + "/" + fileName;
        log.info("图片URL：" + imageUrl);
        File dest = new File(imageUrl);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(dest);
            return Result.success(requestUrl+format+"/"+fileName);
        } catch (IOException e) {
        }
        return Result.fail(ErrorCode.SERVER_BUSY);
    }
}
