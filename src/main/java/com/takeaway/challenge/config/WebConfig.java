package com.takeaway.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.takeaway.challenge.converters.StringToUserIdConverter;

/**
 * @author Naveen Kumashi
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUserIdConverter());
    }
}
