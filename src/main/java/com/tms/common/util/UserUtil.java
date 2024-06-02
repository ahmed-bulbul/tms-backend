package com.tms.common.util;

import com.tms.common.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class UserUtil {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isUserInOrganization(Long organizationId) {
        return getCurrentUser().getOrganization().getId().equals(organizationId);
    }

    public static boolean isUserInOrganization(User user, Long organizationId) {
        return user.getOrganization().getId().equals(organizationId);
    }

    public static Long getOrganizationId() {
        return getCurrentUser().getOrganization().getId();
    }


}
