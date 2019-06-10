package cn.damon.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateUtils
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 15:03
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class DateUtils {

    public static String date2String(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date str2Date(String str, String pattern) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
