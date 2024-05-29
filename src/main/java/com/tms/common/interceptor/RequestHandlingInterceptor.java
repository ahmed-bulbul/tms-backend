package com.tms.common.interceptor;


import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.loader.DefaultAccessRightLoader;
import com.tms.common.models.AccessRight;
import com.tms.common.models.Role;
import com.tms.common.models.User;
import com.tms.common.security.jwt.JwtUtils;
import com.tms.common.service.UserService;
import com.tms.common.util.Helper;
import com.tms.common.util.NumberUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RequestHandlingInterceptor implements HandlerInterceptor {

    private static final String SLASH = "/";
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final Helper helper;
    /**
     * Autowired constructor
     *
     * @param userService {@link UserService}
     * @param jwtUtils    {@link JwtUtils}
     * @param helper      {@link Helper}
     */
    @Autowired
    public RequestHandlingInterceptor(@Lazy UserService userService, JwtUtils jwtUtils, Helper helper) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.helper = helper;
    }

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(ApplicationConstant.TRACE_ID, UUID.randomUUID().toString());
        String requestURI = helper.getRequestUri();

        if (!includesInWhiteListedUrls(requestURI)) {
            String jwt = helper.parseJwt();
            String username = jwtUtils.getUserNameFromJwtToken(jwt);

            Map pathVariables =(Map) request.getAttribute(
                    HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String methodType = request.getMethod();

            Optional<User> user = userService.findByUsername(username);
            user.ifPresent(value -> validateUserAccessPermission(value, requestURI, pathVariables, methodType));

        }

        return true;
    }

    private boolean includesInWhiteListedUrls(String path) {
        return Arrays.stream(ApplicationConstant.WHITE_LIST_URLS)
                .anyMatch(path::startsWith);
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) {
    }

    private void validateUserAccessPermission(User user, String requestURI, Map pathVariables, String methodType) {
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            if (Objects.equals(role.getId(), ApplicationConstant.SUPER_ADMIN_ROLE_ID)) {
                return;
            }
        }

        String[] requestURIArr = requestURI.split(SLASH);
        int length = pathVariables.size();
        int requestURIArrLastIndex = requestURIArr.length - 1;

        while (length > 0 && requestURIArr.length > length) {
            requestURIArr = ArrayUtils.remove(requestURIArr, requestURIArrLastIndex);
            length--;
            requestURIArrLastIndex--;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : requestURIArr) {
            sb.append(s).append(SLASH);
        }
        sb.append(methodType.toLowerCase());

        Set<Integer> accessRightSet = new HashSet<>();

        for (Role role : roles) {
            if (ApplicationConstant.roleMap.containsKey(role.getId())) {
                accessRightSet.addAll(ApplicationConstant.roleMap.get(role.getId()));
            } else {
                Set<Integer> roleAccessRightSet = role.getAccessRightSet().stream()
                        .map(AccessRight::getId)
                        .collect(Collectors.toSet());
                accessRightSet.addAll(roleAccessRightSet);
                ApplicationConstant.roleMap.put(role.getId(), roleAccessRightSet);
            }
        }
        ApplicationConstant.roleMap.clear(); //  This is new line added ... Testing purpose......

        Integer accessRightId = NumberUtil.convertToInteger(
                DefaultAccessRightLoader.DEFAULT_ACCESS_MAP.get(sb.toString()),
                ApplicationConstant.DEFAULT_PERMISSION_ID
        );
        if (Objects.equals(accessRightId, ApplicationConstant.DEFAULT_PERMISSION_ID)) {
            return;
        }

        if (!accessRightSet.contains(accessRightId)) {
//            throw new AppServerException(
//                    ErrorId.INVALID_ACCESS_PERMISSION, HttpStatus.BAD_REQUEST, MDC.get(ApplicationConstant.TRACE_ID)
//            );

        }

    }
}
