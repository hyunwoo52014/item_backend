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
    @GetMapping("applyData")
    @ResponseBody
    public Map<String, Object> applyData(@RequestParam Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        try {
            int currentPage = paramMap.get("currentPage") != null ?
                    Integer.parseInt(paramMap.get("currentPage").toString()) : 1;
            int pageSize = paramMap.get("pageSize") != null ?
                    Integer.parseInt(paramMap.get("pageSize").toString()) : 10;
            int pageIndex = (currentPage - 1) * pageSize;

            paramMap.put("pageIndex", pageIndex);
            paramMap.put("pageSize", pageSize);

            List<ApplyModel> applyList = applyService.applyList(paramMap);
            int applyCnt = applyService.applyCnt(paramMap);

            result.put("totalcnt", applyCnt);
            result.put("datalist", applyList);
        } catch (Exception e) {
            // DB 에러 대비: 기본값 내려줌
            result.put("totalcnt", 0);
            result.put("datalist", Collections.emptyList());
            System.out.println("applyData ERROR >>> " + e.getMessage());
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
        int res = applyService.applyReg(paramMap);
        return getResult(res, "신청되었습니다.", "신청 실패");
    }

    /** 반납 */
    @PostMapping("applyReturn")
    @ResponseBody
    public Map<String, Object> applyReturn(@RequestParam Map<String, Object> paramMap) throws Exception {
        int res = applyService.applyReturn(paramMap);
        return getResult(res, "반납 신청되었습니다.", "반납 신청 실패");
    }

    /** 취소 */
    @PostMapping("applyCancel")
    @ResponseBody
    public Map<String, Object> applyCancel(@RequestParam Map<String, Object> paramMap) throws Exception {
        int res = applyService.applyCancel(paramMap);
        return getResult(res, "취소 되었습니다.", "취소 실패");
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
