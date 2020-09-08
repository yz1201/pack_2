package cn.dbdj1201.statistics.schedule;

import cn.dbdj1201.statistics.service.IStatisticsDailyService;
import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 18:53
 */
@Component
@Slf4j
public class ScheduleTask {

    @Autowired
    private IStatisticsDailyService dailyService;

    //每天凌晨01:00执行定时任务，生成前一天统计数据，这里是每天登陆人数
    @Scheduled(cron = "0 0 1 ? * *")
    public void generateStatisticsData() {
        LocalDateTime current = LocalDateTime.now();
        log.info("来执行定时任务了-{}", current);
        LocalDateTime lastDay = LocalDateTimeUtil.offset(current, -1, ChronoUnit.DAYS);
        this.dailyService.registerCounter(LocalDateTimeUtil.format(lastDay, "yyyy-MM-dd"));
    }


}
