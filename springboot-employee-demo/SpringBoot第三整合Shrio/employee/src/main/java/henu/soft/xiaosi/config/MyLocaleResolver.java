package henu.soft.xiaosi.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取语言请求参数
        String language = httpServletRequest.getParameter("l");
        //默认
        Locale locale = Locale.getDefault();

        //如果请求的连接携带了国际化语言参数
        if(!StringUtils.isEmpty(language)){
            String[] split = language.split("_");
            //拿出来参数地区-语言
            return new Locale(split[0], split[1]);
        }


        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
