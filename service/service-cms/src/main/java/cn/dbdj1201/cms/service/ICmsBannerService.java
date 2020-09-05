package cn.dbdj1201.cms.service;

import cn.dbdj1201.cms.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-05
 */
public interface ICmsBannerService extends IService<CmsBanner> {

    /**
     * 获取所有banner图片
     * @return
     */
    List<CmsBanner> getAllBanners();
}
