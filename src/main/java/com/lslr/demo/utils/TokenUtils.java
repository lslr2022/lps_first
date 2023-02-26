package com.lslr.demo.utils;


import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtils {
    //生成Token
    public  static String getToken(String userId,String sign){
       return JWT.create().withAudience(userId)  //将userid保存在token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),1))//一个小时后token 过期
                .sign(Algorithm.HMAC256(sign));    //用password作为密钥
    }

}
