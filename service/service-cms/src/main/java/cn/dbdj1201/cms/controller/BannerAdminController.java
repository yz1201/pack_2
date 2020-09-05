package cn.dbdj1201.cms.controller;


import cn.dbdj1201.cms.entity.CmsBanner;
import cn.dbdj1201.cms.service.ICmsBannerService;
import cn.dbdj1201.common.utils.result.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-05
 */
@RestController
@RequestMapping("/cms/banner/admin")
@Api("后台品牌模块")
@Slf4j
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private ICmsBannerService bannerService;

    @ApiOperation("分页查询banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(
            @PathVariable("page") Long page,
            @PathVariable("limit") Long limit
    ) {
        log.info("分页查询banner-{}，-{}", page, limit);
        Page<CmsBanner> bannerPage = this.bannerService.page(new Page<>(page, limit));
        log.info("分页banner查询完成-{}", bannerPage.getRecords());
        return R.success().data("total", bannerPage.getTotal()).data("items", bannerPage.getRecords());
    }

    @ApiOperation("添加banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CmsBanner banner) {
        this.bannerService.saveBanner(banner);
        return R.success();
    }

    @ApiOperation("修改banner")
    @PostMapping("/updateBanner")
    public R updateBannerById(@RequestBody CmsBanner banner) {
        this.bannerService.updateBannerById(banner);
        return R.success();
    }


    @ApiOperation("删除banner")
    @PostMapping("/remove/{id}")
    public R removeBannerById(@PathVariable("id") String id) {
        this.bannerService.removeBannerById(id);
        return R.success();
    }

    @ApiOperation("获取banner")
    @PostMapping("/getBanner/{id}")
    public R getBannerById(@PathVariable("id") String id) {
        CmsBanner cmsBanner = this.bannerService.getBannerById(id);
        return R.success().data("cmsBanner", cmsBanner);
    }


}
