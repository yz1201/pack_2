package cn.dbdj1201.edu.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-02 18:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo {

    //主键
    private String id;
    //章节名称
    private String title;
    //一个章节有多个小节
    private List<VideoVo> children = new ArrayList<>();
}
