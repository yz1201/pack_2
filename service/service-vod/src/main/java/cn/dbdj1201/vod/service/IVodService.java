package cn.dbdj1201.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-04 19:27
 */
public interface IVodService {
    String uploadAliVideo(MultipartFile file);

    void deleteVideoByVideoId(String videoId);

    void deleteVideoByVideoIds(String[] ids);

    /**
     * 根据视频id获取视频播放凭证
     * @param id
     * @return
     */
    String getPlayAuth(String id);
}
