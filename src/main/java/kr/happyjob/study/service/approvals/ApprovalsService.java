package kr.happyjob.study.service.approvals;


import kr.happyjob.study.vo.approvals.ApprovalsVO;

import java.util.List;
import java.util.Map;

public interface ApprovalsService {

    /* (관리자) 신청/반납 > 결제 : 전체 목록 뿌리기 */
    public List<ApprovalsVO> showApprovalsList();

    /* (관리자) 신청/반납 > 결제 : Pagination을 위한 총 column 수 반환 */
    public int totalCnt();

    /* 장비코드 + 이름 + 신청날짜 => 신청사유(String 반환) */
    public String getWrittenReason(ApprovalsVO oneRowData);
}//end interface
