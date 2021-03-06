package cn.dbdj1201.statistics.service;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.entity.StatisticsDaily;
import cn.dbdj1201.statistics.entity.vo.StatisticsListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
public interface IStatisticsDailyService extends IService<StatisticsDaily> {

    /**
     *
     * @param date
     * @return
     */
    R registerCounter(String date);

    /**
     * 统计某一天的数据
     * @param date
     * @return
     */
    R statisticsFieldCounter(String date);

    /**
     * 根据查询字段获取统计数据
     * @param statisticsListVo
     * @return
     */
    Map<String, Object> getStaData(StatisticsListVo statisticsListVo);

    /**
     * 获取一段日期内的所有统计数据字段
     * @param statisticsListVo
     * @return
     */
     Map<String, Object> getStatisticsData(StatisticsListVo statisticsListVo);
}
