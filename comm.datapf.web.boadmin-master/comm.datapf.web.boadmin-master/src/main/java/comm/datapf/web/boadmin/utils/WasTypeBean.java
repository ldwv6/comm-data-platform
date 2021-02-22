package comm.datapf.web.boadmin.utils;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class WasTypeBean implements InitializingBean {
    @Autowired
    private Environment env;

    private static String wasType_str;
    private static String wasNum;

    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public static String getWasNum() {
        return wasNum;
    }
    public static void setWasNum(String wasNum) {
        WasTypeBean.wasNum = wasNum;
    }
    public static String getWasType() { return wasType_str; }
    public static void setWasType(String wasType) { wasType_str = wasType; }

    @SuppressWarnings("static-access")
    @Override
    public void afterPropertiesSet() throws Exception {

        String[] profiles = env.getActiveProfiles();

        // Environment 이용
        profiles = env.getActiveProfiles();
        int i = 0;
        for(String profile : profiles){

            logger.info(">>>>>>>>>>>>>>>>>>>> WasTypeBean profile value {}", profile);
            if(i < 1) {
                this.setWasType(profile);
            }else {
                this.setWasNum(profile);
            }
            i++;
        }
        if(StringUtils.isEmpty(wasNum)){ //profile에 wasnum이 없는 경우 0으로 셋
            this.setWasNum("0");
        }
        logger.info(">>>>>>>>>>>>>>>>>>>> wasType_str " + wasType_str);
        logger.info(">>>>>>>>>>>>>>>>>>>> wasNum " + wasNum);
    }
}
