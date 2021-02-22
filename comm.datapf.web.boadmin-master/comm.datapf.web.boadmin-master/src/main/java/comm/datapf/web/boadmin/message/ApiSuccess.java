package comm.datapf.web.boadmin.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccess<T> {
    /** 상태(성공메시지이므로 success 고정) */
    private String status = "success";

    /** 결과 데이터 */
    private T data;

    /** 페이징 정보 */
    private Link links;
}
