package cn.dbdj1201.msm.service;

import java.util.Map;

/**
 * @author yz1201
 * @date 2020-09-05 23:14
 **/
public interface IMsmService {
    /**
     * 调用阿里云短信接口，发送短信
     * @param param
     * @param phone
     * @return
     */
    boolean sendMsm(Map<String, Object> param, String phone);


}
