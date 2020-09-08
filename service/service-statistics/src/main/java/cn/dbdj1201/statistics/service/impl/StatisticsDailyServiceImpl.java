package cn.dbdj1201.statistics.service.impl;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.api.EduClient;
import cn.dbdj1201.statistics.api.StaUcenterClient;
import cn.dbdj1201.statistics.entity.StatisticsDaily;
import cn.dbdj1201.statistics.entity.vo.StatisticsListVo;
import cn.dbdj1201.statistics.mapper.StatisticsDailyMapper;
import cn.dbdj1201.statistics.service.IStatisticsDailyService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private StaUcenterClient staUcenterClient;

    @Autowired
    private EduClient eduClient;

    //todo 其他三个字段的自动化统计也要做一下
    @Override
    public R registerCounter(String date) {
        //远程调用。
        R countAtSomeday = this.staUcenterClient.findRegisterCountAtSomeday(date);
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


    //统计当天的所有数据， 注册人数/登录人数/视频观看数量/新增视频树
    @Override
    public R statisticsFieldCounter(String date) {
        //远程调用用户模块查询注册人数。
        R countAtSomeday = this.staUcenterClient.findRegisterCountAtSomeday(date);
        Integer registerCount = (Integer) countAtSomeday.getData().get("registerCount");
        log.info("远程调用完毕，{}注册人数为{}", date, registerCount);
        if (registerCount == null) {
            return countAtSomeday;
        }

        /*
        统计新增课程数据
         */
        R addCourseCount = this.eduClient.getAddCourseCount(date);
        Integer courseCount = (Integer) addCourseCount.getData().get("courseCount");
        if (courseCount == null) {
            return addCourseCount;
        }

        /*
        查询一下当前日期是否已有记录，如果没有插入一条新记录，
        如果有了,判断数据是否是新值，只修改对应已修改过的字段
        另外一种方式就是每次都先删除该日期的统计数据记录，每次都插入新的。
         */
        StatisticsDaily currentStatistics = this.getOne(new QueryWrapper<StatisticsDaily>().eq("date_calculated", date));
        if (currentStatistics == null) {
            StatisticsDaily statisticsDaily = new StatisticsDaily();
            statisticsDaily.setDateCalculated(date);
            statisticsDaily.setRegisterNum(registerCount);
            statisticsDaily.setCourseNum(courseCount);
            statisticsDaily.setDateCalculated(date);
            //当天登录人次跟视频观看总数如何监控？
            statisticsDaily.setLoginNum(RandomUtil.randomInt(10000, 50000));
            statisticsDaily.setVideoViewNum(RandomUtil.randomInt(20000, 1000000));
            this.save(statisticsDaily);
            log.info("入库的统计数据-{}", statisticsDaily);
        } else {
            if (!registerCount.equals(currentStatistics.getRegisterNum())) {
                currentStatistics.setRegisterNum(registerCount);
                log.info("修改了注册人数-{}", currentStatistics);
            }

            if (!courseCount.equals(currentStatistics.getCourseNum())) {
                currentStatistics.setRegisterNum(courseCount);
                log.info("修改了新增视频-{}", currentStatistics);
            }
            this.updateById(currentStatistics);
        }
        return R.success().message("统计数据入库完成");
    }

    @Override
    public Map<String, Object> getStaData(StatisticsListVo statisticsListVo) {
        String type = statisticsListVo.getType();
        String begin = statisticsListVo.getBegin();
        String end = statisticsListVo.getEnd();

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isEmpty(type) || StrUtil.isEmpty(begin) || StrUtil.isEmpty(end)) {
            log.error("空数据能放到这里的？");
            throw new GOLException(20001, "?");
        }

        queryWrapper.select("date_calculated", type);
        queryWrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dailies = this.list(queryWrapper);

        List<String> dates = dailies.stream().map(StatisticsDaily::getDateCalculated).collect(Collectors.toList());

        List<Integer> dataList;

        switch (type) {
            case "register_num":
                dataList = dailies.stream().map(StatisticsDaily::getRegisterNum).collect(Collectors.toList());
                break;
            case "login_num":
                dataList = dailies.stream().map(StatisticsDaily::getLoginNum).collect(Collectors.toList());
                break;
            case "video_view_num":
                dataList = dailies.stream().map(StatisticsDaily::getVideoViewNum).collect(Collectors.toList());
                break;
            case "course_num":
                dataList = dailies.stream().map(StatisticsDaily::getCourseNum).collect(Collectors.toList());
                break;
            default:
                throw new GOLException(20001, "?");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", dates);
        map.put("numDataList", dataList);
        log.info("前台请求的统计数据集-{}", map);
        return map;
    }

    @Override
    public Map<String, Object> getStatisticsData(StatisticsListVo statisticsListVo) {
        String begin = statisticsListVo.getBegin();
        String end = statisticsListVo.getEnd();

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isEmpty(begin) || StrUtil.isEmpty(end)) {
            log.error("空数据能放到这里的？");
            throw new GOLException(20001, "?");
        }

        queryWrapper.select("date_calculated", "register_num", "login_num", "video_view_num", "course_num");
        queryWrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dailies = this.list(queryWrapper);

        List<String> dates = dailies.stream().map(StatisticsDaily::getDateCalculated).collect(Collectors.toList());

        List<Integer> registerNumList = dailies.stream().map(StatisticsDaily::getRegisterNum).collect(Collectors.toList());
        List<Integer> loginNumList = dailies.stream().map(StatisticsDaily::getLoginNum).collect(Collectors.toList());
        List<Integer> videoNumList = dailies.stream().map(StatisticsDaily::getVideoViewNum).collect(Collectors.toList());
        List<Integer> courseNumList = dailies.stream().map(StatisticsDaily::getCourseNum).collect(Collectors.toList());


        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", dates);
        map.put("registerNumList", registerNumList);
        map.put("loginNumList", loginNumList);
        map.put("videoNumList", videoNumList);
        map.put("courseNumList", courseNumList);
        log.info("前台请求的统计数据集-{}", map);
        return map;
    }


}