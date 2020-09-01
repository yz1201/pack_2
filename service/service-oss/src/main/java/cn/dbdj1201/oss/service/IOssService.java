package cn.dbdj1201.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 15:29
 */
public interface IOssService {
    /**
     * 上传头像到oss，返回图片对应URL
     *
     * @param multipartFile
     * @return
     */
    String uploadFileAvatar(MultipartFile multipartFile) throws IOException;
}
