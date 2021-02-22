package comm.datapf.web.boadmin.controller;

import comm.datapf.web.boadmin.domain.dto.role.MenuPgmDto;
import comm.datapf.web.boadmin.interceptor.FrontInterceptor;
import comm.datapf.web.boadmin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    FrontInterceptor frontInterceptor;

    @RequestMapping(value="/main", method={RequestMethod.POST, RequestMethod.GET})
    public String memberMain(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception{
        //    	ModelAndView mav = new ModelAndView("memberMain/memberMain");
        HttpSession session = request.getSession();
        List<MenuPgmDto> menuList = new ArrayList<MenuPgmDto>();
        menuList = (List<MenuPgmDto>) session.getAttribute("menuList");

        String setViewName = "";
        if(menuList.size() > 0) {
            String membListName = "membershipRetrieve";
            for(int i=0; i < menuList.size(); i++) {
                String viewName = StringUtils.nvl(menuList.get(i).getSrcPath(), "");
                if(StringUtils.isNotEmpty(viewName)){
                    viewName = "views/main/main";
                    setViewName = viewName;
                    break;
                }
            }
            //회원조회를 포함하는 관리자는 회원조회가 기본화면으로 셋팅
            for(int i=0; i < menuList.size(); i++) {
                String viewName = StringUtils.nvl(menuList.get(i).getSrcPath(), "");
                if(StringUtils.isNotEmpty(viewName)){
                    if(StringUtils.equals(membListName, viewName)){
                        setViewName = "redirect:"+membListName;
                        break;
                    }
                }
            }
        }else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out=response.getWriter();
            frontInterceptor.front_msg_script(out);
            out.print("alert('메뉴권한이 없어 로그아웃됩니다. 관리자에게 문의하세요.', function(){location.href='/logout'},'0000');");
            frontInterceptor.after_msg_script(out);
            out.close();
        }
        return setViewName;
    }
}
