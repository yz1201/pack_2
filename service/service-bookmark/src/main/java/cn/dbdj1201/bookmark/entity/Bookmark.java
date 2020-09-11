package cn.dbdj1201.bookmark.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 书签
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chrome_bookmark")
@ApiModel(value = "Bookmark对象", description = "书签")
public class Bookmark implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "所属上级")
    private String pid;

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

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime dateModified;

    //嵌套下级书签
    @TableField(exist = false)
    private List<Bookmark> children;

}
