package com.soho.inko.configuration.shiro.configuration;

import com.soho.inko.configuration.shiro.filter.SysUserFilter;
import com.soho.inko.security.service.UserCacheService;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Configuration
public class ShiroFilterConfiguration {
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter() {
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        formAuthenticationFilter.setUsernameParam("userName");
        formAuthenticationFilter.setPasswordParam("password");
        formAuthenticationFilter.setFailureKeyAttribute("loginError");
        return formAuthenticationFilter;
    }

    @Bean
    public PermissionsAuthorizationFilter permissionsAuthorizationFilter() {
        PermissionsAuthorizationFilter permissionsAuthorizationFilter = new PermissionsAuthorizationFilter();
        permissionsAuthorizationFilter.setLoginUrl("/login");
        return permissionsAuthorizationFilter;
    }

    @Bean
    public SysUserFilter sysUserFilter(UserCacheService userCacheService) {
        return new SysUserFilter(userCacheService);
    }

//    @Bean
//    public LogoutFilter logoutFilter() {
//        LogoutFilter logoutFilter = new LogoutFilter();
//        logoutFilter.setRedirectUrl("login");
//        return logoutFilter;
//    }
}
