package com.dg.myblog.global.utils;

import com.dg.myblog.global.GlobalConstants;
import com.dg.myblog.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-05 15:55
 */
public class SessionUtils {
    /**
     * 获取当前Session
     *
     * @return 当前Session
     */
    public static Session getCurrentSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject == null ? null : subject.getSession();
    }

    /**
     * 获取当前Session的ID
     *
     * @return 当前Session的ID
     */
    public static String getCurrentSessionId() {
        return getCurrentSession() == null ? null : getCurrentSession().getId().toString();
    }

    /**
     * 获取当前的用户对象
     *
     * @return 当前用户
     */
    public static User getCurrentUser() {
        return getCurrentSession() == null ? null : (User) getCurrentSession().getAttribute(GlobalConstants.USER_TOKEN);
    }

    /**
     * 获取当前用户的ID
     *
     * @return 当前用户的ID
     */
    public static Long getCurrentUserId() {
        return getCurrentUser() == null ? null : getCurrentUser().getId();
    }

    /**
     * 存储参数到Session
     *
     * @param key   存储的key
     * @param value 存储的value
     */
    public static void setAttribute(String key, Object value) {
        Session session = getCurrentSession();
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

}
