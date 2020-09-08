package cn.dbdj1201.statistics.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 19:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsListVo {

    //统计数据类型 学员登录数统计 学员注册数统计 课程播放数统计 每日新增课程数统计
    private String type;
    //起始日期
    private String begin;
    //截止日期
    private String end;

}
