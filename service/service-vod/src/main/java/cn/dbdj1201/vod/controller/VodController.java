package cn.dbdj1201.vod.controller;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.vod.service.IVodService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-04 19:26
 */
@RestController
@RequestMapping("/vod/video")
@CrossOrigin
@Slf4j
@Api("阿里云视频点播模块")
public class VodController {

    @Autowired
    private IVodService vodService;

    @PostMapping("/uploadAliVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = this.vodService.uploadAliVideo(file);
        return R.success().data("videoId", videoId);
    }
}
