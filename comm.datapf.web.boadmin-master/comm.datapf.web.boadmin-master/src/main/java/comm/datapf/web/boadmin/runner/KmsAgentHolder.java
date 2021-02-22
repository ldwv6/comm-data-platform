package comm.datapf.web.boadmin.runner;

import com.penta.scpdb.ScpDbAgent;
import comm.datapf.web.boadmin.utils.PropertyUtils;
import comm.datapf.web.boadmin.utils.WasTypeBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KmsAgentHolder implements CommandLineRunner {
    public final static ScpDbAgent agentHolder = new ScpDbAgent();
    public static String configFilePathHolder = "";

	@Override
    public void run(String... args) throws Exception {
		this.checkKmsConnect();
    }

    public void checkKmsConnect() throws Exception {

        //구동시 kms 연동확인
        String wasType = WasTypeBean.getWasType();
        String configFileName = PropertyUtils.getProperty("kms.ini.name").toString();
        String configFilePath = PropertyUtils.getProperty("kms.ini.path."+wasType);
        int ret = 9999;

        configFilePath = configFilePath + configFileName;
        this.configFilePathHolder = configFilePath;

        ret = agentHolder.AgentInit(configFilePath);

        if(ret < 1) { //연동 성공
            log.info("[Connection status from COMM-PF SETTLEMENT backoffice to KMS Damo] [{}]", "Success");
        }else { //연동 실패
            log.info("[Connection status from COMM-PF SETTLEMENT to KMS Damo] [{}, {}]", "fail", ret);
        }
    }

    public static String getConfigFilePathHolder() {
        return configFilePathHolder;
    }

    public static void setConfigFilePathHolder(String configFilePathHolder) {
        KmsAgentHolder.configFilePathHolder = configFilePathHolder;
    }
}
