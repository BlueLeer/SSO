package com.lee.sso.domain;

/**
 * 已被授权的客户端信息
 */
public class ClientInfo {
    private String sessionId;
    private String logoutUrl;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "sessionId='" + sessionId + '\'' +
                ", logoutUrl='" + logoutUrl + '\'' +
                '}';
    }
}
