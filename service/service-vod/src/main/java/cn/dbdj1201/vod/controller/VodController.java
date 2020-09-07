package cn.dbdj1201.vod.controller;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.vod.service.IVodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

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

    @ApiOperation("根据视频id删除视频")
    @DeleteMapping("/delete/{videoSourceId}")
    public R deleteVideoByVideoId(@PathVariable("videoSourceId") String videoSourceId) {
        log.info("根据视频id删除视频-{}", videoSourceId);
        this.vodService.deleteVideoByVideoId(videoSourceId);
        return R.success().message("视频删除成功");
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("/deleteBatch")
    public R deleteBatchByVideoIds(@RequestParam("ids") String... ids) {
        log.info("根据视频id删除视频-{}", Arrays.asList(ids));
        this.vodService.deleteVideoByVideoIds(ids);
        return R.success().message("批量删除视频成功");
    }

    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        log.info("根据视频id获取视频播放凭证-{}", id);
        String playAuth = this.vodService.getPlayAuth(id);
        return R.success().data("playAuth", playAuth);
    }
}
