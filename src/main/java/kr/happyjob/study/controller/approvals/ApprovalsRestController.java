package kr.happyjob.study.controller.approvals;

import kr.happyjob.study.service.approvals.ApprovalServiceImpl;
import kr.happyjob.study.service.approvals.ApprovalsService;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ApprovalsRestController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApprovalServiceImpl as;

    /* 전체 신청 목록 출력 */
    @RequestMapping("/approvals/showList")
    public List<ApprovalsVO> showAllApprovalsList(){

        List<ApprovalsVO> list=as.showApprovalsList();
        return list;
    }//end showAllApprocalsList

    /* 전체 신청 목록 갯수 출력 */
    @RequestMapping("/approvals/getTotalCnt")
    public int getTotalListCnt(){
        int cnt=0;
        cnt=as.totalCnt();

        return cnt;
    }//end getTotalListCnt

    /**
     * 장비코드(카테고리명, 장비상세코드), 사용자이름, 신청날짜 비교 <br/>
     * 해당되는 사유 반환
     * @param oneRowDataApprovalsVO
     * @return String 장비신청 사유 혹은 반납 사유 적은거 반환
     */
    @RequestMapping("/approvals/getWrittenPurpose")
    public String getWrittenPurpose(@RequestBody ApprovalsVO oneRowDataApprovalsVO){
        String strPurpose = "";
        logger.info("/approvals/getWrittenPurpose..---=======" + oneRowDataApprovalsVO);
        strPurpose=as.getWrittenReason(oneRowDataApprovalsVO);

        logger.info("strPurpose-------======"+ strPurpose);
        return strPurpose;
    }//getWrittenPurpose


}//end class
