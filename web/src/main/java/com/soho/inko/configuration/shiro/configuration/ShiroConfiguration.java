package com.soho.inko.configuration.shiro.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.soho.inko.configuration.shiro.configuration.properties.ShiroFilterProperties;
import com.soho.inko.configuration.shiro.configuration.properties.ShiroPasswordProperties;
import com.soho.inko.configuration.shiro.filter.SysUserFilter;
import com.soho.inko.configuration.shiro.realm.EmailShiroRealm;
import com.soho.inko.configuration.shiro.realm.UserNameShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Shiro 配置
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 *
 * @version v.0.1
 */
@Configuration
@EnableConfigurationProperties({ShiroPasswordProperties.class, ShiroFilterProperties.class})
@AutoConfigureAfter(ShiroFilterConfiguration.class)
public class ShiroConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(
            SecurityManager securityManager,
            FormAuthenticationFilter formAuthenticationFilter,
            PermissionsAuthorizationFilter permissionsAuthorizationFilter,
            ShiroFilterProperties shiroFilterProperties,
            SysUserFilter sysUserFilter) {
        logger.info("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //全路径Put到map中
        shiroFilterProperties.getFilterChainDefinition().put("/**", "anon,sysUser");

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterProperties.getFilterChainDefinition());
        //过滤器
        shiroFilterFactoryBean.setFilters(new HashMap<>());
        shiroFilterFactoryBean.getFilters().put("authc", formAuthenticationFilter);
        shiroFilterFactoryBean.getFilters().put("perms", permissionsAuthorizationFilter);
        shiroFilterFactoryBean.getFilters().put("sysUser", sysUserFilter);
//        shiroFilterFactoryBean.getFilters().put("logout", logoutFilter);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(UserNameShiroRealm userNameShiroRealm, EmailShiroRealm emailShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>(2);
        //根据用户名验证realm
        realms.add(userNameShiroRealm);
        //根据邮箱，验证realm
        realms.add(emailShiroRealm);
        securityManager.setRealms(realms);
        return securityManager;
    }

    @Bean
    public UserNameShiroRealm userNameShiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserNameShiroRealm myShiroRealm = new UserNameShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myShiroRealm;
    }

    @Bean
    public EmailShiroRealm emailShiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        EmailShiroRealm emailShiroRealm = new EmailShiroRealm();
        emailShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return emailShiroRealm;
    }


    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(ShiroPasswordProperties shiroPasswordProperties) {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(shiroPasswordProperties.getAlgorithmName());
        hashedCredentialsMatcher.setHashIterations(shiroPasswordProperties.getHashIterations());
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


//    @Bean("rememberMeManager")
//    public CookieRememberMeManager cookieRememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        try {
//            cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
//        } catch (Base64DecodingException e) {
//            e.printStackTrace();
//        }
//        cookieRememberMeManager.setCookie(sessionIdCookie);
//        return cookieRememberMeManager;
//    }
//
//    @Bean("sessionIdCookie")
//    public Cookie cookie() {
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setMaxAge(7 * 24 * 60 * 60);
//        return simpleCookie;
//    }
//
//    @Bean("sessionDAO")
//    public SessionDAO sessionDAO() {
//        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
//        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
//        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator);
//        return enterpriseCacheSessionDAO;
//    }
//
//    @Bean("sessionIdGenerator")
//    public SessionIdGenerator sessionIdGenerator() {
//        return new JavaUuidSessionIdGenerator();
//    }
//
//    @Bean("sessionValidationScheduler")
//    public SessionValidationScheduler sessionValidationScheduler() {
//        QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
//        quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
//        quartzSessionValidationScheduler.setSessionManager(sessionManager);
//        return quartzSessionValidationScheduler;
//    }
//
//    @Bean("sessionManager")
//    public ValidatingSessionManager sessionManager() {
//        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
//        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
//        defaultWebSessionManager.setDeleteInvalidSessions(true);
//        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
//        defaultWebSessionManager.setSessionDAO(sessionDAO);
//        defaultWebSessionManager.setSessionIdCookieEnabled(true);
//        defaultWebSessionManager.setSessionIdCookie(cookie);
//        defaultWebSessionManager.setSessionValidationScheduler(sessionValidationScheduler);
//        return defaultWebSessionManager;
//    }
}