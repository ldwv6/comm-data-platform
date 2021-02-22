package comm.datapf.web.boadmin.message;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Link {
    private int prev;
    private int[] pages;
    private int next;
    private int last;
    private long totalCnt;

    public Link() {

    }

    @SuppressWarnings("rawtypes")
    public Link(PageInfo pageInfo) {
        this.prev = pageInfo.getPrePage();
        this.next = pageInfo.getNextPage();
        this.last = pageInfo.getPages();
        this.pages = pageInfo.getNavigatepageNums();
        this.totalCnt = pageInfo.getTotal();
    }
}
