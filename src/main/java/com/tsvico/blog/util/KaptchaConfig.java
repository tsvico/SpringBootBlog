package com.tsvico.blog.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/9/6 21:33
 * 功能
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {

        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        String s = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHMNQRSTYZ";
        properties.setProperty("kaptcha.border", "no"); //是否有边框，默认为yes，可选yes、no
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.char.string", s);
        //properties.setProperty("kaptcha.textproducer.char.string", str.toString());
        properties.setProperty("kaptcha.image.width", "110");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.session.key", "code"); //存储session key
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }
}
