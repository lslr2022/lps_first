package com.lslr.demo.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lslr.demo.entity.User;
import com.lslr.demo.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Component
public class TokenUtils {
    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    //生成Token
    public  static String getToken(String userId,String sign){
       return JWT.create().withAudience(userId)  //将userid保存在token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),1))//一个小时后token 过期
                .sign(Algorithm.HMAC256(sign));    //用password作为密钥
    }


    //获取当前用户的登录信息
    //返回user对象
    public static User getCurrentUser() {
        try {
            //取出token
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


}
