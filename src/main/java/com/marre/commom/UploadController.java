package com.marre.commom;

import com.marre.constant.MessageConstant;
import com.marre.utils.AliOssUtil;
import com.marre.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Class: com.marre.commom
 * @ClassName: CommonController
 * @author: Marre
 * 通用工具类
 */
@RestController
@RequestMapping("/upload")
@Api(tags = "上传接口")
@Slf4j
public class UploadController {

    @Autowired
    AliOssUtil aliOssUtil;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("")
    public Result<String> upload(MultipartFile file) throws IOException {
        //获取原始文件名以获取后缀
        String originalFilename = file.getOriginalFilename();
        //截取后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //构造新文件名称
        String objectName = UUID.randomUUID() + extension;
        //文件的请求路径
        String filePath = aliOssUtil.upload(file.getBytes(), objectName);
        return Result.success(filePath);
    }
}
