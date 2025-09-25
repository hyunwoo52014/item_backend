package kr.happyjob.study.repository.approvals;

import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalsMapper {

    /* (관리자) 신청/반납 > 결제 : 전체 목록 뿌리기 */
    public List<ApprovalsVO> getApprovalsList();
    /* (관리자) 신청/반납 > 결제 : Pagination을 위한 총 column 수 반환 */
    public int getTotalApprovalCnt();

    /* 장비코드 + 이름 + 신청날짜 => 신청사유(String 반환) */
    public String getWrittenReaSon(ApprovalsVO approvalsVO);
}//interface
