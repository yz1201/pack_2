package cn.dbdj1201.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yz1201
 * @date 2020-09-01 21:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectData implements Serializable {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
