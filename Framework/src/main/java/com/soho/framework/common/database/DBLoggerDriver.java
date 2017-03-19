package com.soho.framework.common.database;


import com.cownew.JDBMonitor.common.ConfigFileInfo;
import com.cownew.JDBMonitor.common.ConfigFileReader;
import com.cownew.JDBMonitor.common.DBListenerInfo;
import com.cownew.JDBMonitor.common.InstanceUtils;
import com.cownew.JDBMonitor.jdbc.DBConnection;
import com.cownew.JDBMonitor.jdbc.DBDriver;
import com.cownew.JDBMonitor.jdbc.JdbcUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhongChongtao on 2017/3/6.
 */
public class DBLoggerDriver extends DBDriver {
    public synchronized Connection connect(String url, Properties info) throws SQLException {
        if (!this.acceptsURL(url)) {
            return null;
        } else {
            Matcher match = this.getURLMatcher(url);
            //noinspection ResultOfMethodCallIgnored
            match.matches();
            //noinspection ResultOfMethodCallIgnored
            match.group();
            String configFile = match.group(1);
            String realUrl = match.group(2);
            ConfigFileInfo configInfo = null;
            Object is = null;

            try {
                try {
                    is = this.getClass().getClassLoader().getResourceAsStream(configFile);
                } catch (RuntimeException var19) {
                    is = null;
                }

                if (is == null) {
                    try {
                        is = new FileInputStream(configFile);
                    } catch (RuntimeException var18) {
                        is = null;
                    }
                }

                if (is == null) {
                    throw new SQLException("config file not found:" + configFile);
                }

                configInfo = ConfigFileReader.getConfigFileInfo((InputStream) is);
                this.registerRealJdbcDriver(configInfo);
            } catch (FileNotFoundException var20) {
                throw JdbcUtils.toSQLException(var20);
            } finally {
                InstanceUtils.closeInStream((InputStream) is);
            }

            Connection cn = DriverManager.getConnection(realUrl, info);
            if (!configInfo.isActive()) {
                return cn;
            } else {
                List lisList = configInfo.getListenerInfoList();
                DBListenerInfo[] dbListenerInfos = new DBListenerInfo[lisList.size()];
                int i = 0;

                for (int n = lisList.size(); i < n; ++i) {
                    DBListenerInfo lisInfo = (DBListenerInfo) lisList.get(i);
                    dbListenerInfos[i] = lisInfo;
                }

                return new DBConnection(cn, dbListenerInfos, configFile);
            }
        }
    }

    private Matcher getURLMatcher(String url) {
        Pattern pattern = Pattern.compile("listenerconfig=(.+):url=(.+)");
        Matcher match = pattern.matcher(url);
        return match;
    }

    private synchronized void registerRealJdbcDriver(ConfigFileInfo configInfo) throws SQLException {
        List list = configInfo.getRealJdbcDriverList();
        int i = 0;

        for (int n = list.size(); i < n; ++i) {
            try {
                Class.forName(list.get(i).toString());
            } catch (ClassNotFoundException var6) {
                throw JdbcUtils.toSQLException(var6);
            }
        }

    }
}
