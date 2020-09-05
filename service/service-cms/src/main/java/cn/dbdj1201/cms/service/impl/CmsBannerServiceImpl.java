package cn.dbdj1201.cms.service.impl;

import cn.dbdj1201.cms.entity.CmsBanner;
import cn.dbdj1201.cms.mapper.CmsBannerMapper;
import cn.dbdj1201.cms.service.ICmsBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-05
 */
@Service
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerMapper, CmsBanner> implements ICmsBannerService {

    @Override
    public List<CmsBanner> getAllBanners() {
        QueryWrapper<CmsBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 3");
        return this.list(queryWrapper);
    }
}
