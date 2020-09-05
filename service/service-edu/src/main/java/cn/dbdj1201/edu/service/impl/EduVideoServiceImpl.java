package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.api.VodClient;
import cn.dbdj1201.edu.entity.EduVideo;
import cn.dbdj1201.edu.mapper.EduVideoMapper;
import cn.dbdj1201.edu.service.IEduVideoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private VodClient vodClient;

    @Override
    public boolean removeVideoById(String videoId) {
        //todo 删除视频
        log.info("根据课程id删除所有小节-{}", videoId);
        EduVideo eduVideo = this.getById(videoId);
        log.info("删除小节对应的阿里云视频");
        String videoSourceId = eduVideo.getVideoSourceId();
        //不为空才去删
        if (StrUtil.isNotEmpty(videoSourceId)) {
            R deleteVideoByVideoId = this.vodClient.deleteVideoByVideoId(videoSourceId);
            if (!deleteVideoByVideoId.getSuccess()) {
                log.info("msg:-{}", deleteVideoByVideoId.getMessage());
                return false;
            }
        }
        return this.removeById(videoId);
    }

    @Override
    public boolean removeVideosByCourseId(String courseId) {
        /*
            遍历出对应该课程id的所有video，删除阿里云的视频+删除小节数据表
         */
        log.info("删除课程下的所有小节-{}", courseId);
        try {
            List<EduVideo> eduVideos = this.list(new QueryWrapper<EduVideo>().select("id", "video_source_id").eq("course_id", courseId));

            int size = eduVideos.size();
            String[] ids = new String[size];
            int len = 0;
            for (EduVideo video : eduVideos) {
                if (StrUtil.isNotEmpty(video.getVideoSourceId())) {
                    ids[len] = video.getVideoSourceId();
                    len++;
                }
            }
            if (ids.length > 0) {
                log.info("批量删除的id为-{}", Arrays.toString(ids));
                R r = this.vodClient.deleteVideoByVideoIds(ids);
                if (!r.getSuccess()) {
                    log.error("msg:-{}", r.getMessage());
                    return false;
                }
            }

            List<String> idList = eduVideos.stream().map(EduVideo::getId).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(idList)) {
                log.info("批量删除-小节表-{}", idList);
                this.baseMapper.deleteBatchIds(idList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除课程下小节失败");
            return false;
        }
        return true;
    }
}
