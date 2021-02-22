package comm.datapf.web.boadmin.domain.dto.common;

import lombok.Getter;
import lombok.Setter;


public class SearchRepository {
    @Getter
    @Setter
    /** 페이지 번호 */
    private int pageNo = 1;

    @Getter
    @Setter
    /** 페이지 당 항목 수 */
    private int pageSize = 10;

    public boolean hasStartSlash() {
        return false;
    }
}
