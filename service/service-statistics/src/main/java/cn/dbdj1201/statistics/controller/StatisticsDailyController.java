package cn.dbdj1201.statistics.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.api.UcenterClient;
import cn.dbdj1201.statistics.entity.vo.StatisticsListVo;
import cn.dbdj1201.statistics.service.IStatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/statistics")
@Slf4j
@CrossOrigin
@Api("统计模块")
public class StatisticsDailyController {

    @Autowired
    private IStatisticsDailyService dailyService;

    //统计某一天的注册人数
    @ApiOperation("统计某一天的注册人数")
    @PostMapping("/registerCounter/{date}")
    public R registerCounter(@PathVariable("date") String date) {
        return this.dailyService.registerCounter(date);
    }

    @ApiOperation("获取统计数据")
    @GetMapping("/getStaData")
    public R getStaData(@RequestBody StatisticsListVo statisticsListVo) {
        log.info("根据{}查询统计数据", statisticsListVo);
        //获取统计数据，返回数据数组及日期数组
        Map<String, Object> map = this.dailyService.getStaData(statisticsListVo);

        return R.success();
    }
}
