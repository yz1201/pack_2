package cn.dbdj1201.edu.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-02 18:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    //主键
    private String id;
    //节点名称 第一课/讲/篇
    private String title;
    //视频id
    private String videoSourceId;
}
