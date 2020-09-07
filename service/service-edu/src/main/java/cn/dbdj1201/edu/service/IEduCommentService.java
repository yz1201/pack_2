package cn.dbdj1201.edu.service;

import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.edu.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-07
 */
public interface IEduCommentService extends IService<EduComment> {

    Map<String, Object> getCommentPage(Long page, Long limit, String courseId);

    void writeComment(EduComment comment, JwtInfo jwtInfo);
}
