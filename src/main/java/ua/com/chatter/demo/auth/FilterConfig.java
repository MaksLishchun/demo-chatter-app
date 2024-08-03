package ua.com.chatter.demo.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitFilter> loggingFilter() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RateLimitFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}

