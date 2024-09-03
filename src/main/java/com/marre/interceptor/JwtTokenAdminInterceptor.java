package com.marre.interceptor;

import com.marre.constant.JwtClaimsConstant;
import com.marre.properties.JwtProperties;
import com.marre.utils.BaseContext;
import com.marre.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取当前线程id
//        System.out.println("线程id：" + Thread.currentThread().getId());

        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2、校验令牌
        try {
            log.info("JWT:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long Id = Long.valueOf(claims.get(JwtClaimsConstant.ID).toString());

            BaseContext.setCurrentId(Id);

            log.info("线程池抓取到当前id:{}", Id);
            //3、通过，放行
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT令牌已过期: {}", e.getMessage());
            response.setStatus(401);
            return false;
        } catch (SignatureException e) {
            log.error("JWT签名验证失败: {}", e.getMessage());
            response.setStatus(401);
            return false;
        } catch (Exception ex) {
            log.error("JWT验证失败: {}", ex.getMessage());
            response.setStatus(401);
            return false;
        }
    }
}
