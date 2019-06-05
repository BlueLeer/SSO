package com.lee.client1.interceptor;

import com.lee.client1.util.Constant;
import com.lee.client1.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with : IntelliJ IDEA
 * User : KingIsHappy
 * Date : 2018/10/7
 * Time : 22:08
 * Description :
 */
public class MainInterceptor implements HandlerInterceptor {

    @Autowired
    private Constant constant;

    /**
     * 进入Handler方法之前执行
     * 用于身份认证/身份授权
     * 比如身份认证,如果认证不通过表示当前用户没有登录,需要此方法拦截不再向下执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // : 检查是否有局部会话
        // 从session中获取"isLogin"是否为true
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin != null && isLogin) {
            // 已经登录过了,放行
            return true;
        }

        // : 检查地址栏中是否携带了token信息
        String token = request.getParameter("token");
        // 校验token
        if (!StringUtils.isEmpty(token)){
            String verfiyUrl = constant.getSERVER_HOST()+constant.getSSO_TOKEN_VERIFY();
            Map<String,String>  params = new HashMap<>();
            // 添加待验证的token
            params.put("token",token);
            // 添加该客户端的登出地址
            params.put("logoutUrl",constant.getCLIENT_HOST()+constant.getCLIENT_LOGOUT_URL());
            // 添加sessionId
            params.put("sessionId",session.getId());

            String s = HttpUtil.sendHttpRequest(verfiyUrl, params);
            if ("true".equals(s)){
                // 如果返回true字符串,说明是由统一认证中心产生的
                session.setAttribute("isLogin",true);
                session.setAttribute("serverLogOutUrl",constant.getSERVER_HOST()+constant.getSSO_LOGOUT_URL());
                return true;
            }
        }

        // : 重定向到SSO(统一认证中心)
        StringBuilder sb = new StringBuilder();
        sb.append(constant.getSERVER_HOST())
                .append("/checkLogin?redirectUrl=")
                .append(constant.getCLIENT_HOST() + request.getRequestURI()); // 注:getRequestURI()返回除去域名(IP)部分的路径

        response.sendRedirect(sb.toString());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
