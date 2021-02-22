package comm.datapf.web.boadmin.service.impl;

import comm.datapf.web.boadmin.domain.dao.TrsfDao;
import comm.datapf.web.boadmin.domain.dto.common.ResultVo;
import comm.datapf.web.boadmin.domain.dto.trsf.TrsfGranterVo;
import comm.datapf.web.boadmin.domain.dto.trsf.TrsfPymntVo;
import comm.datapf.web.boadmin.domain.dto.trsf.TrsfReceiverVo;
import comm.datapf.web.boadmin.domain.dto.trsf.UpdateTrsfParamVo;
import comm.datapf.web.boadmin.message.ApiSuccess;
import comm.datapf.web.boadmin.service.TrsfService;
//import comm.datapf.web.boadmin.utils.CommonUtils;
//import comm.datapf.web.boadmin.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.PrintWriter;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrsfServiceImpl implements TrsfService {

    private final TrsfDao trsfDao;

    @Override
    public ApiSuccess doSelectGranter(HashMap<String, Object> input) throws Exception {
        ApiSuccess<List<TrsfGranterVo>> result = new ApiSuccess<List<TrsfGranterVo>>();

        result.setData(trsfDao.getTrsfGranter(input));

        return result;
    }

    @Override
    public ApiSuccess doSelectReceiver(HashMap<String, Object> input) throws Exception {
        ApiSuccess<List<TrsfReceiverVo>> result = new ApiSuccess<List<TrsfReceiverVo>>();

        result.setData(trsfDao.getTrsfReceiver(input));

        return result;
    }

    @Override
    public ApiSuccess doSelectPymnt(HashMap<String, Object> input) throws Exception {
        ApiSuccess<List<TrsfPymntVo>> result = new ApiSuccess<List<TrsfPymntVo>>();

        result.setData(trsfDao.getTrsfPymnt(input));

        return result;
    }

    @Override
    public ApiSuccess doUpdateTrsf(UpdateTrsfParamVo param) throws Exception {
        ApiSuccess<ResultVo> result = new ApiSuccess<ResultVo>();
        ResultVo res = ResultVo.getInstance();

        if (false) {
            res.setCode("01");
            res.setMsg("예외발생");

            result.setData(res);
        }
        else result.setData(res);

        return result;
    }
}
