package kr.happyjob.study.controller.approvals;

import kr.happyjob.study.service.approvals.ApprovalServiceImpl;
import kr.happyjob.study.vo.approvals.ApprovalResponseVO;
import kr.happyjob.study.vo.approvals.ApprovalSearchVO;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    }//end showAllApprovalsList

    /* 전체 신청 목록 갯수 출력 */
    @RequestMapping("/approvals/getTotalCnt")
    public int getTotalListCnt(){
        return as.totalCnt();
    }//end getTotalListCnt


    /* [사용요청], [반납요청] 버튼을 눌렀을 때 실행 */
    @RequestMapping("/approvals/clickApprovals")
    public int clickApprovals(@RequestBody ApprovalsVO approvalsVO){
        int resultCnt;
        //만약 resultCnt가 2라면 잘 실행된것.! (사용신청, 반납신청 모두 resultCnt가 2라면 잘 실행된거임.)
        resultCnt = as.clickApprovalsBtn(approvalsVO);
        return resultCnt;
    }//end clickApprovals


    /* 검색 버튼을 눌렀을 때 */
    //front 쪽에서 검색하는걸로 기능 변경 > 따라서 사용 안하는 method
    @RequestMapping("/approvals/search")
    public ApprovalResponseVO<List<ApprovalsVO>> clickSearchBtn(@RequestBody ApprovalSearchVO aSearchVO){
        return as.clickSearchBtn(aSearchVO);
    }//end clickSearchBtn
}//end class
