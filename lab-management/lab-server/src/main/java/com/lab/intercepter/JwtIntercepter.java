package com.lab.intercepter;

import com.lab.context.BaseContext;
import com.lab.properties.JwtProperties;
import com.lab.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtIntercepter implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验Jwt
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller方法还是其他的静态资源
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //校验令牌
//        try {
        log.info("jwt校验:{}",token);
        log.info("签名密钥：{}",jwtProperties.getUserSecretKey());
        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
        Long suerID = Long.valueOf(claims.get("userId").toString());
        log.info("当前用户的id:{}",suerID);
        BaseContext.setCurrentId(suerID);
        return true;
//        }catch (Exception e){
//            log.info("jwt校验不正确");
//            response.setStatus(401);
//            return false;
//        }
    }
}
