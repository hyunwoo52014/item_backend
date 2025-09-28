package kr.happyjob.study.controller.requests;

import kr.happyjob.study.service.requests.ApplyService;
import kr.happyjob.study.vo.requests.ApplyModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;
import java.util.ArrayList;
@Controller
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:3000") // 이 컨트롤러의 API만 허용
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /** 리스트 조회 (React 호출) */
    @GetMapping("/applyData")
    @ResponseBody
    public Map<String, Object> applyData(@RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            int currentPage = paramMap.get("currentPage") != null ?
                    Integer.parseInt(paramMap.get("currentPage").toString()) : 1;
            int pageSize = paramMap.get("pageSize") != null ?
                    Integer.parseInt(paramMap.get("pageSize").toString()) : 10;
            int pageIndex = (currentPage - 1) * pageSize;

            paramMap.put("pageIndex", pageIndex);
            paramMap.put("pageSize", pageSize);

            // Frontend 파라미터를 MyBatis 매퍼에서 사용하는 파라미터명으로 변환
            String searchsel = (String) paramMap.get("searchsel");
            String searchword = (String) paramMap.get("searchword");

            // 검색 조건을 그대로 MyBatis로 전달 (MyBatis에서 처리)
            paramMap.put("searchsel", searchsel);
            paramMap.put("searchword", searchword);

            // 사용자 권한별 조회 제한 추가
            String userLoginId = (String) paramMap.get("userLoginId");
            String userType = (String) paramMap.get("userType");

            // 권한별 접근 제한: admin이 아닌 경우 본인 신청 건만 조회
            if (!"A".equals(userType) && !"admin".equals(userLoginId)) {
                // 일반 사용자인 경우
                if (userLoginId != null && !userLoginId.trim().isEmpty()) {
                    paramMap.put("loginIdFilter", userLoginId); // 본인 신청 건만 필터링
                    System.out.println("일반 사용자 조회 - loginId: " + userLoginId);
                } else {
                    // 로그인 정보가 없는 경우 빈 결과 반환
                    result.put("totalcnt", 0);
                    result.put("datalist", Collections.emptyList());
                    return result;
                }
            } else {
                // admin인 경우 모든 데이터 조회 가능
                System.out.println("관리자 조회 - 모든 데이터 접근 가능");
            }

            // 디버깅 로그 추가
            System.out.println("=== 검색 파라미터 ===");
            System.out.println("searchsel: " + searchsel);
            System.out.println("searchword: " + searchword);
            System.out.println("currentPage: " + currentPage);
            System.out.println("pageSize: " + pageSize);
            System.out.println("pageIndex: " + pageIndex);
            System.out.println("userLoginId: " + userLoginId);
            System.out.println("userType: " + userType);

            List<ApplyModel> applyList = applyService.applyList(paramMap);
            int applyCnt = applyService.applyCnt(paramMap);

            System.out.println("=== 조회 결과 ===");
            System.out.println("조회된 데이터 수: " + applyList.size());
            System.out.println("전체 데이터 수: " + applyCnt);

            result.put("totalcnt", applyCnt);
            result.put("datalist", applyList);
        } catch (Exception e) {
            // DB 에러 대비: 기본값 내려줌
            result.put("totalcnt", 0);
            result.put("datalist", Collections.emptyList());
            System.out.println("applyData ERROR >>> " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /** 상세 조회 */
    @GetMapping("detailApply")
    @ResponseBody
    public ApplyModel detailApply(@RequestParam Map<String, Object> paramMap) throws Exception {
        return applyService.applyDetail(paramMap);
    }

    /** 신청 */
    @PostMapping("applyReq")
    @ResponseBody
    public Map<String, Object> applyReq(@RequestParam Map<String, Object> paramMap) throws Exception {
        System.out.println("=== 사용신청 요청 ===");
        System.out.println("전달받은 파라미터: " + paramMap);
        try {
            int res = applyService.applyReg(paramMap);
            System.out.println("신청 결과: " + res);
            return getResult(res, "신청되었습니다.", "신청 실패");
        } catch (Exception e) {
            System.out.println("신청 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /** 반납 */
    @PostMapping("applyReturn")
    @ResponseBody
    public Map<String, Object> applyReturn(@RequestParam Map<String, Object> paramMap) throws Exception {
        System.out.println("=== 반납신청 요청 ===");
        System.out.println("전달받은 파라미터: " + paramMap);
        try {
            int res = applyService.applyReturn(paramMap);
            System.out.println("반납 결과: " + res);
            return getResult(res, "반납 신청되었습니다.", "반납 신청 실패");
        } catch (Exception e) {
            System.out.println("반납 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /** 취소 */
    @PostMapping("applyCancel")
    @ResponseBody
    public Map<String, Object> applyCancel(@RequestParam Map<String, Object> paramMap) throws Exception {
        System.out.println("=== 신청취소 요청 ===");
        System.out.println("전달받은 파라미터: " + paramMap);
        try {
            int res = applyService.applyCancel(paramMap);
            System.out.println("취소 결과: " + res);
            return getResult(res, "취소 되었습니다.", "취소 실패");
        } catch (Exception e) {
            System.out.println("취소 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /** 카테고리 목록 조회 */
    @GetMapping("/categories")
    @ResponseBody
    public List<Map<String, Object>> getCategories() throws Exception {
        return applyService.getCategories();
    }

    /** 공통 응답 */
    private Map<String, Object> getResult(int res, String successMsg, String failMsg) {
        Map<String, Object> map = new HashMap<>();
        if (res > 0) {
            map.put("result", "SUCCESS");
            map.put("resultMsg", successMsg);
        } else {
            map.put("result", "FAIL");
            map.put("resultMsg", failMsg);
        }
        return map;
    }
}
