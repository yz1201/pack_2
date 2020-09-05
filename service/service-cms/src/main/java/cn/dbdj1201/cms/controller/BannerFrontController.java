package cn.dbdj1201.cms.controller;


import cn.dbdj1201.cms.entity.CmsBanner;
import cn.dbdj1201.cms.service.ICmsBannerService;
import cn.dbdj1201.common.utils.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-05
 */
@RestController
@RequestMapping("/cms/front/banner")
@Api("前台品牌模块")
@Slf4j
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private ICmsBannerService bannerService;


    @ApiOperation("获取所有banner")
    @GetMapping("/getAll")
    public R getAllBanner() {
        return R.success().data("items", this.bannerService.getAllBanners());
    }

}
