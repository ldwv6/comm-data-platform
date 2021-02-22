package comm.datapf.web.boadmin.utils;

import com.yelopay.module.security.ByteOperator;
import com.yelopay.module.security.SHA;
import comm.datapf.web.boadmin.runner.KmsAgentHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public final static String ALL_NUMBER_REGEXP = ".*[0-9].*";
    public final static String ALL_UPPERCASE_REGEXP = ".*[A-Z].*";
    public final static String ALL_LOWERCASE_REGEXP = ".*[a-z].*";
    public final static String ALL_SYMBOL_REGEXP = ".*\\p{Punct}.*";

    public static String convertToHash(String passwd) {

        try {
            SHA testSHA = new SHA();
            byte[] result = testSHA.SHAWithString("SHA-512", passwd);
            String hexStr = ByteOperator.ByteToHexStr(result, 0, result.length, "");
            return hexStr;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String test(String encStr) throws Exception {

        String configFilePath = "D:\\dbms_api\\scpdb_agent.ini";
        String inputPlain = "123";

        encStr = KmsAgentHolder.agentHolder.ScpEncB64(configFilePath, "INT1", inputPlain, "EUC-KR").toString();

        encStr = KmsAgentHolder.agentHolder.ScpEncB64(configFilePath, "INT1", inputPlain, "EUC-KR").toString();

        return encStr;
    }

    public static String stringEncrypt(Object plainText) throws Exception {

        String inputPlain = plainText.toString();
        String returnStr = "";
        returnStr = KmsAgentHolder.agentHolder.ScpEncB64( KmsAgentHolder.getConfigFilePathHolder(), "INT1", inputPlain, "UFT-8" );

        return returnStr;
    }

    public static String stringDecrypt(Object encryptText) {

        String inputText = encryptText.toString();
        String returnStr = "";
        returnStr = KmsAgentHolder.agentHolder.ScpDecB64( KmsAgentHolder.getConfigFilePathHolder(), "INT1", inputText, "UFT-8" );
        return returnStr;
    }

    public static String getRandomPwd(int pwdSize){

        String [][] str = {
                {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"}
                ,{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}
                ,{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}
                ,{"!", "@", "#", "$", "%", "^", "&", "*", "-", "_", "+", "="}
        };

        String result = "";
        boolean flag = false;

        while(!flag){
            result = "";
            for(int i = 0 ; i < pwdSize ; i++){

                int type1 = (int)(Math.random()*str.length);
                int type2 = 0;

                if(type1 == 0) type2 = (int)(Math.random()*str[0].length);
                if(type1 == 1) type2 = (int)(Math.random()*str[1].length);
                if(type1 == 2) type2 = (int)(Math.random()*str[2].length);
                if(type1 == 3) type2 = (int)(Math.random()*str[3].length);

                result +=str[type1][type2];
            }

            flag = isAddCharValidation(result);
        }

        logger.debug("SecurityUtils getRandomPwd ["+result+"]");

        return result;

    }

    /**
     * BO PASSWORD 규칙 정의
     *
     * @date 2014.12.15
     * @param input 입력값
     * @return 코드|메시지
     */
    public static String passwordValidation(String input) {

        try {

            input = StringUtils.decodeHtml(StringUtils.decodeHtml(input));
            input = input.replaceAll("''", "'");
            input = input.replaceAll("\"\"", "\"");

            if (StringUtils.isEmpty(input)) {
                logger.debug("SecurityUtils passwordValidation isValidObject input [" + stringEncrypt(input) + "]");
                return "9999"; //input null
            }

            if (!isSizeValidation(input, 8))
                return "0001"; //8자리 이상
            if (!isRepeatValidation(input, 4))
                return "0003"; //같은 문자 반복 체크
            if (!isStraightNumValidation(input, 5))
                return "0004"; //연속숫자(역순포함) 체크
            if (!isAddCharValidation(input))
                return "0002"; //숫자 + 대문자 + 소문자 + 특수문자 사용 체크

            return "0000";

        }

        catch (Exception e) {
//            logger.error("SecurityUtils passwordValidation input [" + stringEncrypt(input) + "] Exception : " + e);
            return null;
        }
    }

    /**
     * 입력값 길이 체크
     *
     * @date 2014.12.15
     * @param input 입력값
     * @param size 체크 길이 설정
     * @return TRUE or FALSE
     */
    public static boolean isSizeValidation(String input, int size) {

        if (input.length() <= size - 1) {
            logger.debug("password is " + size + "-character minimum [" + input + "]");
            return false;
        }

        return true;

    }

    /**
     * 숫자 + 대문자 + 소문자 + 특수문자 사용 체크
     *
     * @date 2014.12.15
     * @param input 입력값
     * @return TRUE or FALSE
     */
    public static boolean isAddCharValidation(String input) {

        if (!(Pattern.matches(ALL_NUMBER_REGEXP, input) && Pattern.matches(ALL_UPPERCASE_REGEXP, input) && Pattern.matches(ALL_LOWERCASE_REGEXP, input) && Pattern.matches(ALL_SYMBOL_REGEXP, input))) {
            logger.debug("password contain at least one number and one uppercase and symbols [" + input + "]");
            return false;
        }

        return true;
    }

    /**
     * 같은 문자 반복 체크
     *
     * @date 2014.12.15
     * @param input 입력값
     * @param length 반복 길이 설정
     * @return TRUE or FALSE
     */
    public static boolean isRepeatValidation(String input, int length) {

        int add = 1;

        if (input.length() != 0) {
            String tmpString = "" + input.charAt(0);

            for (int i = 1; i < input.length(); i++) {
                if (tmpString.equals("" + input.charAt(i))) {
                    add++;
                } else {
                    tmpString = "" + input.charAt(i);
                    if (add < length)
                        add = 1;
                }
            }

            if (add >= length) {
                logger.debug("password is repeated same character " + length + " times [" + input + "]");
                return false;

            }
        }

        return true;


    }

    /**
     * 연속숫자(역순포함) 체크
     *
     * @date 2014.12.15
     * @param input 입력값
     * @param length 연속 길이 설정
     * @return TRUE or FALSE
     */
    public static boolean isStraightNumValidation(String input, int length) {

        int o = 0;
        int d = 0;
        int p = 0;
        int n = 0;
        int l = length;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (NumberUtils.isValidNumber(c)) {
                if (i > 0 && (p = o - c) > -2 && p < 2 && (n = p == d ? n + 1 : 0) > l - 3) {
                    logger.debug("password is contained " + length + " times consecutively [" + input + "]");
                    return false;
                }
            }
            d = p;
            o = c;
        }

        return true;
    }

    public enum Operation {
        RNCNO, MBPNO, MBPNO2, STTL_ACNO, CRYPT_KEY, PKEY, MEMB_EMAL, MCHT_MEMB_NO
        , MEMB_NM, MCHT_MEMB_ID, EMAIL, IP_INFO, ETC, EMAIL_ID, BIRTH, INTER_BIRTH, noOperation;

        public static Operation getMethod(String str) {

            try {
                return valueOf(str);
            } catch (Exception ex) {
                return noOperation;
            }
        }
    }

    @SuppressWarnings("incomplete-switch")
    public static String stringFormat(String operation, Object _value) {

        String result = new String();

        if(StringUtils.isNotEmpty(_value)){

            String value = _value.toString();
            try {
                switch (Operation.getMethod(operation)) {
                    case RNCNO: {
                        if (value.length() == 13) {
                            // 주민등록번호, 뒤 7자리 *처리
                            result = value.substring(0, value.length() - 7) + "-*******";
                        }

                        if (value.length() == 10) {
                            // 사업자 번호 3후 - 추가
                            result = value.substring(0, 3) + "-" + value.substring(3, 5) + "-" + value.substring(5, result.length());
                        }
                        break;
                    }
                    case MBPNO: {
                        String temp = new String();
                        for (int i = 3; i < (value.length() - 5); i++) {
                            temp += "*";
                        }
                        result = "***-" + temp + value.substring(value.length() - 5, value.length() - 4) + "-" + value.substring(value.length() - 4, value.length());
                        break;
                    }
                    case MBPNO2: { //인터파크 규정에 의한 마스킹처리 010-****-1234, ****-1234
                        String temp = new String();

                        if(!StringUtils.contains(value, "D")){
                            for (int i = 3; i < (value.length() - 4); i++) {
                                temp += "*";
                            }
                            result = value.substring(0,3) + "-" + temp + "-" + value.substring(value.length() - 4, value.length());
                        }else{
                            result = value;
                        }
                        break;
                    }
                    case STTL_ACNO: {

//                      String temp = new String();
//                      if (value.length() > 0) {
//
//                          if(value.length() > 5){
//                              int start_str = value.length()-5;
//                              result = "X-"+value.substring(start_str);
//                          }else{
//                              result = "-";
//                          }
//
//                      }else{
//
//                      }
//                      break;
                        String dChk = (String) value.subSequence(0, 1);
                        //카드만 처리 계좌는 DB function 처리
                        if (value.length() > 0 && !(StringUtils.equals("D", dChk))) {
                            result = value.subSequence(0, 4)+"********"+value.subSequence(value.length()-4, value.length());
//                    	  result = (String)value.subSequence(0, 4) + (String)value.subSequence(4, 8) +"********";

                        }else{
                            result = value;
                        }
                        break;
                    }
                    case CRYPT_KEY: {
                        if (value.length() > 0) {
                            result = value.subSequence(0, 4)+"-XXXX-"+value.subSequence(value.length()-4, value.length());
                        }else{
                            result = value;
                        }
                        break;
                    }
                    case PKEY: {
                        if (value.length() > 0) {
                            if(value.contains("_test_")){
                                result = value.subSequence(0, 12)+"-XXXX-"+value.subSequence(value.length()-4, value.length());
                            }else{
                                result = value.subSequence(0, 7)+"-XXXX-"+value.subSequence(value.length()-4, value.length());
                            }
                        }else{
                            result = value;
                        }
                        break;
                    }
                    //20160928 인터파크 개인정보 비식별화 조치 사항 - 가맹점 회원번호 마스킹처리 : 앞 6자리이후 마스킹(210847****)
                    case MCHT_MEMB_NO : {
                        if(value.length() > 0){
                            result = value.substring(0, value.length() - 6) + "****";
                        }else{
                            result = value;
                        }

                        break;
                    }

                    //20160928 인터파크 개인정보 비식별화 조치 사항- MEMB_NM 회원 이름 처리 : 앞뒤 한글자씩 제외한 가운데 마스킹(홍*동, 김*, 박**나)
                    //20170720 성명이 영문이고 띄어쓰기가 있는 경우, 띄어쓰기의 마지막 글자의 경우는 남겨두고 나머지는 4글자 이하인 경우 앞2글자만 표기,  5글자 이상은 앞 3글 자만 표기. Kildong Hong -> Kil**** Hong, WANG TSAI JUNG -> WA** TSAI JUNG
                    //20170720 성명이 영문이고 띄어쓰기가 없는 경우 LIHUASHU  -> L*******U
                    case MEMB_NM : {
                        if(value.length() > 0){
                            if(value.length() == 3){ //3글자

                                result = value.substring(0, 1) + "*"+ value.substring(value.length()-1);
                            }else if(value.length() > 3){ //3글자 이상

                                //탈퇴회원문구 일때 skip
                                if(StringUtils.equals("탈퇴회원", value)){
                                    result = value;
                                }else{
                                    //회원번호 체크, 이름에 숫자가 포함될 때
                                    if(!value.matches(".*[0-9].*")){
                                        String[] _arrStr = null;
                                        _arrStr = value.split(" ");
                                        int _arrCnt = (_arrStr.length)-1;

                                        if(!value.matches("^[가-힣]*$") && _arrCnt > 0){
                                            //성명이 영문이며, 띄어쓰기가 있는 경우
                                            String _firstStr =  _arrStr[0];

                                            int fst_len = _firstStr.length();
                                            if(fst_len <= 4){
                                                if(fst_len > 2){
                                                    _firstStr = _firstStr.substring(0, 2);
                                                    for (int i = 0; i < fst_len-2; i++) {
                                                        _firstStr = _firstStr+ "*";
                                                    }
                                                }
                                            }else{ //4자리초과

                                                int full_len = _firstStr.length();
                                                int af_len = 3;
                                                _firstStr = _firstStr.substring(0, af_len);

                                                for (int i = 0; i < (full_len-af_len); i++) {

                                                    _firstStr += "*";
                                                }
                                            }

                                            for (int i = 0; i < _arrCnt+1; i++) {
                                                if(i < 1){
                                                    result = _firstStr;
                                                }else{
                                                    result = result+ " " + _arrStr[i];
                                                }
                                            }
                                        }else{
                                            //성명이 한글인 경우
                                            int astNum = value.length()-2;
                                            String phrase = "";
                                            for (int i = 0; i < astNum; i++) {
                                                phrase += "*";
                                            }

                                            result = value.substring(0, 1) + phrase + value.substring(value.length()-1);
                                        }
                                    }else{

                                        result = value;
                                    }
                                }

                            }else if(value.length() < 3){ //2글자
                                result = value.substring(0, value.length()-1) + "*";
                            }
                        }else{
                            result = value;
                        }
                        break;
                    }

                    //20160928 인터파크 개인정보 비식별화 조치 사항 - MCHT_MEMB_ID 가맹점 회원 아이디 : 뒤세자리 마스킹(testId***)
                    //20170720 MCHT_MEMB_ID 가맹점 회원 아이디를 이메일로 사용하는 사람이 존재 : 12345@naver.com -> 12***@*******
                    case MCHT_MEMB_ID : {
                        if(value.length() > 0){

                            //이메일 검증
                            String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$";
                            Pattern p = Pattern.compile(emailRegex);
                            Matcher m = p.matcher(value);
                            if(m.matches()){
                                String[] arrMail = null;
                                String mailStr = "";
                                arrMail = value.split("@");
                                mailStr = arrMail[0];
                                int cnt = mailStr.length()-3;
                                result= value.replaceAll("(?<=.{"+cnt+"}).(?=.*@)", "*");
                                arrMail = result.split("@");
                                String front_mailStr = arrMail[0];
                                result = front_mailStr + "@"+"*******";
                            }else{
                                if(value.length() <= 3){ //3자리 이하인 경우
                                    result = value.substring(0, value.length()-1) + "*";
                                }else{ //3자리 이상
                                    int full_len = value.length();
                                    int af_len = (value.length())-3;
                                    result = value.substring(0, af_len);
                                    for (int i = 0; i < (full_len-af_len); i++) {
                                        result += "*";
                                    }
                                }
                            }
                        }else{
                            result = value;
                        }
                        break;
                    }

                    //20160928 인터파크 개인정보 비식별화 조치 사항 - EMAIL 이메일 정보 : @앞 세자리 (test***@gmail.com)
                    case EMAIL : {
                        if(value.length() > 0){
                            //이메일 검증
                            String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$";
                            Pattern p = Pattern.compile(emailRegex);
                            Matcher m = p.matcher(value);
                            if(m.matches()){
                                String[] arrMail = null;
                                String mailStr = "";

                                arrMail = value.split("@");
                                mailStr = arrMail[0];
                                int cnt = mailStr.length()-3;
                                result= value.replaceAll("(?<=.{"+cnt+"}).(?=.*@)", "*");
                            }else{
                                result = value;
                            }
                        }else{
                            result = value;
                        }
                        break;
                    }

                    //20160928 인터파크 개인정보 비식별화 조치 사항 - EMAIL ID 이메일 정보 : @앞 세자리 (test***@*****)
                    case EMAIL_ID : {
                        if(value.length() > 0){
                            //이메일 검증
//							String emailRegex = "^[a-zA-Z0-9._-]+(.[a-zA-Z0-9.-]+)*@(?:\\w+\\.)+\\w+$";
                            String emailRegex =  "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$";
                            Pattern p = Pattern.compile(emailRegex);
                            Matcher m = p.matcher(value);
                            if(m.matches()){
                                String[] arrMail = null;
                                String mailStr = null;
                                arrMail = value.split("@");
                                mailStr = arrMail[0].substring(0, 3)+"***"+"@";
                                mailStr = mailStr+"*******";
                                result = mailStr;
                            }else{
                                result = value;
                            }
                        }else{
                            result = value;
                        }
                        break;
                    }
                    //20160928 인터파크 개인정보 비식별화 조치 사항 - IP ip정보 : 17~24비트영역(123.123.***.123)
                    case IP_INFO : {
                        if(value.length() > 0){
                            StringBuffer buffer = new StringBuffer();
                            InetAddressValidator iav = new InetAddressValidator();
                            if(iav.isValidInet4Address(value)){ //ip4
                                Inet4Address inet4Str = (Inet4Address) InetAddress.getByName(value);
                                byte[] i4Byte = inet4Str.getAddress();
                                for (int i = 0; i < i4Byte.length; i++) {
                                    if(i > 0) buffer.append('.');
                                    if(i != 2) buffer.append(i4Byte[i] & 0xFF); else buffer.append("***");
                                }
                            }
                            result = buffer.toString();
                        }else{
                            result = value;
                        }
                        break;
                    }
                    //20160928 인터파크 개인정보 비식별화 조치 사항 - ETC 영수증 및 기타 마스킹 : 앞 세자리 이후 (123*****)
                    case ETC :{
                        if(value.length() > 5){
                            result = value.substring(0, 3) + "******";
                        }else{
                            result = value;
                        }
                        break;
                    }

                    //20161101 I-Point 자체 처리 생년월일 YYYYMMDD 일때, 19830902 1983****, yymmdd 일때, 830902 83****
                    //20170619 인터파크 생년월일 처리 년도마지막자리, 날짜 마스킹처리 198*년 09월 **일 / 8*09**처리
                    case BIRTH :{
                        if(value.length() == 8){
                            result = value.substring(0, 3) + "****";
                        }else if(value.length() == 6){
                            String _s = "*";
                            for (int i = 0; i < 6; i++) {
                                char _v = value.charAt(i);
                                if(i == 0 || i == 2 || i == 3){
                                    result = result+_v;
                                }else if(i == 1 || i == 4 || i == 5){
                                    result = result+_s;
                                }
                            }
                        }else if(value.length() == 7){ // +성별

                            String _tmp = value;
                            result = getYYInfo(_tmp);

                        }else if(value.length() == 4){ //생년만 있는 경우
                            result = value.substring(0, 3) + "*";
                        }else{
                            result = value;
                        }
                        break;
                    }
                }
            } catch (Exception ex) {
                result = value;
            }
        }

        return result;
    }

    private static String getYYInfo(String _tmp) throws Exception {

        String result = "";
        String _gen = StringUtils.right(_tmp, 1);
        String _year = "";
        if("1".equals(_gen)){
            _year = "19";
        }else if("2".equals(_gen)){
            _year = "19";
        }else if("3".equals(_gen)){
            _year = "20";
        }else if("4".equals(_gen)){
            _year = "20";
        }else if("5".equals(_gen)){
            _year = "19";
        }else if("6".equals(_gen)){
            _year = "19";
        }else if("7".equals(_gen)){
            _year = "20";
        }else if("8".equals(_gen)){
            _year = "20";
        }else if("9".equals(_gen)){
            _year = "18";
        }else if("0".equals(_gen)){
            _year = "18";
        }

        String _s = "*";
        for (int i = 0; i < 6; i++) {
            char _v = _tmp.charAt(i);
            if(i == 0 || i == 2 || i == 3){
                result = result+_v;
            }else if(i == 1 || i == 4 || i == 5){
                result = result+_s;
            }
        }

        result = _year+result;
        return result;
    }

    public static String convertToIBSS(String passwd)throws Exception {

        StringBuffer hexString = new StringBuffer();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(passwd.getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return new String(hexString);
    }

    public static String tEncript(String passwd)throws Exception {

        String ss = Base64.encodeBase64String(passwd.getBytes());

        return ss;
    }

    public static String tDecript(String passwd)throws Exception {

        byte[] ss = Base64.decodeBase64(passwd);
        return new String(ss);
    }
}
