package cn.dbdj1201.edu.api.fallback;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.api.VodClient;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-05 14:45
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideoByVideoId(String videoSourceId) {
        return R.error().message("删除单个视频，远程调用失败，删除超时或者对方服务器已崩");
    }

    @Override
    public R deleteVideoByVideoIds(String... ids) {
        return R.error().message("批量删除视频，远程调用失败，删除超时或者对方服务器已崩");
    }
}
