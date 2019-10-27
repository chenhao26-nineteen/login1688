package com.chaos.sso.main.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Chaos
 * @Data 添加getter/settrt方法
 *  @NoArgsConstructor 生成无参构造
 *  @AllArgsConstructor 生成全参构造
 *  @Accessors(chain = true) 允许嵌套调用,例如 new User().setUsername("张三").setPassword("12131")
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private String id;
    private String username;
    private String password;
    private String nickname;
}
