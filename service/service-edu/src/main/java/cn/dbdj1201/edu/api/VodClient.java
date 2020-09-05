package cn.dbdj1201.edu.api;

import cn.dbdj1201.common.utils.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-05 10:42
 */
@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/vod/video/delete/{videoSourceId}")
    R deleteVideoByVideoId(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("/vod/video/deleteBatch")
    R deleteVideoByVideoIds(@RequestParam("ids") String... ids);
}
