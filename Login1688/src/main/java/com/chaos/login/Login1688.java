package com.chaos.login;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chaos
 * @date 2020/3/28 12:35
 */
public class Login1688 {
    public static void main(String[] args) throws Exception {
        //当登录完成后可以得到cookie信息
        Login1688 login1688 = new Login1688();
        login1688.login("username。。。", "password。。。");
    }

    public String login(String userName, String pwd) throws Exception {
        // 第一次请求，阿里巴巴登录网址
        Connection con = Jsoup
                .connect("https://login.taobao.com/member/login.jhtml");// 获取连接
        // 配置模拟浏览器
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Response rs = con.execute();// 获取响应
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
        List<Element> et = d1.select("#J_Form");// 获取form表单，可以通过查看页面源码代码得知
        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<>();
        //解析dom树
        for (Element e : et.get(0).getAllElements()) {
            if (e.attr("name").equals("TPL_username")) {
                e.attr("value", userName);// 设置用户名
            }
            if (e.attr("name").equals("TPL_password")) {
                e.attr("value", pwd); // 设置用户密码
            }
            if (e.attr("name").length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }
        }
        /**
         * 第二次请求，post表单数据，以及cookie信息
         *
         * **/
        Thread.sleep(1000);//防止连续请求，造成请求频繁

        Connection con2 = Jsoup
                .connect("https://login.taobao.com/member/login.jhtml");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        //登录数据
        System.out.println("login：" + datas);
        System.out.println(login.body());
        String result = login.body();
        // 打印登录信息
        Map<String, String> map = login.cookies();
        for (String s : map.keySet()) {
            System.out.println(s + "      " + map.get(s));
        }
        return result;
    }
}

