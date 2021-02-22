package comm.datapf.web.boadmin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private static final String YYYY = "yyyy";
    private static final String MM = "MM";
    private static final String DD = "dd";
    private static final String HH = "HH";
    private static final String MI = "mm";
    private static final String SS = "ss";

    private static final String sDateSeparator = "-";
    private static final String sTimeSeparator = ":";

    private static Locale llocale = Locale.KOREA;

    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static String getToday(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String today = date.format(calendar.getTime());
        return today;
    }

    public static String getToday(String format){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat date = new SimpleDateFormat(format);
        String today = date.format(calendar.getTime());
        return today;
    }

    public static String timeFormat(Object value) {
        String object = "";
        try{
            object  = (String) value;
        } catch (Exception e) {
            object = "";
        }

        String result = new String();
        if (StringUtils.isNotEmpty(object)) {

            if(object.length() > 20) object = object.substring(0, 20);
            int length = object.length();

            if (length == 4) {
                result = object.substring(0, 2) + ":" + object.substring(2, 4);
            }else if (length == 6) {
                result = object.substring(0, 2) + ":" + object.substring(2, 4) + ":" + object.substring(4, 6);
            } else if (length == 8) {
                result = object.substring(0, 4) + "-" + object.substring(4, 6) + "-" + object.substring(6, 8);
            } else if (length == 9) {
                result = object.substring(0, 2) + ":" + object.substring(2, 4) + ":" + object.substring(4, 6) + "." + object.substring(6, 9);
            } else if (length == 14) {
                result = object.substring(0, 4) + "-" + object.substring(4, 6) + "-" + object.substring(6, 8) + " " +
                        object.substring(8, 10) + ":" + object.substring(10, 12) + ":" + object.substring(12);
            } else if (length == 20) {
                result = object.substring(0, 4) + "-" + object.substring(4, 6) + "-" + object.substring(6, 8) + " " +
                        object.substring(8, 10) + ":" + object.substring(10, 12) + ":" + object.substring(12, 14) + "."+ object.substring(14);
            }
        }
        return result;
    }

    public static int getDiffOfDate(String begin, String end){
        int diffDays = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date beginDate = format.parse(begin);
            Date endDate = format.parse(end);
            long diff = endDate.getTime() - beginDate.getTime();
            diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            logger.error("DateUtils.getDiffOfDate ERROR " +e );
        }
        return diffDays;
    }


}
