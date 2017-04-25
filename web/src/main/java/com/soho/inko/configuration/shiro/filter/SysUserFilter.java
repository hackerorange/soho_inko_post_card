package com.soho.inko.configuration.shiro.filter;

import com.soho.inko.configuration.shiro.Constants;
import com.soho.inko.database.entity.UserEntity;
import com.soho.inko.security.service.UserCacheService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

    private final UserCacheService userCacheService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public SysUserFilter(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        if (!StringUtils.isEmpty(username)) {
            UserEntity userEntity = userCacheService.findByUserName(username);
            request.setAttribute(Constants.CURRENT_USER, userEntity);
            request.setAttribute("tokenId", userEntity.getId());
//            logger.info("已经登录");
        } else {
            request.setAttribute(Constants.CURRENT_USER, null);
//            logger.info("用户信息为NULL");
        }
        return true;
    }
}
