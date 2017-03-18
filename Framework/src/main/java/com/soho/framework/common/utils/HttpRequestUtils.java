package com.soho.framework.common.utils;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by ZhongChongtao on 2017/2/25.
 */
public class HttpRequestUtils {
    public static String getRequestParamByDef(String paramKey, String def) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "is not web request");
        String parameter = requestAttributes.getRequest().getParameter(paramKey);
        if (StringUtils.isEmpty(parameter)) {
            return def;
        }
        return parameter;
    }

    public static Object getRequestSessionAttribute(String attributeKey) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "is not web request");
        return requestAttributes.getAttribute(attributeKey, RequestAttributes.SCOPE_SESSION);
    }
}

