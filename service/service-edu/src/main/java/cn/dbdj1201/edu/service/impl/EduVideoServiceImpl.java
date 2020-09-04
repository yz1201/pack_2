package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.edu.entity.EduVideo;
import cn.dbdj1201.edu.mapper.EduVideoMapper;
import cn.dbdj1201.edu.service.IEduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-02
 */
@Service
@Slf4j
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements IEduVideoService {

    @Override
    public boolean removeVideoByCourseId(String courseId) {
        //todo 删除视频
        log.info("根据课程id删除所有小节-{}", courseId);
        return this.remove(new QueryWrapper<EduVideo>().eq("course_id", courseId));
    }
}
