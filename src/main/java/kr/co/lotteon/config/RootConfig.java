package kr.co.lotteon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public AppInfo appInfo(){

        String name = buildProperties.getName();
        String version = buildProperties.getVersion();

        return new AppInfo(name, version);
    }


}
