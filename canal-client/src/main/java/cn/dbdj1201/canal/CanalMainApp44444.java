package cn.dbdj1201.canal;

import cn.dbdj1201.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-09 10:02
 */
@SpringBootApplication
public class CanalMainApp44444 implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalMainApp44444.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        canalClient.run();
    }
}
