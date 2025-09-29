package kr.happyjob.study.service.approvals;

import kr.happyjob.study.repository.approvals.ApprovalsMapper;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        //여기서 id를 집어넣어야해..........

        for(int i=0; i<list.toArray().length; i++){
            status=list.get(i).getProduct_state();
            if(status.equals("O")){
                list.get(i).setProduct_state_str("사용신청");
            }else if(status.equals("R")){
                list.get(i).setProduct_state_str("반납신청");
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
        int rc1=0, rc2=0;
        int resultCnt = 0;
        logger.info("serviceImpl-----------------------"+approvalsVO);

        if(approvalsVO.getProduct_state().equals("O")){
            /* 사용 요청이었을 경우 */
            //승인, 거절
            if(approvalsVO.getApprove().equals("Y")){ //승인
                rc1 = am.updateProductDetailOnApprove(approvalsVO);
                rc2 = am.updateUseHistoryOnApprove(approvalsVO);
                //근데 지금은 없을 수 있으니까, 없으면 내가 insert 해주자.
                if(rc2 == 0){
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnApprove(approvalsVO);
                }//end if
                resultCnt = rc1+rc2;

            }else if(approvalsVO.getApprove().equals("N")){ //거절
                rc1 = am.updateProductDetailOnReject(approvalsVO);
                rc2 = am.updateUseHistoryOnReject(approvalsVO);

                //근데 지금은 없을 수 있으니까, 없으면 내가 insert 해주자.
                if(rc2 == 0){
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnApprove(approvalsVO);
                }//end if

            }//end if~else


        }else if(approvalsVO.getProduct_state().equals("R")){
            /* 반납 요청이었을 경우  */
            //승인, 거절
            if(approvalsVO.getApprove().equals("Y")){ //승인
                rc1 = am.updateProductDetailOnReturn(approvalsVO);
                rc2 = am.updateUseHistoryOnReturn(approvalsVO);
                if(rc2 == 0){
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnReject(approvalsVO);
                }//end if
                logger.info("rc1 "+rc1+", "+"rc2 "+rc2);
            }else if(approvalsVO.getApprove().equals("N")){ // 거절
                rc1 = am.updateProductDetailOnReturnReject(approvalsVO); // 이미 approve는 n이야....
                rc2 = am.updateUseHistoryOnReturnReject(approvalsVO);

                if(rc2 == 0){
                    logger.info("반납 거절===>"+approvalsVO);
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnReject(approvalsVO);
                }//end if
                logger.info("rc1 "+rc1+", "+"rc2 "+rc2);
            }
            //만약 resultCnt가 2라면 잘 실행된것.!
        }//end else if
        resultCnt = rc1+rc2;
        logger.info("resultCnt========"+resultCnt);

        return resultCnt;
    }//clickApprovalsBtn


}//end class
