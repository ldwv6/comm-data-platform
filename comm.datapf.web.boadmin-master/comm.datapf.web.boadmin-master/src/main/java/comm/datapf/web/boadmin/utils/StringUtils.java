package comm.datapf.web.boadmin.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static boolean isNotEmpty(Object o){
        return !isEmpty(o);
    }

    public static String getString(Object paramValue, String defaultValue) {
        try {
            if (paramValue instanceof String)
                return (String)paramValue;
            return paramValue.toString();
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static String getString(Object paramValue) {
        return StringUtils.getString(paramValue, "");
    }

    public static String nvl(Object o){
        return ((o==null)? "": o.toString());
    }

    public static String nvl(Object o, String defaultValue){
        if(isEmpty(o)){
            return defaultValue;
        }
        return o.toString();
    }

    public static boolean isEmpty(Object o){
        return o==null || o.toString().trim().length() == 0;
    }

    public static String numberFormat(Object o){
        NumberFormat nf = NumberFormat.getInstance();
        if(isEmpty(o)){
            return "0";
        }
        double tmp = Double.parseDouble((String)o);
        return nf.format(tmp);
    }

    public static String replaceHtml(String text) {
        String replaceStr = StringUtils.getString(text).replace("\n", "<br/>");
        replaceStr = StringUtils.getString(replaceStr).replace("\r", "");
        replaceStr = replaceStr.replaceAll("(<br/>) {0,100}<(/){0,1}([Tt][Aa][Bb][Ll][Ee]|[Tt][Dd]|[Tt][Rr])", "<$2$3");

        return replaceStr;
    }

    public static String removeBrTag(String text) {
        if (text == null || "".equals(text)) {
            return text;
        }
        return text.replaceAll("<([Bb][Rr])(/)?>", "");
    }

    public static String changeBrTag(String text) {
        if (text == null || "".equals(text)) {
            return text;
        }
        return text.replaceAll("<([Bb][Rr])(/)?>", "\n");
    }

    /**
     * String형태의 숫자값을 3자리마다 , 찍어서 리턴
     * @return
     */
    public static String getThreeComma(String str) {
        int convert = Integer.parseInt(str);
        DecimalFormat df = new DecimalFormat("#,###");

        return (String)df.format(convert);
    }

    public static void toMap(Object source, Map<String, Object> target){

        try {
            for(Field f : source.getClass().getDeclaredFields()){
                f.setAccessible(true);
                target.put(f.getName(), f.get(source));
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setNullValue(Map<String, Object> map){
        Iterator<String> key = map.keySet().iterator();
        while(key.hasNext()){
            String k = key.next();
            if(StringUtils.isEmpty(map.get(k))){
                map.put(k, null);
            }
        }
    }

    public static Map<String,Object> setStrToObj(Map<String, String> map){
        Map<String,Object> newParam = new HashMap<String, Object>();

        Iterator<String> key = map.keySet().iterator();
        while(key.hasNext()){
            String k = key.next();
            if(StringUtils.isEmpty(map.get(k))){
                newParam.put(k, null);
            }else{
                newParam.put(k, (Object)map.get(k));
            }
        }

        return newParam;
    }

    public static String lpad(String src, int len, String addStr){
        StringBuilder sb = new StringBuilder();
        for(int i= 0;i<len-src.length();i++){
            sb.append(addStr);
        }
        sb.append(src);
        return sb.toString();
    }

    public static String getTelNumberHyphen(String strTel) throws Exception{

        String optionTel = "";

        if(StringUtils.contains(strTel, "~")){
            //02-123-1234~9 에서 마지막자리 처리
            String tmp[] = strTel.split("~");
            strTel = tmp[0];
            optionTel = tmp[1];
        }

        String strReturn = "";
        strTel = strTel.replace("-", "");

        strTel = StringUtils.nvl(strTel,"");

        if(strTel.length() < 4) return strTel;

        String[] arrayTwoPattern = new String[]{"02"};
        String[] arrayThreePattern = new String[]{"051","053","032","062","042","052","044","031","033","043","041","063","061","054","055","064","010","011","018","019","060","070","080"};
        String[] arrayFourPattern = new String[]{"1544","1566","1577","1588","1599","1600"};

        List<String> towPattern   = new ArrayList<String>();
        List<String> threePattern = new ArrayList<String>();
        List<String> fourPattern  = new ArrayList<String>();

        for(int i = 0; i < arrayTwoPattern.length; i++) towPattern.add(arrayTwoPattern[i]);
        for(int i = 0; i < arrayThreePattern.length; i++) threePattern.add(arrayThreePattern[i]);
        for(int i = 0; i < arrayFourPattern.length; i++) fourPattern.add(arrayFourPattern[i]);

        int nTelLength = strTel.length();

        strReturn = strTel;

        try{

            if( towPattern.contains(strTel.substring(0,2)) || threePattern.contains(strTel.substring(0,3)) || fourPattern.contains(strTel.substring(0,4)) ){

                if(towPattern.contains(strTel.substring(0,2))){

                    if(nTelLength == 10) strReturn = getStringHyphen(strTel,2,6,nTelLength);
                    else if(nTelLength == 9) strReturn = getStringHyphen(strTel,2,5,nTelLength);

                }else if(threePattern.contains(strTel.substring(0,3))){

                    if(nTelLength == 11) strReturn = getStringHyphen(strTel,3,7,nTelLength);
                    else if(nTelLength == 10) strReturn = getStringHyphen(strTel,3,6,nTelLength);

                }else if(fourPattern.contains(strTel.substring(0,4))){

                    if(nTelLength == 8) strReturn = strTel.substring(0,4) + "-" + strTel.substring(4,8);
                    else if(nTelLength == 7) strReturn = strTel.substring(0,3) + "-" + strTel.substring(4,7);
                    else strReturn = strTel;

                }

            }else{
                strReturn = strTel;
            }

        }catch(Exception e){
            return strTel;
        }

        if(StringUtils.isNotEmpty(optionTel)){
            strReturn += "~"+optionTel;
        }
        return strReturn;

    }

    public static String getStringHyphen(String strTel, int first, int second, int third) throws Exception{

        String strReturn = "";

        strReturn = strTel.substring(0,first) + "-" + strTel.substring(first,second) + "-" + strTel.substring(second,third);

        return strReturn;

    }

    public static String decodeHtml(String source) {

        if (source == null)
            return "";

        source = source.replaceAll("&quot;", "\\\"");
        source = source.replaceAll("&#35;", "#");
        source = source.replaceAll("&lt;", "<");
        source = source.replaceAll("&gt;", ">");
        source = source.replaceAll("<br />", "\n");
        source = source.replaceAll("&#39;", "'");
        source = source.replaceAll("&nbsp;", " ");
        source = source.replaceAll("&#40;", "(");
        source = source.replaceAll("&#41;", ")");
        source = source.replaceAll("&#38;", "&");

        return (source);
    }

    public static boolean isValidPattern(String pattern, Object value) {

        if (value == null) {
            return false;
        }
        boolean isValid = true;
        // 정규식으로 문자를 포함하는지 찾는다.
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value.toString());
        if (!m.matches()) {
            isValid = false;
        }

        return isValid;
    }

    public static Set getSelectedKeySet(String keyset, String sep) {

        Set result = new HashSet();

        StringTokenizer st = new StringTokenizer(keyset, sep);
        while (st.hasMoreTokens()) {
            String cur = (String) st.nextToken();
            result.add(new String(cur));
        }

        return result;
    }

    public static String decodeSms(String source) {
        if (source == null)
            return "";

        source = source.replaceAll("&#35;", "#");
        source = source.replaceAll("&#38;", "&");
        source = source.replaceAll("&lt;", "<");
        source = source.replaceAll("&gt;", ">");
        source = source.replaceAll("&#40;", "(");
        source = source.replaceAll("&#41;", ")");
        return (source);
    }

    public static String encodeHtml(String source) {
        if (source == null)
            return "";

        source = source.replaceAll("&", "&#38;");
        source = source.replaceAll("\\\"", "&quot;");
        source = source.replaceAll("#", "&#35;");
        source = source.replaceAll("<", "&lt;");
        source = source.replaceAll(">", "&gt;");
        source = source.replaceAll("\n", "<br />");
        source = source.replaceAll("vblf", "<br />");
        source = source.replaceAll("\r", "");
        source = source.replaceAll("'", "&#39;");
        source = source.replaceAll("\\(", "&#40;");
        source = source.replaceAll("\\)", "&#41;");
        source = source.replaceAll("<br>", "<br />");



        return (source);
    }


    public static String getRandomNumber(int numLength) {
        String randomStr = "";

        try {
            java.util.Random ran = new Random();
            for (int i = 0; i < numLength; i++) {
                randomStr += ran.nextInt(10);
            }
            ran = null;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return randomStr;
    }

    public static boolean isValidObject(Object object) {

        if (object == null) {
            return false;
        }
        if (object instanceof String) {
            if (((String) object).trim().equals("")) {
                return false;
            }
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        return true;
    }

    public static String checkMessageSize(int size, String msg) {

        String returnStr = new String();

        try {
            String msgSize = new Integer(msg.length()).toString();
            for (int i = 0; i < (size - msgSize.length()); i++) {
                returnStr += "0";
            }
            returnStr += msgSize;
        } catch (Exception ex) {
        }

        return returnStr;
    }

    public static String sqlFilter(String str) {

        str = str.replaceAll("'", "''");
        str = str.replaceAll("\"", "\"\"");
        // str = str.replaceAll("\\","\\\\");
        // str = str.replaceAll(";","");
        str = str.replaceAll("# ", "");
        str = str.replaceAll(" #", "");
        str = str.replaceAll("--", "");
        //str = str.replaceAll(" ", "");
        // SQL Query 제거
        str = str.replaceAll("; ", "");
        str = str.replaceAll("% ", "");

        str = str.replaceAll("union ", "");
        str = str.replaceAll("UNION ", "");
        str = str.replaceAll("user_tables ", "");
        str = str.replaceAll("from ", "");
        str = str.replaceAll("FROM ", "");
        str = str.replaceAll("select ", "");
        str = str.replaceAll("SELECT ", "");
        str = str.replaceAll("column_name ", "");
        str = str.replaceAll("dual ", "");
        str = str.replaceAll("update ", "");
        str = str.replaceAll("UPDATE ", "");
        str = str.replaceAll("delete ", "");
        str = str.replaceAll("DELETE ", "");
        str = str.replaceAll("drop ", "");
        str = str.replaceAll("DROP ", "");
        str = str.replaceAll("set ", "");
        str = str.replaceAll("SET ", "");

        //웹취약점 점검사항 추가
        str = str.replaceAll("#", "&#35;");
        str = str.replaceAll("&", "&#38;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\\(", "&#40;");
        str = str.replaceAll("\\)", "&#41;");


        return (str);
    }

    //15_12_04 추가
    public static String omitStr (String str, int limit) {

        int strlen = str.length();
        String returnStr = "";
        if(!isEmpty(str)){
            if(strlen > limit){
                returnStr = str.substring(0, (limit-1))+"...";
            }else{
                returnStr = str;
            }
        }

        return (returnStr);
    }

    //15_12_24 추가
    public static String getTraceNo(int size){
        StringBuilder traceNumber = new StringBuilder();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        traceNumber.append(dateFormat.format(date));
        for (int i = 0; i < size; i++) {
            int idx = Double.valueOf(Math.random() * ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length() - 1)).intValue();
            traceNumber.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(idx));
        }
        return traceNumber.toString();
    }

    public static String getTraceNo(){
        return getTraceNo(3);
    }

    /**
     * @Method : getMavDataToMap
     * @Date : 2016. 6. 10.
     * @Author : jmchoi
     * @History :
     * @Desc : mav data -> jsp -> service toMap처리
     * @return
     */
    public static Map<String, Object> getMavDataToMap(String mavData, Map<String, Object> dataMap){

        Map<String, Object> paramMap = new HashMap<String, Object>();

        mavData = mavData.replace("{", "");
        mavData = mavData.replace("}", "");
        String[] elements = mavData.split(", ");

        for(String s1: elements) {

            String[] keyValue = s1.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }

        return paramMap;
    }

    /**
     * <B>nvl_p nvl + null문자체크</B>
     *
     * @date 2017. 3. 16.
     * @author jmchoi
     * @param o
     * @param defaultValue
     * @return
     *
     */
    public static String nvl_p(Object o, String defaultValue){
        if(isEmpty(o) || "null".equals(o.toString())){
            return defaultValue;
        }
        return o.toString();
    }

    /**
     * <B>getDelimiterCnt ex. 1566-3433인 경우 -이 delimiter, 1을 리턴 delimiter의 갯수를 리턴</B>
     *
     * @date 2017. 7. 24.
     * @author jmchoi
     * @param _str
     * @param _delimiter
     * @return
     *
     */
    @SuppressWarnings("unused")
    public static int getDelimiterCnt(String _str, String _delimiter){
        String [] _strArr = null;
        int _reCnt = 0;

        if(StringUtils.isNotEmpty(_str)){
            _strArr = _str.split(_delimiter);
        }
        if(_strArr.length > 0){
            for (String _tmp : _strArr) {
                _reCnt = _reCnt+1;
            }
        }
        return _reCnt;
    }


    /**
     * <B>sortByValue 리스트 정렬 </B>
     *
     * @date 2017. 8. 24.
     * @author jmchoi
     * @param unsortMap
     * @return
     *
     */
    public static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap)throws Exception {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {

                //현재 오름차순, 내림차순으로 하려면 o2, o1 순서 변경 필요
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        int chk_cnt = 0;
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static Map<String, Object> includDelimiterStringToMap(String _val, String _delimiter)throws Exception {

        Map<String, Object> tMap = new HashMap<String, Object>();

        int i = 0;
        for (String str : StringUtils.split(_val, _delimiter)) {
            i++;
//    		System.out.println(i+"번째 : " + str);
            tMap.put("data_"+i, str);
        }

        return tMap;
    }

    //map -> string -> map 처리시 사용
    public static Map<String, Object> includDelimiterKeyStringToMap(String _val, String _dataDelimiter, String _keyDelimiter)throws Exception {

        Map<String, Object> tMap = new HashMap<String, Object>();

        for (String str : StringUtils.split(_val, _dataDelimiter)){

//    		System.out.println(i+"번째 : " + str);

            int i = 0;
            String key_str = "";
            String value_str = "";
            for (String data : StringUtils.split(str, _keyDelimiter)) {
                if(i == 0){
                    key_str = StringUtils.trim(data);
                    if(StringUtils.contains(key_str, "{")) {
                        key_str = key_str.replaceAll("\\{", "");
                        data = key_str;
                    }
                    tMap.put(trim(data), "");
                }else{
                    value_str = StringUtils.trim(data);

                    if(StringUtils.contains(value_str, "}")) {
                        value_str = value_str.replaceAll("\\}", "");
                    }

                    tMap.put(key_str, value_str);
                }
                i++;
            }
        }
        return tMap;
    }

    //String to MapList (공통코드 대체) ex. delimiter - '||', value MENU||메뉴,PGRM||화면)
    public static List<Map<String, Object>> getStringToMapList(String delimiter, String value)throws Exception{

        List<Map<String, Object>> tmpList = new ArrayList<>();
        Map<String, Object> tmpMap = new HashMap<String, Object>();

        for (String str : StringUtils.split(value, ",")){

            int i = 0;
            String key_str = "";
            String value_str = "";

            for (String data : StringUtils.split(str, delimiter)) {

                if(i == 0){
                    key_str = StringUtils.trim(data);
                    tmpMap.put("COM_CD", key_str);
                }else{
                    value_str = StringUtils.trim(data);
                    tmpMap.put("COM_CD_NM", value_str);
                }
                i++;
            }
            tmpList.add(tmpMap);
            tmpMap = new HashMap<String, Object>();
        }
        return tmpList;
    }

    //get code
    public static String getListToKeyGetValue(List<Map<String, Object>> list, String value)throws Exception{

        for (Map<String, Object> map : list) {
            if(StringUtils.equals((String)map.get("COM_CD"), value)) {
                value = (String) map.get("COM_CD_NM");
            }
        }

        return value;
    }

    //UUID 생성
    /*public static String generateUuid()throws Exception {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))+RandomStringUtils.randomAlphabetic(5);
    }*/

    //String to MapList (공통코드 대체) ex. delimiter - '||', value MENU||메뉴,PGRM||화면)
    public static List<Map<String, Object>> ipStringToMapList(String delimiter, String value)throws Exception{

        List<Map<String, Object>> tmpList = new ArrayList<>();
        Map<String, Object> tmpMap = new HashMap<String, Object>();

        if(StringUtils.contains("|", value)){
            for (String str : StringUtils.split(value, "|")){
                int i = 0;
                String key_str = "";
                key_str = StringUtils.trim(str);
                tmpMap.put("ip"+i, key_str);
                tmpList.add(tmpMap);
                tmpMap = new HashMap<String, Object>();
            }
        }else{
            tmpMap.put("ip1", value);
            tmpList.add(tmpMap);
        }
        return tmpList;
    }

    //String to Map
    //String이 맵형태여야 가능함.
    public static Map<String, Object> stringToMap(Map<String, Object> paramMap, String dataStr) throws Exception {

        return paramMap = Arrays.stream(dataStr.replace("{", "").replace("}", "").split(","))
                .map(arrayData-> arrayData.split("="))
                .collect(Collectors.toMap(d-> ((String)d[0]).trim(), d-> (String)d[1]));
    }

    //excel header get file name
    //attachment;filename=회원별현황_거래내역_20181217105714.xls
    public static String getHeaderFileNm(String fileNm) throws Exception {
        fileNm = new String(fileNm.getBytes("iso-8859-1"), "euc-kr");
        fileNm = StringUtils.replace(fileNm, "attachment;filename=", "");
        return fileNm;
    }

    //DB 저장시 XSS 방지 처리
    public static Map<String, Object> getParamXssProc(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        // 방법3
        for( String key : paramMap.keySet() ){
            String value = StringUtils.nvl_p(""+paramMap.get(key), "");
            if(StringUtils.isNotEmpty(value)) {
                value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
                value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
                value = value.replaceAll("'", "& #39;");
                value = value.replaceAll("eval\\((.*)\\)", "");
                value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
                value = value.replaceAll("script", "");
            }
            dataMap.put(key, value);
        }
        return dataMap;
    }

    //20190808 propertyUtils 가져오기
    /*public static String getPropertyInfo(String _keyName)throws Exception{
        String wasType = WasTypeBean.getWasType();
        String value = PropertyUtils.getProperty(_keyName+"."+wasType);
        return value;
    }*/
}
