package com.soho.inko.utils;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings({"unused", "SimplifiableIfStatement"})
public final class TypeChecker {
    private TypeChecker() {
    }

    public static boolean isBlueNumber(Object obj) {
        if (isNull(obj)) {
            return false;
        }
        // ch
        if (isEmptyObject(obj)) {
            return false;
        }
        // return
        return StringUtils.isLegalContext(String.valueOf(obj), "0123456789.+-");
    }

    public static boolean isNumber(Object obj) {
        if (obj instanceof Number) {
            return true;
        } else if (!isBlueNumber(obj)) {
            return false;
        } else {
            try {
                double v = Double.parseDouble(String.valueOf(obj));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static boolean isInteger(Object obj) {
        if (!isBlueNumber(obj)) {
            return false;
        } else {
            try {
                int i = Integer.parseInt(String.valueOf(obj));
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isPlusInteger(Object obj) {
        if (!isBlueNumber(obj)) {
            return false;
        } else {
            try {
                return Integer.parseInt(String.valueOf(obj)) >= 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isLong(Object obj) {
        if (isNull(obj)) {
            return false;
        } else {
            try {
                long e = Long.parseLong(String.valueOf(obj));
                return true;
            } catch (Exception var3) {
                return false;
            }
        }
    }

    public static boolean isEmptyObject(Object obj) {
        if (isNull(obj)) {
            return false;
        }
        if (obj instanceof Object[]) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return isEmpty((Collection) obj);
        }
        if (obj instanceof Map) {
            return isEmpty((Map) obj);
        }
        // String obj
        if (obj instanceof String) {
            return isEmpty((String) obj);
        } else {
            // some else case
            return false;
        }
    }

    public static boolean isEmptySQL(String sql) {
        if (!isNull(sql) && !isEmpty(sql)) {
            int i = 0;
            for (int j = sql.length(); i < j; ++i) {
                if (sql.charAt(i) != 37) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object[] arrays) {
        return isNull(arrays) || (arrays.length == 0);
    }

    public static boolean isEmptyStringArrays(String... arrays) {
        if (isEmpty(arrays)) {
            return true;
        } else {
            for (String item : arrays) {
                if (!isEmpty(item)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isAllStringArraysNotEmpty(String... arrays) {
        if (isEmpty(arrays)) {
            return false;
        } else {
            for (String item : arrays) {
                if (isEmpty(item)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isStringArraysOnlyOneNotEmpty(String... arrays) {
        if (isEmpty(arrays)) {
            return false;
        } else {
            for (String item : arrays) {
                if (!isEmpty(item)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isEmpty(StringBuffer sb) {
        return isNull(sb) || (sb.length() == 0);
    }

    public static boolean isEmpty(Collection collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || (str.trim().length() == 0) || "null".equalsIgnoreCase(str);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isOrNull(Object... objs) {
        if (isNull(objs)) {
            return true;
        } else {
            for (Object obj : objs) {
                if (isNull(obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isTrue(String value) {
        return "true".equalsIgnoreCase(value);
    }

    public static boolean isSpecialTrue(String value) {
        return "true".equalsIgnoreCase(value)
                || "t".equalsIgnoreCase(value)
                || "a".equalsIgnoreCase(value)
                || "y".equalsIgnoreCase(value)
                || "yes".equalsIgnoreCase(value);
    }

    public static boolean isSpecialFalse(String value) {
        return "false".equalsIgnoreCase(value)
                || "f".equalsIgnoreCase(value)
                || "n".equalsIgnoreCase(value)
                || "no".equalsIgnoreCase(value)
                || isEmpty(value);
    }

    public static boolean isFalse(String value) {
        return "false".equalsIgnoreCase(value) || isEmpty(value);
    }

    public interface TypeMoreChecker {
        boolean validate(Object... var1);
    }
}

