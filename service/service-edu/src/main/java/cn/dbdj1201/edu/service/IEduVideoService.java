package cn.dbdj1201.edu.service;

import cn.dbdj1201.edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
public interface IEduVideoService extends IService<EduVideo> {

    boolean removeVideoById(String videoId);

    boolean removeVideosByCourseId(String courseId);
}
