package com.learning.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    // resolve request
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // get the lan parameter in the request
        String language = request.getParameter("lan");

        Locale locale = Locale.getDefault(); // if nothing, just use default

        // if found
        if (!StringUtils.isEmpty(language)) {
            // ex: zh_CN
            String[] split = language.split("_");
            // Language, Region
            locale = new Locale(split[0], split[1]);

        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
