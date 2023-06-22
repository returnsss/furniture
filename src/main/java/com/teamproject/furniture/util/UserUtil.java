package com.teamproject.furniture.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

public class UserUtil {

    public static String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }

    public static String getUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
