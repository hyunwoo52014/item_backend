package kr.happyjob.study.controller.returns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.happyjob.study.service.returns.ReturnsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.happyjob.study.vo.returns.ReturnsModel;

@Controller
@RequestMapping("/requests/returns/")
public class ReturnsController {

    @Autowired
    ReturnsService returnsService;

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final String className = this.getClass().toString();

    /**
     * 사용신청 메인화면
     */
    @RequestMapping("returns")
    public String returns(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("+ Start " + className + ".apply");
        logger.info("    - paramMap : " + paramMap);

        logger.info("+ End " + className + ".apply");

        return "returns/returns";
    }

    /**
     * 사용신청 리스트 조회 (JSON 반환으로 변경)
     */
    @RequestMapping("returnsList")
    @ResponseBody
    public Map<String,Object> returnsList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("+ Start " + className + ".returnsList");
        logger.info("    - paramMap : " + paramMap);

        int currentPage = Integer.parseInt((String) paramMap.get("currentPage"));
        int pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
        int pageIndex = (currentPage - 1) * pageSize;

        String loginId = (String) paramMap.get("loginId");

        paramMap.put("currentPage", currentPage);
        paramMap.put("pageSize", pageSize);
        paramMap.put("pageIndex", pageIndex);
        paramMap.put("loginId", loginId);

        List<ReturnsModel> returnsList = returnsService.returnsList(paramMap);
        int returnsCnt = returnsService.returnsCnt(paramMap);

        // Map에 데이터를 담아 JSON으로 반환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("returnsList", returnsList);
        resultMap.put("totalCount", returnsCnt);
        resultMap.put("pageSize", pageSize);
        resultMap.put("currentPage", currentPage);

        logger.info("+ End " + className + ".returnsList");
        logger.info("returnsList" + returnsList);

        return resultMap; // Map 객체를 반환하면 JSON으로 자동 변환
    }

    /**
     * 장비 일괄 반납 신청
     */
    @RequestMapping("returnAll")
    @ResponseBody
    public Map<String,Object> returnAll(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("+ Start " + className + ".returnAll");
        logger.info("    - paramMap : " + paramMap);

        Map<String,Object> returnMap = new HashMap<String,Object>();
        String result = "";
        String resultMsg = "";

        int res = returnsService.returnAll(paramMap);

        if (res > 0) {
            result = "SUCCESS";
            resultMsg = "신청되었습니다.";
        } else {
            result = "Fail";
            resultMsg = "신청에 실패하였습니다.";
        }

        returnMap.put("result", result);
        returnMap.put("resultMsg", resultMsg);

        logger.info("+ End " + className + ".returnAll");
        logger.info("result =======>" + result + "resultMsg" + resultMsg);
        logger.info("returnMap =======>" + returnMap);

        return returnMap;
    }


}
