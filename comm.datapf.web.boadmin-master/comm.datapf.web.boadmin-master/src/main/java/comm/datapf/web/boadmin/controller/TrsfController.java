package comm.datapf.web.boadmin.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class TrsfController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*양도양수 유보 - 채권 양도양수 */
    @RequestMapping("/trsf/bond")
    public String trsfBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "views/trsf/transferagree_bond";
    }

    /*양도양수 유보 - 계약 양도양수 */
    @RequestMapping("/trsf/contract")
    public String trsfContract(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "views/trsf/transferagree_contract";
    }

    /*양도양수 유보 - 유보관리 */
    @RequestMapping("/trsf/rsvt")
    public String trsfRsvt(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "views/trsf/reservation_manage";
    }
}
