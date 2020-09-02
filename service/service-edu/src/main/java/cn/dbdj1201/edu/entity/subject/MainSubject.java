package cn.dbdj1201.edu.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-02 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainSubject {

    private String id;

    private String title;

    //一级分类下可以有多个二级分类
    private List<SecondSubject> children = new ArrayList<>();
}
