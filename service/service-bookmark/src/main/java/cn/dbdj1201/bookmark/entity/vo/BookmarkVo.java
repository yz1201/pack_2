package cn.dbdj1201.bookmark.entity.vo;

import cn.dbdj1201.bookmark.entity.Bookmark;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-11 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("书签VO")
public class BookmarkVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "guid")
    private String guid;

    @ApiModelProperty(value = "类型(1:文件夹,2:书签)")
    private Integer type;

    @ApiModelProperty(value = "书签url")
    private String url;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime dateAdded;

    //嵌套下级书签
    private List<Bookmark> children;
}
