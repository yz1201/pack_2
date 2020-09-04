package cn.dbdj1201.vod.service.impl;

import cn.dbdj1201.vod.component.VodProperties;
import cn.dbdj1201.vod.service.IVodService;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-04 19:28
 */
@Service
@Slf4j
public class VodServiceImpl implements IVodService {

    @Autowired
    private VodProperties vodProperties;

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
}
