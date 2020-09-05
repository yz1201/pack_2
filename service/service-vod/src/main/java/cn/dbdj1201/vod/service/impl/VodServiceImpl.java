package cn.dbdj1201.vod.service.impl;

import cn.dbdj1201.vod.component.VodProperties;
import cn.dbdj1201.vod.service.IVodService;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-04 19:28
 */
@Service
@Slf4j
public class VodServiceImpl implements IVodService {

    @Autowired
    private VodProperties vodProperties;

    @Autowired
    private DefaultAcsClient defaultAcsClient;

    @Override
    public String uploadAliVideo(MultipartFile file) {
        log.info("文件上传-{}", file);
        UploadStreamResponse response;
        try {
            String originalFilename = file.getOriginalFilename();
            Assert.notNull(originalFilename, "传过来的啥玩意？");
            UploadStreamRequest request = new UploadStreamRequest(vodProperties.getKeyId(),
                    vodProperties.getKeySecret(),
                    originalFilename.substring(0, originalFilename.lastIndexOf(".")),
                    originalFilename,
                    file.getInputStream());

            UploadVideoImpl uploader = new UploadVideoImpl();
            response = uploader.uploadStream(request);

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
        return response.getVideoId();
    }

    @Override
    public void deleteVideoByVideoId(String videoId) {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoId);
        DeleteVideoResponse response;
        try {
            response = this.defaultAcsClient.getAcsResponse(request);
            log.info("视频已被删除 - {}", response.getRequestId());
        } catch (Exception e) {
            log.error("删除不了？");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVideoByVideoIds(String[] ids) {
        String idStr = ArrayUtil.join(ids, ",");
        log.info("待批量删除的id-{}", idStr);
        this.deleteVideoByVideoId(idStr);
    }

//    public static void main(String[] args) {
//        String[] ids = {"1", "2", "310"};
//
//        StringBuilder sb = new StringBuilder();
//
//        Arrays.stream(ids).forEach(id -> sb.append(id).append(","));
//        System.out.println(sb.substring(0, sb.lastIndexOf(",")));
//    }
}
