package kr.happyjob.study.repository.approvals;

import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ApprovalsMapper {

    /* (관리자) 신청/반납 > 결재 : 전체 목록 뿌리기 */
    public List<ApprovalsVO> getApprovalsList();
    /* (관리자) 신청/반납 > 결재 : Pagination을 위한 총 column 수 반환 */
    public int getTotalApprovalCnt();

    /* (관리자) 사용요청 승인 */
    public int updateProductDetailOnApprove(ApprovalsVO approvalsVO);
    public int insertUseHistoryOnApprove(ApprovalsVO approvalsVO);

    /**
     * (관리자) tb_use_history DB의 usage값 가져오기
     * @param approvalsVO category_code, product_detail_code를 이용
     * @return 총 몇 행이 있는지 반환
     */
    public int getUsageCodeCount(ApprovalsVO approvalsVO);


    /* (관리자) 반납요청 승인 */
    public int updateProductDetailOnReturn(ApprovalsVO approvalsVO);
    public int updateUseHistoryOnReturn(ApprovalsVO approvalsVO);

}//interface
