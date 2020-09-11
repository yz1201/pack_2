package cn.dbdj1201.bookmark.service;

import cn.dbdj1201.bookmark.entity.Bookmark;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 书签 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-11
 */
public interface IBookmarkService extends IService<Bookmark> {

    /**
     * 解析上传的json文件，并入库
     * @param file
     */
    void resolve(MultipartFile file);

    /**
     * 把本地书签文件解析并入库
     */
    void activeLocalBookmark();

}
