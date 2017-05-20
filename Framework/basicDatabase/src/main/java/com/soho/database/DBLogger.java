//package com.soho.database;
//
//import com.cownew.JDBMonitor.common.IDBListener;
//import com.cownew.JDBMonitor.common.LoggerException;
//import com.cownew.JDBMonitor.common.SQLInfo;
//import com.soho.inko.utils.StringUtils;
//import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
//import org.hibernate.engine.jdbc.internal.Formatter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
///**
// * Created by ZhongChongtao on 2017/3/3.
// */
//public class DBLogger implements IDBListener {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void init(String s) throws LoggerException {
//
//    }
//
//    @Override
//    public void logSql(SQLInfo sqlInfo) throws LoggerException {
//        Formatter formatter = new BasicFormatterImpl();
//        try {
//            if (sqlInfo.getParameterGroup().length > 0) {
//                List<String> split = StringUtils.splitToList("?", sqlInfo.getSql());
//                for (List list : sqlInfo.getParameterGroup()) {
//                    StringBuilder stringBuffer = new StringBuilder("");
//                    for (int i = 0; i < split.size(); i++) {
//                        stringBuffer.append(split.get(i));
//                        if (i < list.size()) {
//                            stringBuffer
//                                    .append("'")
//                                    .append(list.get(i))
//                                    .append("'");
//                        }
//                    }
//                    logger.info(String.format("\n----------------------- SQL_BEGIN --------------------------------%s\n------------------------ SQL_END ---------------------------------", formatter.format(stringBuffer.toString())));
//                }
//            } else {
//                logger.info(String.format("\n----------------------- SQL_BEGIN --------------------------------%s\n------------------------ SQL_END ---------------------------------", formatter.format(sqlInfo.getSql())));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void close() throws LoggerException {
//
//    }
//}
