package cn.dbdj1201.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-30 10:33
 * 教师条件查询VO
 */
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称,模糊查询")
    private  String name;
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;
    //string类型的时间
    @ApiModelProperty(value = "查询开始时间", example = "2020-01-01 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间", example = "2020-12-01 10:10:10")
    private String end;

}
