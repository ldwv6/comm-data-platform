package comm.datapf.web.boadmin.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ClsgRvnuController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*매출집계표 - 쇼핑/도서 */
    @RequestMapping("/clsgRvnu/shopBook")
    public String clsgRvnuShopBook() throws Exception {
        return "views/clsg/rvnu/clsg_rvnu_shop_book";
    }

    /*매출집계표 - 공연 */
    @RequestMapping("/clsgRvnu/ent")
    public String clsgSale() throws Exception {
        return "views/clsg/rvnu/clsg_rvnu_ent";
    }

}
