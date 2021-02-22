package comm.datapf.web.boadmin.domain.dto.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuPgmDto {
    // ADMR_PGM_BASE  통합_프로그램관리기본
    @ApiModelProperty(position = 1,required = false, value = "프로그램ID")
    private String pgmId;

    @ApiModelProperty(position = 2,required = false, value = "프로그램명")
    private String pgmNm;

    @ApiModelProperty(position = 3,required = false, value = "상위프로그램")
    private String prenId;

    @ApiModelProperty(position = 4,required = false, value = "프로그램구분코드")
    private String pgmDcsnCd;

    @ApiModelProperty(position = 5,required = false, value = "정렬순서")
    private int sort;

    @ApiModelProperty(position = 6,required = false, value = "소스경로")
    private String srcPath;

    @ApiModelProperty(position = 7,required = false, value = "상세가능여부")
    private String detailYn;

    @ApiModelProperty(position = 8,required = false, value = "조회가능여부")
    private String selectYn;

    @ApiModelProperty(position = 9,required = false, value = "등록가능여부")
    private String insertYn;

    @ApiModelProperty(position = 10,required = false, value = "변경가능여부")
    private String updateYn;

    @ApiModelProperty(position = 11,required = false, value = "등록관리출력가능여부자ID")
    private String printYn;

    @ApiModelProperty(position = 12,required = false, value = "사용여부")
    private String useYn;

    @ApiModelProperty(position = 13,required = false, value = "비고")
    private String rmks;

    @ApiModelProperty(position = 14,required = false, value = "등록관리자ID")
    private String erlmAdmrId;

    @ApiModelProperty(position = 15,required = false, value = "등록관리자IP")
    private String erlmAdmrIp;

    @ApiModelProperty(position = 16,required = false, value = "등록일시")
    private String erlmDtime;

    @ApiModelProperty(position = 17,required = false, value = "변경관리자ID")
    private String chngAdmrId;

    @ApiModelProperty(position = 18,required = false, value = "변경관리자IP")
    private String chngAdmrIp;

    @ApiModelProperty(position = 19,required = false, value = "변경일시")
    private String chngDtime;

    @ApiModelProperty(position = 20,required = false, value = "개인정보취급여부")
    private String psnlTrtmYn;

    @ApiModelProperty(position = 21,required = false, value = "메뉴ID")
    private int menuId;

    @ApiModelProperty(position = 22,required = false, value = "메뉴뎁스")
    private int menuDepth;

    List<MenuPgmDto> subMenus = new ArrayList<>();

    public void addSubMenu(MenuPgmDto menu) {
        subMenus.add(menu);
    }

    /*public String toHierarchicalString() {
        return _toString(this);
    }

    public String toMinimalizedStrong() {
        return StringUtils.leftPad(" ", this.getMenuDepth()) + this.getPgmId() + "/" + this.getPgmNm();
    }

    private String _toString(MenuPgmDto menu) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.toMinimalizedStrong());

        if(menu.getSubMenus() == null || menu.getSubMenus().isEmpty())      return sb.toString();

        MenuPgmDto subMenu;
        for(int idx = 0; idx < menu.getSubMenus().size(); idx++) {
            subMenu = menu.getSubMenus().get(idx);

            sb.append(subMenu.toHierarchicalString());
        }

        return sb.toString();
    }*/

}
