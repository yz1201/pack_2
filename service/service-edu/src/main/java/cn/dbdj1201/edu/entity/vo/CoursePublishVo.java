package cn.dbdj1201.edu.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-04 14:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePublishVo implements Serializable {

    //课程id
    private String id;
    //课程标题
    private String title;
    //课程封面
    private String cover;
    //总课时
    private Integer lessonNum;
    //一级分类标题
    private String subjectLevelOne;
    //二级分类标题
    private String subjectLevelTwo;
    //讲师名字
    private String teacherName;
    //课程单价
    private String price;

}
