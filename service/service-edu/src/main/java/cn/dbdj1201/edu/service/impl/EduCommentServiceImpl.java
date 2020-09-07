package cn.dbdj1201.edu.service.impl;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.edu.api.UcenterClient;
import cn.dbdj1201.edu.entity.EduComment;
import cn.dbdj1201.edu.entity.EduCourse;
import cn.dbdj1201.edu.mapper.EduCommentMapper;
import cn.dbdj1201.edu.service.IEduCommentService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-07
 */
@Service
@Slf4j
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements IEduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> getCommentPage(Long page, Long limit, String courseId) {
        Page<EduComment> pageCondition = new Page<>(page, limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<EduComment>().eq("course_id", courseId);
        Page<EduComment> commentPage = this.page(pageCondition, queryWrapper);
        log.info("分页查询评论完毕-{}", commentPage.getRecords());

        Map<String, Object> resultMap = new HashMap<>();

        List<EduComment> records = commentPage.getRecords();
        long total = commentPage.getTotal();
        long size = commentPage.getSize();
        long current = commentPage.getCurrent();
        long pages = commentPage.getPages();

        boolean hasNext = commentPage.hasNext();
        boolean hasPrevious = commentPage.hasPrevious();

        resultMap.put("items", records);
        resultMap.put("total", total);
        resultMap.put("size", size);
        resultMap.put("current", current);
        resultMap.put("pages", pages);
        resultMap.put("hasNext", hasNext);
        resultMap.put("hasPrevious", hasPrevious);

        return resultMap;
    }

    @Override
    public void writeComment(EduComment comment, JwtInfo jwtInfo) {
        comment.setAvatar(jwtInfo.getAvatar());
        comment.setNickname(jwtInfo.getNickname());
        comment.setMemberId(jwtInfo.getId());
        log.info("将要入库的评论信息为-{}", comment);
        this.save(comment);
    }
}
