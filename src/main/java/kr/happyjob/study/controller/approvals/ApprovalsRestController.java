package kr.happyjob.study.controller.approvals;

import kr.happyjob.study.service.approvals.ApprovalServiceImpl;
import kr.happyjob.study.service.approvals.ApprovalsService;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApprovalsRestController {

    @Autowired
    private ApprovalServiceImpl as;

    /* 전체 신청 목록 출력 */
    @RequestMapping("/approvals/showList")
    public ApprovalsVO showAllApprovalsList(){
        ApprovalsVO approvalVO = new ApprovalsVO();
        approvalVO=as.showApprovalsList();

        return approvalVO;
    }//end showAllApprocalsList

    /* 전체 신청 목록 갯수 출력 */
    @RequestMapping("/approvals/getTotalCnt")
    public int getTotalListCnt(){
        int cnt=0;
        cnt=as.totalCnt();

        return cnt;
    }//end getTotalListCnt



}//end class
