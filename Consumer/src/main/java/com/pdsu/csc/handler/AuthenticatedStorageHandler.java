package com.pdsu.csc.handler;

import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.exception.web.user.UserNotLoginException;
import com.pdsu.csc.utils.HttpUtils;
import com.pdsu.csc.utils.RedisUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 半梦
 * @create 2021-02-21 22:09
 */
public abstract class AuthenticatedStorageHandler
        implements AbstractHandler, AuthenticationHandler {

    /**
     * 用来存储已经通过认证的
     */
    protected Map<String, UserInformation> users;

    public AuthenticatedStorageHandler() {
        users = new ConcurrentHashMap<>();
    }

    /**
     * 添加已认证用户
     */
    public UserInformation add(@NonNull String sessionId, @NonNull UserInformation user) {
        return users.put(sessionId, user);
    }

    /**
     * 获取已认证用户
     */
    @Nullable
    public UserInformation get(@Nullable String sessionId) {
        if(sessionId == null) {
            return null;
        }
        return users.get(sessionId);
    }

    /**
     * 是否包含该用户
     */
    public boolean contains(@NonNull String sessionId) {
        return users.containsKey(sessionId);
    }

    /**
     * 删除一个用户
     */
    public UserInformation remove(@Nullable String sessionId) {
        if(sessionId == null) {
            return null;
        }
        return users.remove(sessionId);
    }

    /**
     * 定时查询用户是否认证，非认证则删除。
     */
    public void isAuthentication(RedisUtils redisUtils) {
    }

    public abstract UserInformation compulsionGet(String sessionId);

    @Nullable
    public UserInformation compulsionGet(HttpServletRequest request) {
        String sessionId = HttpUtils.getSessionId(request);
        if(sessionId == null) {
            return null;
        }
        return compulsionGet(sessionId);
    }

    @Override
    public void loginOrNotLogin(@NonNull HttpServletRequest request) throws UserNotLoginException {
        if(HttpUtils.getSessionId(request) == null) {
            throw new UserNotLoginException(NOT_LOGIN);
        }
    }

    @Override
    public void loginOrNotLogin(UserInformation user) throws UserNotLoginException {
    }

}
