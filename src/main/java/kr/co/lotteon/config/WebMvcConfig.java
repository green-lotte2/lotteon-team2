package kr.co.lotteon.config;

import kr.co.lotteon.interceptor.AppInfoInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AppInfo appInfo;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppInfoInterceptor(appInfo));
    }
    /*
    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
        String defaultPath = System.getProperty("user.dir") + File.separator + "src";
        String path = defaultPath + File.separator + "product" + File.separator;
        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:///" + path);
    }
    */
}
