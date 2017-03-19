package com.soho.framework.configuration.shiro.realm;

import com.soho.framework.common.utils.TypeChecker;
import com.soho.framework.security.entity.UserEntity;
import com.soho.framework.security.repository.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MyShiroRealm
 */
public class EmailShiroRealm extends AuthorizingRealm {


    private static final Logger logger = LoggerFactory.getLogger(EmailShiroRealm.class);
    //
//    @Autowired
//    private IUserDao userDao;
    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private UserRepository userRepository;

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("execute shiro authority check");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        UserEntity userEntity = userRepository.findByUserName(loginName);
        if (!TypeChecker.isNull(userEntity)) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addStringPermission("admin");
            return info;
        }
        return null;
        //到数据库查是否有此对象
//        User user=userDao.findByName(loginName);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        if(user!=null){
//            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
//            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//            //用户的角色集合
//            info.setRoles(user.getRolesName());
//            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
//            List<Role> roleList=user.getRoleList();
//            for (Role role : roleList) {
//            }
//            // 或者按下面这样添加
//            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
////            simpleAuthorInfo.addRole("admin");
//            //添加权限
////            simpleAuthorInfo.addStringPermission("admin:manage");
////            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
//            return info;
//        }
//        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        logger.info("get authority info");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserEntity userEntity = userRepository.findByEmail(token.getUsername());
        if (!TypeChecker.isNull(userEntity)) {
            return new SimpleAuthenticationInfo(
                    userEntity.getUserName(),
                    userEntity.getPassword(),
                    ByteSource.Util.bytes(userEntity.getUserName() + userEntity.getSalt()),
                    userEntity.getUserName());
        }
        return null;
    }
}