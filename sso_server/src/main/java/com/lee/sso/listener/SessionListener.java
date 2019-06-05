package com.lee.sso.listener;

import com.lee.sso.domain.ClientInfo;
import com.lee.sso.util.HttpUtil;
import com.lee.sso.util.MockDatabase;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

public class SessionListener implements HttpSessionListener {
    /**
     * 监听session创建
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    /**
     * 监听session销毁
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 从数据库中取出已登录子系统的信息,逐一通知子系统销毁信息
        HttpSession session = se.getSession();
        String token = (String) session.getAttribute("token");

        // 从数据库中删除令牌
        MockDatabase.TOKEN_CENTER.remove(token);

        // 从数据库中取出客户端的登出地址,逐一通知客户端退出系统
        List<ClientInfo> clientInfoList = MockDatabase.CLIENT_INFO.remove(token);
        try {
            for (ClientInfo info : clientInfoList) {
                // 调用子系统的登出方法
                HttpUtil.sendHttpRequest(info.getLogoutUrl(), info.getSessionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
