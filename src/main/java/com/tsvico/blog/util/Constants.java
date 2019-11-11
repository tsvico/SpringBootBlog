package com.tsvico.blog.util;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/11/11 13:35
 * 功能 类内常量定义枚举
 */
public class Constants {
    /**
     * 配置常量
     */
    public enum config{
        //session 中user的值
        UserSession("user","用户");

        //配置常量的值
        private String value;
        //配置常量的注释
        private String name;

        config(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
