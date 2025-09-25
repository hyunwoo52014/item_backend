package kr.happyjob.study.controller.notice;

import kr.happyjob.study.service.notice.NoticeService;
import kr.happyjob.study.vo.notice.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")

public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @PostMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> paramMap) throws Exception {
        System.out.println("=== Notice List API Called ===");
        System.out.println("Request params: " + paramMap);

        // 조회
        try {
            int currentPage = parseInt(paramMap.get("currentPage"), 1);
            int pageSize = parseInt(paramMap.get("pageSize"), 5);
            int pageIndex = Math.max(0, (currentPage - 1) * pageSize);

            paramMap.put("pageIndex", pageIndex);
            paramMap.put("pageSize", pageSize);

            if (paramMap.containsKey("front_date")) {
                paramMap.put("from_date", paramMap.get("front_date"));
            }
            // 프론트에서 넘어온 'back_date'를 'to_date'로 변경
            if (paramMap.containsKey("back_date")) {
                paramMap.put("to_date", paramMap.get("back_date"));
            }

            //지금 됨
            System.out.println("들어온 Final paramMap for DB: " + paramMap);
            //날짜
            System.out.println("front-date : " + paramMap.get("front_date"));
            System.out.println("back-date : " + paramMap.get("back_date"));

            // 조회
            List<NoticeVO> rows = noticeService.noticeList(paramMap);
            int totalCnt = noticeService.noticeCnt(paramMap);

            //이거 들어옴
            //System.out.println("DB Result - rows size: " + (rows != null ? rows.size() : "null"));
            //System.out.println("DB Result - totalCnt: " + totalCnt);
            //System.out.println("First row data: " + (rows != null && !rows.isEmpty() ? rows.get(0) : "no data"));

            Map<String, Object> res = new HashMap<>();
            res.put("list", rows);
            res.put("totalCnt", totalCnt);

            //System.out.println("Response: " + res);
            return res;

        } catch (Exception ex) {
            //System.out.println("Exception occurred: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    // 검색
    private static int parseInt(Object v, int def) {
        try { return Integer.parseInt(String.valueOf(v)); } catch (Exception e) { return def; }
    }

    @PostMapping(value ="/insert")
    @ResponseBody
    // 추가
    public Map<String, Object> insert (@RequestParam Map<String, Object> paramMap) throws Exception {
        int insResult = noticeService.insertNotice(paramMap);

        Map<String, Object> res=new HashMap<>();
        res.put("success", insResult >0);
        return res;
    }
    @PostMapping("/delete")
    @ResponseBody
    //삭제
    public Map<String, Object> delete(@RequestParam Map<String, Object> paramMap) throws Exception {
        int delResult  = noticeService.deleteNotice(paramMap);

        Map<String, Object> res= new HashMap<>();
        res.put("success", delResult >0);
        return res;
    }

    @PostMapping("/detail")
    @ResponseBody
    public Map<String, Object> detail(@RequestParam Map<String, Object> paramMap) throws Exception {
        NoticeVO detail = noticeService.noticeDetail(paramMap);

        Map<String, Object> res = new HashMap<>();
        res.put("success", detail != null);
        res.put("detail", detail);

        System.out.println("Response: " + res);
        return res;
    }

    // 업데이트
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestParam Map<String, Object> paramMap) throws Exception {
        int updateResult = noticeService.updateNotice(paramMap);

        Map<String, Object> res = new HashMap<>();
        res.put("success", updateResult > 0);
        return res;
    }

}
