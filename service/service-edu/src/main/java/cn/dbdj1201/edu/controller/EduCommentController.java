package cn.dbdj1201.edu.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.common.utils.util.JwtUtils;
import cn.dbdj1201.edu.entity.EduComment;
import cn.dbdj1201.edu.service.IEduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/edu/comment")
@Api("前台课程评论模块")
@CrossOrigin
@Slf4j
public class EduCommentController {

    @Autowired
    private IEduCommentService commentService;

    @ApiOperation("获取课程评论列表分页")
    @GetMapping("/commentList/{page}/{limit}")
    public R getCommentList(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestParam String courseId) {
        log.info("分页查询课程评论，page-{},limit-{},courseId-{}", page, limit, courseId);
        Map<String, Object> map = this.commentService.getCommentPage(page, limit, courseId);
        return R.success().data(map);
    }

    @ApiOperation("写评论")
    @PostMapping("/saveComment")
    public R writeComment(@RequestBody EduComment comment, HttpServletRequest request) {
        /*
        经典用远程调用，我请求头的token里存了用户的id，头像，昵称，在这里一下子全拿到了。我还去用户中心干几把？
         */
        log.info("前台评论来了-{}", comment);
        JwtInfo jwtInfo = JwtUtils.getMemberInfoByJWT(request);
        this.commentService.writeComment(comment, jwtInfo);
        return R.success();
    }

}
