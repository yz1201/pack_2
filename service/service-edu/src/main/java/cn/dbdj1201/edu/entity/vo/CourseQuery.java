package cn.dbdj1201.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-30 10:33
 * 教师条件查询VO
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;
    @ApiModelProperty(value = "课程状态，发布-未发布")
    private String status;

}
