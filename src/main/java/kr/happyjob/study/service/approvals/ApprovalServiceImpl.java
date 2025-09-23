package kr.happyjob.study.service.approvals;

import kr.happyjob.study.repository.approvals.ApprovalsMapper;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<ApprovalsVO> list=new ArrayList<ApprovalsVO>();
        list = am.getApprovalsList();
        logger.info("ApprocalServiceImpl------------------"+list.toString());
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



}//end class
