package kr.happyjob.study.service.approvals;

import kr.happyjob.study.repository.approvals.ApprovalsMapper;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalServiceImpl implements ApprovalsService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApprovalsMapper am;


    /**
     * (관리자) 신청/반납 > 결제 : 사용신청 혹은 반납 신청 중인 것들을 모두 뿌려준다.
     * @return ApprvalsVO
     */
    public List<ApprovalsVO> showApprovalsList(){
        List<ApprovalsVO> list=am.getApprovalsList();
        String status=null;

        for(int i=0; i<list.toArray().length; i++){
            status=list.get(i).getProduct_state();
            if(status.equals("O")){
                list.get(i).setProduct_state("사용신청");
            }else if(status.equals("R")){
                list.get(i).setProduct_state("반납신청");
            }//end if~else
        }//end for

        return list;
    }//end showApprovalsList


    /**
     * (관리자) 신청/반납 > 결제 : Pagination을 위한 총 column 수 반환
     * @return int column 갯수 반환 (목록 갯수 반환)
     */
    public int totalCnt(){
        int cnt=0;
        cnt = am.getTotalApprovalCnt();
        return cnt;
    }// end totalCnt

    /**
     * 승인 버튼을 눌렀을 때 실행 <br/>
     * @return 성공 실행한 쿼리문 cnt
     */
    @Transactional
    public int clickApprovalsBtn(ApprovalsVO approvalsVO){
        int resultCnt=0;

        if(approvalsVO.getProduct_state().equals("O")){
            //사용 요청이었을 경우
            resultCnt+=am.updateProductDetailOnApprove(approvalsVO);
            approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1);
            resultCnt+=am.insertUseHistoryOnApprove(approvalsVO);

            //만약 resultCnt가 2라면 잘 실행된거지.

        }else if(approvalsVO.getProduct_state().equals("R")){
            //반납 요청이었을 경우
            resultCnt+=am.updateProductDetailOnReturn(approvalsVO);
            resultCnt+=am.updateUseHistoryOnReturn(approvalsVO);

            //만약 resultCnt가 2라면 잘 실행된것.!

        }//end else if

        return resultCnt;
    }//clickApprovalsBtn

    //승인 버튼을 누르면, 승인이 완료되었습니다. 팝업 ( 초록색)
    //거절 버튼을 누르면, 거절되었습니다. 팝업 (빨간색)



}//end class
