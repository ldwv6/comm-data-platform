package comm.datapf.web.boadmin.domain.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString
@Alias("LoginDto")
public class LoginDto {
    // ADMR_BASE_INFO 통합_관리자기본
    @ApiModelProperty(position = 1,required = false, value = "관리자ID")
    private String admrId;

    @ApiModelProperty(position = 2,required = false, value = "관리자명")
    private String  admrNm;

    @ApiModelProperty(position = 3,required = false, value = "패스워드")
    private String pwd;

    @ApiModelProperty(position = 4,required = false, value = "ADMR_GRAD_CD")
    private String admrGradCd;

    @ApiModelProperty(position = 5,required = false, value = "사용여부")
    private String  useYn;

    @ApiModelProperty(position = 6,required = false, value = "관리자EMAIL주소")
    private String admrEmal;

    @ApiModelProperty(position = 7,required = false, value = "비밀번호재설정여부")
    private String pwdReStupYn;

    @ApiModelProperty(position = 8,required = false, value = "비밀번호변경일시")
    private String pwdChngDtime;

    @ApiModelProperty(position = 9,required = false, value = "패스워드오류횟수")
    private int pwdErrRcnt;

    @ApiModelProperty(position = 10,required = false, value = "관리자상태")
    private String admrStatCd;

    @ApiModelProperty(position = 11,required = false, value = "사번")
    private String empyNo;

    @ApiModelProperty(position = 12,required = false, value = "부문명")
    private String prtlNm;

    @ApiModelProperty(position = 13,required = false, value = "부서명")
    private String dpmtNm;

    @ApiModelProperty(position = 14,required = false, value = "팀명")
    private String teamNm;

    @ApiModelProperty(position = 15,required = false, value = "개인정보취급여부")
    private String  psnlTrtmYn;

    @ApiModelProperty(position = 16,required = false, value = "최종로그인일시")
    private String lastLinDtime;

    @ApiModelProperty(position = 17,required = false, value = "등록일시")
    private String erlmDtime;

    @ApiModelProperty(position = 18,required = false, value = "등록관리자ID")
    private String erlmAdmrId;

    @ApiModelProperty(position = 19,required = false, value = "등록관리자IP")
    private String erlmAdmrIp;

    @ApiModelProperty(position = 20,required = false, value = "변경일시")
    private String chngDtime;

    @ApiModelProperty(position = 21,required = false, value = "변경관리자ID")
    private String chngAdmrId;

    @ApiModelProperty(position = 22,required = false, value = "변경관리자IP")
    private String chngAdmrIp;

    @ApiModelProperty(position = 23,required = false, value = "관리자번호")
    private int admrNo;

    @ApiModelProperty(position = 24,required = false, value = "Role ID")
    private String roleId;

    @ApiModelProperty(position = 25,required = false, value = "Role 명")
    private String roleNm;

    // 변수 추가할지 dto를 가져올지 확인 start 26~32
    @ApiModelProperty(position = 26,required = false, value = "사용자 IP")
    private String ip;

    @ApiModelProperty(position = 27,required = false, value = "접근 가능 IP")
    private String connPsblIp;

    @ApiModelProperty(position = 28,required = false, value = "접근 차단 IP 리스트")
    private List<HashMap<String, Object>> blockIpList;

    @ApiModelProperty(position = 29,required = false, value = "접근 가능 시작 시간")
    private String connPsblStrtTime;

    @ApiModelProperty(position = 30,required = false, value = "접근 가능 종료 시간")
    private String connPsblEndTime;

    @ApiModelProperty(position = 31,required = false, value = "로그인 시간")
    private String loginTime;

    @ApiModelProperty(position = 32,required = false, value = "로그아웃 시간")
    private String logoutTime;

    @ApiModelProperty(position = 33,required = false, value = "로그아웃 시간")
    private int pwdRemainDt;
}
