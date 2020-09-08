package cn.dbdj1201.statistics.service.impl;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.api.UcenterClient;
import cn.dbdj1201.statistics.entity.StatisticsDaily;
import cn.dbdj1201.statistics.entity.vo.StatisticsListVo;
import cn.dbdj1201.statistics.mapper.StatisticsDailyMapper;
import cn.dbdj1201.statistics.service.IStatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
@Service
@Slf4j
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements IStatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //todo 其他三个字段的自动化统计也要做一下
    @Override
    public R registerCounter(String date) {
        //远程调用。
        R countAtSomeday = this.ucenterClient.findRegisterCountAtSomeday(date);
        Integer registerCount = (Integer) countAtSomeday.getData().get("registerCount");
        log.info("远程调用完毕，{}注册人数为{}", date, registerCount);
        if (registerCount == null) {
            return countAtSomeday;
        }

        /*
        查询一下当前日期是否已有记录，如果没有插入一条新记录，如果有了,判断数据是否是新值，只修改对应已修改过的字段
        另外一种方式就是每次都先删除该日期的统计数据记录，每次都插入新的。
         */
        StatisticsDaily currentStatistics = this.getOne(new QueryWrapper<StatisticsDaily>().eq("date_calculated", date));
        if (currentStatistics == null) {
            StatisticsDaily statisticsDaily = new StatisticsDaily();
            statisticsDaily.setDateCalculated(date);
            statisticsDaily.setRegisterNum(registerCount);
            statisticsDaily.setCourseNum(12);
            statisticsDaily.setDateCalculated(date);
            statisticsDaily.setLoginNum(26);
            statisticsDaily.setVideoViewNum(25);
            this.save(statisticsDaily);
            log.info("入库的统计数据-{}", statisticsDaily);
        } else {
            if (!registerCount.equals(currentStatistics.getRegisterNum())) {
                currentStatistics.setRegisterNum(registerCount);
                this.updateById(currentStatistics);
                log.info("修改了统计数据入库-{}", currentStatistics);
            }
        }

        return R.success().message("统计数据入库完成");
    }

    @Override
    public Map<String, Object> getStaData(StatisticsListVo statisticsListVo) {
        String type = statisticsListVo.getType();
        String begin = statisticsListVo.getBegin();
        String end = statisticsListVo.getEnd();

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();

        if (type!=null){
            queryWrapper.eq(type,type);
        }

//        if (begin == null){
//            begin
//        }
        queryWrapper.between("gmt_create",begin,end);


        return null;
    }
}
