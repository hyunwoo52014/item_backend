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

//        List<ApprovalsVO> list=as.showApprovalsList();
        return as.showApprovalsList();
    }//end showAllApprocalsList

    /* 전체 신청 목록 갯수 출력 */
    @RequestMapping("/approvals/getTotalCnt")
    public int getTotalListCnt(){
        int cnt=0;
        cnt=as.totalCnt();

        return cnt;
    }//end getTotalListCnt


    /* 승인 버튼을 눌렀을 때 실행 */
    @RequestMapping("/approvals/clickApprovals")
    public int clickApprovals(@RequestBody ApprovalsVO approvalsVO){
        int resultCnt=0;

        //만약 resultCnt가 2라면 잘 실행된것.! (사용신청, 반납신청 모두 resultCnt가 2라면 잘 실행된거임.)
        resultCnt = as.clickApprovalsBtn(approvalsVO);
        return resultCnt;
    }//end clickApprovals

}//end class
