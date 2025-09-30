package kr.happyjob.study.repository.approvals;

import kr.happyjob.study.vo.approvals.ApprovalSearchVO;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ApprovalsMapper {

    /* (관리자) 신청/반납 > 결재 : 전체 목록 뿌리기 */
    public List<ApprovalsVO> getApprovalsList();
    /* (관리자) 신청/반납 > 결재 : Pagination을 위한 총 column 수 반환 */
    public int getTotalApprovalCnt();

    /* (관리자) 사용요청 [승인] */
    public int updateProductDetailOnApprove(ApprovalsVO approvalsVO);
    public int updateUseHistoryOnApprove(ApprovalsVO approvalsVO);


    /* (관리자) 사용요청 [거절] */
    public int updateProductDetailOnReject(ApprovalsVO approvalsVO);
    public int updateUseHistoryOnReject(ApprovalsVO approvalsVO);


    /**
     * (관리자) tb_use_history DB의 usage값 가져오기
     * @param approvalsVO category_code, product_detail_code를 이용
     * @return 총 몇 행이 있는지 반환
     */
    public int getUsageCodeCount(ApprovalsVO approvalsVO);


    /* (관리자) 반납요청 [승인] */
    public int updateProductDetailOnReturn(ApprovalsVO approvalsVO);
    public int updateUseHistoryOnReturn(ApprovalsVO approvalsVO);
    
    
    /* (관리자) 반납요청 [거절]*/
    public int updateProductDetailOnReturnReject(ApprovalsVO approvalsVO);
    public int updateUseHistoryOnReturnReject(ApprovalsVO approvalsVO);

    /* DB 가데이터가 이상해서 필요함....  */
    //만약에 history에 없으면.... 만듭시다...
    public int insertUseHistoryOnApprove(ApprovalsVO approvalsVO);
    public int insertUseHistoryOnRejectYes(ApprovalsVO approvalsVO);
    public int insertUseHistoryOnRejectNo(ApprovalsVO approvalsVO);
    
    
    /* 검색 */
    public List<ApprovalsVO> selectSearchList(ApprovalSearchVO aSearchVO);
    
}//interface
