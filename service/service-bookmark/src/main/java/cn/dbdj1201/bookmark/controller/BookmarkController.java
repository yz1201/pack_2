package cn.dbdj1201.bookmark.controller;


import cn.dbdj1201.bookmark.service.IBookmarkService;
import cn.dbdj1201.common.utils.result.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 书签 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-11
 */
@RestController
@RequestMapping("/bookmark")
@Slf4j
@Api("chrome书签后台管理")
public class BookmarkController {

    @Autowired
    private IBookmarkService bookmarkService;

    @GetMapping("/findAll")
    public R getAll() {
        return R.success();
    }

    @PostMapping("/resolve")
    public R resolve(MultipartFile file) {
        this.bookmarkService.resolve(file);
        return R.success().message("解析完成");
    }


    @GetMapping("/activeLocalBookmark")
    public R activeLocalBookmark(){

        this.bookmarkService.activeLocalBookmark();
        return R.success();
    }

}
