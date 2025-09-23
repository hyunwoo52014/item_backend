package kr.happyjob.study.controller.requests;

import kr.happyjob.study.service.requests.HistoryService;
import kr.happyjob.study.vo.requests.HistoryModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/requests/")
public class RequestsController {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final String className = this.getClass().toString();
    private final HistoryService historyService;

    public RequestsController(@Autowired HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping("historyList")
    @ResponseBody
    public Map<String, Object> historyList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        logger.info("+ Start " + className + ".historyList");
        Map<String, Object> resultMap = new HashMap<>();
        String loginId = String.valueOf(session.getAttribute("loginId"));
        String userType = String.valueOf(session.getAttribute("userType"));

        if(!"A".equals(userType)){
            paramMap.put("loginID", loginId);
        }

        int currentPage = Integer.parseInt((String) paramMap.get("currentPage"));
        int pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
        int pageIndex = (currentPage - 1) * pageSize;

        paramMap.put("searchKey", paramMap.get("searchSel"));
        paramMap.put("search", paramMap.get("searchTitle"));
        paramMap.put("currentPage", currentPage);
        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);

        List<HistoryModel> modelList = historyService.historyList(paramMap);
        int historyCnt = historyService.historyCnt(paramMap);

        resultMap.put("historyList", modelList);
        resultMap.put("historyCnt", historyCnt);
        resultMap.put("currentPage", currentPage);
        resultMap.put("pageSize", pageSize);

        logger.info("+ end " + className + ".historyList");
        return resultMap;
    }
}
