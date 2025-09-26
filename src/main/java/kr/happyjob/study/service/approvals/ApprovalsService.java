package kr.happyjob.study.service.approvals;


import kr.happyjob.study.vo.approvals.ApprovalsVO;

import java.util.List;
import java.util.Map;

public interface ApprovalsService {

    /* (관리자) 신청/반납 > 결제 : 전체 목록 뿌리기 */
    public List<ApprovalsVO> showApprovalsList();

    /* (관리자) 신청/반납 > 결제 : Pagination을 위한 총 column 수 반환 */
    public int totalCnt();

    /* (관리자) 승인 버튼을 눌렀을 때 실행 */
    public int clickApprovalsBtn(ApprovalsVO approvalsVO);
}//end interface
