package com.chaos.sso.main.controller;
import	java.util.HashSet;

import com.chaos.sso.main.pojo.User;
import com.chaos.sso.main.utils.LoginCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Chaos
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Set<User> userSet = null;
    // 模拟数据库存放数据
    static {
        userSet = new HashSet<User> ();
        userSet.add(new User("01","zhangsan","123456","晴空下的彩虹"));
        userSet.add(new User("02","lisi","666666","吃鱼的猫"));
        userSet.add(new User("03","wangwu","111111","你好鸭"));
        userSet.add(new User("04","zhaoliu","222222","吃竹子的熊猫"));
    }

    @PostMapping("/doLogin")
    public String login(User user, HttpSession session, HttpServletResponse response){
        if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
            //如果有，则校验
            Optional<User> first = userSet.stream().filter(userEntity -> userEntity.getUsername().equals(user.getUsername()) &&
                    userEntity.getPassword().equals(user.getPassword())).findFirst();

            // 如果查询出来的了数据，将数据存放到
            if (first.isPresent()) {
                String target = (String) session.getAttribute("target");

                //放入一个token和用户实体,token可以使用uuid
                String token = UUID.randomUUID().toString();
                //将token放到cookie里面
                Cookie cookie = new Cookie("TOKEN",token);
                //设置cookie作用的域
                cookie.setDomain("codeshop.com");
                cookie.setPath("/");
                // 添加cookie，通过response
                response.addCookie(cookie);

                System.out.println(cookie.getValue());

                //将用户信息放到模拟的Redis里面
                LoginCache.map.put(token,first.get());
                session.removeAttribute("msg");
                return "redirect:"+target;
            }else {
                session.setAttribute("msg","用户名或密码错误！");
                return "login";
            }

        }
        session.setAttribute("msg","用户名或密码不能为空！");
        return "login";
    }

    /**
     * 为各个子系统提供一个验证token的接口,返回一个ResponseEntity<User>，
     * @param token
     * @return
     */
    @GetMapping("/checkToken")
    @ResponseBody
    public ResponseEntity<User> checkToken(String token){
        if (!StringUtils.isEmpty(token)){
            LoginCache loginCache = new LoginCache();
            User user= (User)  LoginCache.map.get(token);
            if (user!=null) {
                //如果当前登录成功，就返回登录成功后的用户
                return ResponseEntity.ok(user);
            }else {
                return new ResponseEntity<> (null,HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<> (null,HttpStatus.BAD_REQUEST);
    }
}