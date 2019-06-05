package com.lee.sso.util;

import com.lee.sso.domain.ClientInfo;

import java.util.*;

/**
 * 模拟数据库
 */
public class MockDatabase {
    public static final Set<String> TOKEN_CENTER = new HashSet<>(8);
    public static final Map<String, List<ClientInfo>> CLIENT_INFO = new HashMap<>(8);
}
