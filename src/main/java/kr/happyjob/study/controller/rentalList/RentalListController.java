package kr.happyjob.study.controller.rentalList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.vo.rentalList.RentalListModel;
import kr.happyjob.study.service.rentalList.RentalListService;

@Controller
@RequestMapping("/system/")
public class RentalListController {

    @Autowired
    RentalListService rentalListService;

    // Set logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get class name for logger
    private final String className = this.getClass().toString();

    // 대여 현황 리스트 출력 (메인 페이지)
    @RequestMapping("rent")
    public String userMgmtList(Model model, @RequestParam Map<String, Object> paramMap,
                               HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - paramMap : " + paramMap);

        // 파라미터 기본값 설정
        String currentPageStr = (String) paramMap.get("currentPage");
        String pageSizeStr = (String) paramMap.get("pageSize");

        int currentPage = 1; // 기본값
        int pageSize = 10;   // 기본값

        // 파라미터가 있는 경우에만 파싱
        if (currentPageStr != null && !currentPageStr.isEmpty()) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 검색 조건을 포함하여 전체 데이터 조회 (페이징 없음)
        List<RentalListModel> allRentalList = rentalListService.rentalList(paramMap);

        // Java에서 페이징 처리
        int totalCount = allRentalList.size();
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalCount);

        List<RentalListModel> pagedRentalList;
        if (startIndex < totalCount) {
            pagedRentalList = allRentalList.subList(startIndex, endIndex);
        } else {
            pagedRentalList = new ArrayList<>();
        }

        model.addAttribute("rentalList", pagedRentalList);
        model.addAttribute("rentalListCnt", totalCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", currentPage);

        logger.info("   - rentalListCnt : " + totalCount);
        logger.info("   - pageSize : " + pageSize);
        logger.info("   - currentPage : " + currentPage);

        return "system/rentalList/rentalListPage";
    }

    // AJAX 요청을 위한 대여 현황 리스트 조회 (페이징 처리) - JSON 응답으로 수정
    @RequestMapping("rentalList.do")
    @ResponseBody  // 이 어노테이션 추가!
    public Map<String, Object> getRentalListAjax(@RequestParam Map<String, Object> paramMap,
                                                 HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        logger.info("   - AJAX paramMap : " + paramMap);

        // 응답용 Map 생성
        Map<String, Object> result = new HashMap<>();

        try {
            // 파라미터 기본값 설정 및 이름 통일
            Object currentPageObj = paramMap.get("currentPage");
            Object pageSizeObj = paramMap.get("pageSize");
            if (pageSizeObj == null) {
                pageSizeObj = paramMap.get("pagesize"); // JavaScript에서 pagesize로 보낸 경우
            }

            int currentPage = 1; // 기본값
            int pageSize = 10;   // 기본값

            // 파라미터가 있는 경우에만 파싱
            if (currentPageObj != null) {
                if (currentPageObj instanceof String) {
                    String currentPageStr = (String) currentPageObj;
                    if (!currentPageStr.isEmpty()) {
                        currentPage = Integer.parseInt(currentPageStr);
                    }
                } else if (currentPageObj instanceof Integer) {
                    currentPage = (Integer) currentPageObj;
                }
            }

            if (pageSizeObj != null) {
                if (pageSizeObj instanceof String) {
                    String pageSizeStr = (String) pageSizeObj;
                    if (!pageSizeStr.isEmpty()) {
                        pageSize = Integer.parseInt(pageSizeStr);
                    }
                } else if (pageSizeObj instanceof Integer) {
                    pageSize = (Integer) pageSizeObj;
                }
            }

            // 검색 키워드 처리
            String searchKeyword = (String) paramMap.get("searchKeyword");
            if (searchKeyword != null) {
                searchKeyword = searchKeyword.trim();
                if (searchKeyword.isEmpty()) {
                    paramMap.remove("searchKeyword");
                    searchKeyword = null;
                }
            }

            logger.info("   - searchKeyword : " + searchKeyword);
            logger.info("   - currentPage : " + currentPage);
            logger.info("   - pageSize : " + pageSize);

            // 전체 데이터 조회 (검색 조건 포함)
            List<RentalListModel> allRentalList = rentalListService.rentalList(paramMap);
            logger.info("   - allRentalList size : " + allRentalList.size());

            // 검색어가 있는 경우 페이징 처리 안함 (전체 결과 표시)
            List<RentalListModel> resultList;
            int totalCount = allRentalList.size();

            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                // 검색의 경우: 전체 결과 반환
                resultList = allRentalList;
                logger.info("   - Search mode: returning all " + totalCount + " results");
            } else {
                // 일반 조회의 경우: 페이징 처리
                int startIndex = (currentPage - 1) * pageSize;
                int endIndex = Math.min(startIndex + pageSize, totalCount);

                if (startIndex < totalCount) {
                    resultList = allRentalList.subList(startIndex, endIndex);
                } else {
                    resultList = new ArrayList<>();
                }
                logger.info("   - Paging mode: startIndex=" + startIndex + ", endIndex=" + endIndex + ", resultSize=" + resultList.size());
            }

            // JSON 응답 데이터 설정
            result.put("rentalList", resultList);
            result.put("rentalListCnt", totalCount);
            result.put("pageSize", pageSize);
            result.put("currentPage", currentPage);
            result.put("success", true);

            logger.info("   - Final result size: " + resultList.size());
            logger.info("   - Final totalCount: " + totalCount);

        } catch (Exception e) {
            logger.error("Error in getRentalListAjax: ", e);
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("rentalList", new ArrayList<>());
            result.put("rentalListCnt", 0);
        }

        return result; // JSON으로 자동 변환되어 응답
    }
}