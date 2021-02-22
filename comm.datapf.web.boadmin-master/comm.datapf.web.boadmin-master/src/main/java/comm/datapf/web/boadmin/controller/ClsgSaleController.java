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
public class ClsgSaleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /* 결제집계표 */
    @RequestMapping("/clsgSale")
    public String clsgSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "views/clsg/sale/clsg_sale";
    }


}
