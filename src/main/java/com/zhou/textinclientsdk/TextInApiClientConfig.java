package com.zhou.textinclientsdk;

import com.zhou.textinclientsdk.client.TextInApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("textin.client")   // 读取配置文件
@Data
@ComponentScan  // 扫包
public class TextInApiClientConfig {

    private String appId;

    private String secretCode;

    @Bean
    public TextInApiClient yuApiClient() {
        return new TextInApiClient(appId, secretCode);
    }



}
