package com.chaos.sso.main.controller;

import com.chaos.sso.main.pojo.User;
import com.chaos.sso.main.utils.LoginCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @author Chaos
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "") String target,
                          HttpSession session, @CookieValue(required = false,value ="TOKEN") Cookie cookie){
        //如果携带的参数没有，则返回登录页面
        if (StringUtils.isEmpty(target)) {
            target = "http://www.main.codeshop.com:9010/";
        }
        if (cookie!=null) {
            String token = cookie.getValue();
            User user = (User) LoginCache.map.get(token);
            if (user!=null) {
                //发送http请求，请求接口，当前token是否在LoginCache中
                return "redirect:" + target;
            }
        }
        //将target放到session里面
        session.setAttribute("target",target);
        return "login";
    }
}
