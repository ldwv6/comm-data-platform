package comm.datapf.web.boadmin.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    public final static String NUMBER_REGEXP = "[0-9]+";

    public static boolean isValidNumber(Object value) {

        return StringUtils.isValidPattern(NUMBER_REGEXP, value);
    }

    /**
     * object를 double로 변경
     * @param paramValue
     * @param defaultValue
     * @return
     */
    public static double getDouble(Object paramValue, double defaultValue) {
        try {
            if (paramValue instanceof Double)
                return ((Double) paramValue).doubleValue();
            return Double.parseDouble(paramValue.toString());
        } catch (Exception e) {
        }
        return defaultValue;
    }
    public static double getDouble(Object paramValue) {
        return NumberUtils.getDouble(paramValue, 0);
    }

    /**
     * object를 int로 변경
     * @param paramValue
     * @param defaultValue
     * @return
     */
    public static int getInt(Object paramValue, int defaultValue) {
        try {
            if (paramValue instanceof Integer)
                return ((Integer) paramValue).intValue();
            return Integer.parseInt(paramValue.toString());
        } catch (Exception e) {
        }
        return defaultValue;
    }
    public static int getInt(Object paramValue) {
        return NumberUtils.getInt(paramValue, 0);
    }

    /**
     * double을 decimal format에 맞게 변경
     * @param decimalFormat
     * @param paramValue
     * @param defaultValue
     * @return
     */
    public static String getDecimalFormat(String decimalFormat, double paramValue, String defaultValue) {
        try {
            DecimalFormat df = new DecimalFormat(decimalFormat);
            return df.format(paramValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
    public static String getDecimalFormat(String decimalFormat, Object paramValue, String defaultValue) {
        return NumberUtils.getDecimalFormat(decimalFormat, getDouble(paramValue), defaultValue);
    }

    /**
     * <B>getRound String 파라메터로 반올림 처리</B>
     *
     * @date 2017. 1. 25.
     * @author jmchoi
     * @param num 반올림 파라메터
     * @param cipher 반올림 자릿수 1000000백만원, 1000천원
     *
     */
    public static String getRound(String num, String cipher) {
        double dnum = 0;
        String retrunNum = "0";
        dnum = Double.parseDouble(num);
        double a_p = 0;
        double d_cipher = Double.parseDouble(cipher);
        a_p = 1/d_cipher;

//		System.out.println("1 : " + Math.round((dnum * a_p)));

        if(dnum >= d_cipher){
            dnum = Math.round((dnum * a_p) * 100d) / 100d;
        }else{
            dnum = 0;
        }

//		System.out.println("dnum : " + dnum);

        retrunNum = String.format("%.0f", dnum);
        retrunNum = String.valueOf(retrunNum);

//		System.out.println("retrunNum : " + retrunNum);

        return retrunNum;
    }

}
