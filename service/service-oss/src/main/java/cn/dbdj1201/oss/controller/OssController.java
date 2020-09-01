package cn.dbdj1201.oss.controller;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.oss.service.IOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 15:29
 */
@RestController
@Slf4j
@Api("阿里云图片上传服务")
@RequestMapping("/oss")
@CrossOrigin
public class OssController {

    @Autowired
    private IOssService ossService;

    @ApiOperation("头像上传")
    @PostMapping("/avatarUpload")
    public R uploadOssFile(@RequestParam("file") MultipartFile multipartFile ) {
        String url;
        try {
            log.info("上传图片到阿里oss - {}", multipartFile);
            url = this.ossService.uploadFileAvatar(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("出问题了");
            return R.error();
        }

        return R.success().data("url", url);
    }
}
