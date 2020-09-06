package cn.dbdj1201.msm.service.impl;

import cn.dbdj1201.msm.component.MsmProperties;
import cn.dbdj1201.msm.service.IMsmService;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yz1201
 * @date 2020-09-05 23:14
 **/
@Service
@Slf4j
public class MsmServiceImpl implements IMsmService {

    @Autowired
    private MsmProperties msmProperties;

    @Override
    public boolean sendMsm(Map<String, Object> param, String phone) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                msmProperties.getAliKey(),
                msmProperties.getAliSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "ShoppingCity"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_186953758"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("发送短信完成-{}", response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            log.error("发送失败了？-{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
