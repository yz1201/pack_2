package cn.dbdj1201.oss.service.impl;

import cn.dbdj1201.oss.component.OssProperties;
import cn.dbdj1201.oss.service.IOssService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 15:29
 */
@Service
@Slf4j
public class OssServiceImpl implements IOssService {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String uploadFileAvatar(MultipartFile multipartFile) {

        log.info("upload ossProperties - {}", ossProperties);

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = this.ossProperties.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = this.ossProperties.getKeyId();
        String accessKeySecret = this.ossProperties.getKeySecret();

        StringBuilder url = new StringBuilder("https://");
        String filename;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            PutObjectRequest putObjectRequest;

            /*
            处理文件名字，防止相同文件名产生覆盖，以年月日对文件分类，即年文件夹/月文件夹/日文件夹/uuid+源文件名
             */
            filename = IdUtil.simpleUUID() + multipartFile.getOriginalFilename();
            String dateStr = DateUtil.date().toString("yyyy/MM/dd");
            filename = dateStr + "/" + filename;
            // 创建PutObjectRequest对象。
            putObjectRequest = new PutObjectRequest(this.ossProperties.getBucketName(), filename, multipartFile.getInputStream());

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            log.error("上传失败");
            e.printStackTrace();
            return null;
        }
        url.append(this.ossProperties.getBucketName()).append(".").append(endpoint).append("/").append(filename);
        return url.toString();
    }
}
