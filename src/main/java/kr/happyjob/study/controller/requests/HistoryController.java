package kr.happyjob.study.controller.requests;

import kr.happyjob.study.service.requests.HistoryService;
import kr.happyjob.study.vo.requests.HistoryModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/requests/")
public class HistoryController {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final String className = this.getClass().toString();
    private final HistoryService historyService;

    public HistoryController(@Autowired HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping("subCategoryList")
    @ResponseBody
    public List<String> subCategoryList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                               HttpServletResponse response,HttpSession session) {
        logger.info("+ Start" + className + ".subCategoryList");
        logger.info("+ >>>>>> paramMap" + paramMap);
        Set<String> resultSet = historyService.subCategory(paramMap);
        List<String> tempList = new ArrayList<>(resultSet);

        if("status".equalsIgnoreCase((String)paramMap.get("flag"))) {
            Set<String> tempSet = new HashSet<>();
            for(String s : tempList) {
                if("Y".equalsIgnoreCase(s)) {
                    tempSet.add("승인");
                } else if("N".equalsIgnoreCase(s) || "R".equalsIgnoreCase(s)) {
                    tempSet.add("반려");
                } else {
                    tempSet.add("오류");
                }
            }
            tempList = new ArrayList<>(tempSet);
        }

        logger.info("+ End" + className + ".subCategoryList");
        return tempList;
    }

    @RequestMapping("adminHistoryList")
    @ResponseBody
    public Map<String, Object> adminHistoryList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        logger.info("+ Start " + className + ".adminHistoryList");
        Map<String, Object> resultMap = new HashMap<>();

        String userType = String.valueOf(session.getAttribute("userType"));
        paramMap.put("userType", userType);

        int currentIndex = Integer.parseInt((String) paramMap.get("currentIndex"));
        int pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
        int pageIndex = currentIndex * pageSize;


        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);

        if("승인".equalsIgnoreCase((String)paramMap.get("searchSubSel"))) {
            paramMap.put("searchSubSel", "Y");
        }

        if("반려".equalsIgnoreCase((String)paramMap.get("searchSubSel"))) {
            paramMap.put("searchSubSel_N", "N");
            paramMap.put("searchSubSel_R", "R");
        }

        if("오류".equalsIgnoreCase((String)paramMap.get("searchSubSel"))){
            paramMap.put("searchSubSel", "미판별");
        }


        logger.info(" data check");
        for(Map.Entry<String, Object> entry : paramMap.entrySet()){
            logger.info("key : " + entry.getKey() + " value : " + entry.getValue() + " value type : " + entry.getValue().getClass());
        }

        List<HistoryModel> modelList = historyService.historyList(paramMap);
        int historyCnt = historyService.historyCnt(paramMap);

        resultMap.put("historyList", modelList);
        resultMap.put("historyCnt", historyCnt);
        resultMap.put("currentPage", currentIndex+1);
        resultMap.put("pageSize", pageSize);

        logger.info("+ end " + className + ".adminHistoryList");
        return resultMap;
    }

    @RequestMapping("userHistoryList")
    @ResponseBody
    public Map<String, Object> userHistoryList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                           HttpServletResponse response, HttpSession session) {
        logger.info("+ Start " + className + ".userHistoryList");
        Map<String, Object> resultMap = new HashMap<>();

        String loginId = String.valueOf(session.getAttribute("loginId"));
        String userType = String.valueOf(session.getAttribute("userType"));

        paramMap.put("loginID", loginId);
        paramMap.put("userType", userType);

        int currentIndex = Integer.parseInt((String) paramMap.get("currentIndex"));
        int pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
        int pageIndex = currentIndex * pageSize;

        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);

       switch(((String)paramMap.get("searchMajorSel")).toUpperCase()){
           case "RENTALDATE" :
               logger.info((String)paramMap.get("searchSubSel"));
               Date rentalDate = new Date(Long.parseLong((String)paramMap.get("searchSubSel")));
               SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
               paramMap.put("searchSubSel", sdf1.format(rentalDate));
               break;
           case "RETURNDATE" :
               logger.info((String)paramMap.get("searchSubSel"));
               Date returnDate = new Date(Long.parseLong((String)paramMap.get("searchSubSel")));
               SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
               paramMap.put("searchSubSel", sdf2.format(returnDate));
               break;
           case "STATUS" :
               paramMap.put("searchSubSel_N", "N");
               paramMap.put("searchSubSel_R", "R");
               break;
           default :
               break;
       }

        for(Map.Entry<String, Object> entry : paramMap.entrySet()){
            logger.info("key : " + entry.getKey() + " value : " + entry.getValue() + " value type : " + entry.getValue().getClass());
        }


        List<HistoryModel> modelList = historyService.historyList(paramMap);
        int historyCnt = historyService.historyCnt(paramMap);

        resultMap.put("historyList", modelList);
        resultMap.put("historyCnt", historyCnt);
        resultMap.put("currentPage", currentIndex+1);
        resultMap.put("pageSize", pageSize);

        logger.info("+ end " + className + ".userHistoryList");
        return resultMap;
    }

    @RequestMapping("detailHistory")
    @ResponseBody
    public Map<String, Object> detailHistory(@RequestParam Map<String, Object> paramMap, Model model) throws  Exception {
        logger.info("+ Start " + className + ".detailHistory");
        HistoryModel modelOne = historyService.detailHistory(paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("team", modelOne.getTeam());
        resultMap.put("name", modelOne.getName());
        resultMap.put("productName", modelOne.getProductName());
        resultMap.put("productDetailCode", modelOne.getProductDetailCode());
        resultMap.put("rentalDate", modelOne.getRentalDate());
        resultMap.put("returnDate", modelOne.getReturnDate());
        resultMap.put("orderReason", modelOne.getOrderReason());
        resultMap.put("productNo", modelOne.getProductNo());
        resultMap.put("orderDate", modelOne.getOrderDate());

        logger.info("+ End " + className + ".detailHistory");
        return resultMap;
    }

    @RequestMapping("statusCodeList")
    @ResponseBody
    public List<Map<String, String>> statusCodeList() {
        logger.info("+ Start " + className + ".statusCodeList");
        List<String> list = historyService.statusCodeList();
        
        Set<Map<String, String>> mapList = new HashSet<>();
        Map<String,String> resultMap = null;
        
        for (String s : list) {
            resultMap = new HashMap<>();
            if ("Y".equalsIgnoreCase(s)) {
                resultMap.put("code","Y");
                resultMap.put("value","승인");
            } else if ("N".equalsIgnoreCase(s) || "R".equalsIgnoreCase(s)) {
                resultMap.put("code","N");
                resultMap.put("value","반려");
            } else {
                continue;
            }
            mapList.add(resultMap);
        }
        logger.info(">>>>> mapList " + mapList);
        logger.info("+ End " + className + ".statusCodeList");
        return new ArrayList<>(mapList);
    }

    @RequestMapping("deleteHistory")
    @ResponseBody
    public Map<String, Object> deleteHistory(@RequestParam Map<String, Object> paramMap, Model model) throws  Exception {
        logger.info("+ Start " + className + ".deleteHistory");
        logger.info("└ paramMap" + paramMap);

        Map<String, Object> resultMap = new HashMap<>();
        String result = "";

        try{
            int deleteResult = historyService.deleteHistory(paramMap);

            if(deleteResult > 0) {
                result = "success";
            } else {
                result = "fail";
            }
        } catch (Exception e){
            result = "fail";
            logger.info("폐기 처리 중 오류 발생 : " + e.getMessage());
        }

        resultMap.put("result", result);

        logger.info("+ End " + className + ".deleteHistory");
        return resultMap;
    }
}