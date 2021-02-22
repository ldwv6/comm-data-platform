package comm.datapf.web.boadmin.domain.dto.common;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * CUD의 대한 응답코드, 메세지 리턴
 *
 * @project 차세대 마감/정산
 * @author T09013
 * @since 2021-02-19
 * @version 1.0
 *<pre>
 *2021.02.19 : 최초 작성
 *</pre>
 */
@Alias("resultVo")
@Getter
@Setter
@ApiModel(value="응답 VO")
public class ResultVo {
    private static final String CODE = "00";
    private static final String MSG = "정상적으로 처리되었습니다.";
    private String code;
    private String msg;

    public ResultVo() {
        this.code = CODE;
        this.msg = MSG;
    }


    public static ResultVo getInstance() {
        return new ResultVo();
    }
}