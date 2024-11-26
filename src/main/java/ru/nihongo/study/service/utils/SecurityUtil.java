package ru.nihongo.study.service.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.nihongo.study.entity.UserInfo;

public class SecurityUtil {
    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserInfo) {
            return ((UserInfo) principal).getId();
        }

        return null;
    }

    public static UserInfo getcurrentUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserInfo) {
            return ((UserInfo) principal);
        }

        return null;
    }
}