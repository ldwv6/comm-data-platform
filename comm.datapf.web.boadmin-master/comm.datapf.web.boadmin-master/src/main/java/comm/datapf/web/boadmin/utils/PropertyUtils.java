package comm.datapf.web.boadmin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class PropertyUtils implements InitializingBean {
    private static Properties prop = new Properties();
    public static PropertyUtils instance;
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    public void afterPropertiesSet() throws Exception {

        ClassPathResource classPathResource = new ClassPathResource("Config.properties");
        InputStream is = classPathResource.getInputStream();

        try{
            this.prop.load(is);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }finally{
            if(is!=null){
                is.close();
            }
        }
//			}
//		}
    }

    @SuppressWarnings("static-access")
    public static String getProperty(String key){

        return getInstance().prop.getProperty(key);
    }

    /**
     * <B>getPropertyList </B>
     *
     * @date 2017. 6. 13.
     * @author jmchoi
     * @param key 	property file 내 키값
     * @param _elmt property file 내 구분자
     * @return
     *
     */
    @SuppressWarnings("static-access")
    public static List<String> getPropertyList(String key, String _elmt){

        String _val = getInstance().prop.getProperty(key);
        if("".equals(_elmt)){
            _elmt = "|";
        }
        String [] _tmp = StringUtils.split(_val, _elmt);


        List<String> val_list = new ArrayList<String>();

        for (String str : _tmp) {
            val_list.add(str);
        }
        return val_list;
    }

    public static PropertyUtils getInstance(){
        return instance;
    }
}
