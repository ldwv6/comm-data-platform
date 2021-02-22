package comm.datapf.sys.cloudconfig.log;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import net.logstash.logback.decorate.JsonFactoryDecorator;

/**
 * net.logstash.logback.encoder.LogstashEncoder 클래스를 이용해 로그를 JSON 형식으로 생성할 때
 * 한글이 깨지는 문제를 해결하기 위하여 JsonFactoryDecorator.decorate 메서드를 재정의한 클래스
 * logback.xml의 jsonFileAppender > encoder 에서 참조함.
 *
 * 참고 : https://github.com/logstash/logstash-logback-encoder/issues/197
 * 참고 : https://github.com/logstash/logstash-logback-encoder#customizing-json-factory-and-generator
 *
 * @author 서비스INFRA팀 TA파트 장환호
 * @version 1.0
 * <pre>
 * 2019.09.02 : 최초 작성
 * </pre>
 * @since 2019-09-02
 */
public class NoEscapingJsonFactoryDecorator implements JsonFactoryDecorator {
    @Override
    public MappingJsonFactory decorate(MappingJsonFactory factory) {
        ObjectMapper codec = factory.getCodec();
        codec.setDateFormat(new ISO8601DateFormat());
        return (MappingJsonFactory) factory.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
    }
}
