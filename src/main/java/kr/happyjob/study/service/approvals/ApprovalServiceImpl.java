package kr.happyjob.study.service.approvals;

import kr.happyjob.study.repository.approvals.ApprovalsMapper;
import kr.happyjob.study.vo.approvals.ApprovalResponseVO;
import kr.happyjob.study.vo.approvals.ApprovalSearchVO;
import kr.happyjob.study.vo.approvals.ApprovalsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;

import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalsService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApprovalsMapper am;


    /**
     * (관리자) 신청/반납 > 결제 : 사용신청, 반납 신청 중인 것들을 모두 뿌려준다.
     * @return ApprovalsVO
     */
    public List<ApprovalsVO> showApprovalsList(){
        List<ApprovalsVO> list=am.getApprovalsList();
        String status;
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
        return am.getTotalApprovalCnt();
    }// end totalCnt



    /**
     * 사용 요청(승인, 거절), 반납 요청(승인, 거절) <br/>
     * @return 성공 실행한 쿼리문 cnt
     */
    @Transactional
    public int clickApprovalsBtn(ApprovalsVO approvalsVO){
        int rc1=0, rc2=0;
        int resultCnt;

        if(approvalsVO.getProduct_state().equals("O")){
            /* 사용 요청이었을 경우 */

            if(approvalsVO.getApprove().equals("Y")){
                //승인
                rc1 = am.updateProductDetailOnApprove(approvalsVO);
                rc2 = am.updateUseHistoryOnApprove(approvalsVO);
                //근데 지금은 없을 수 있으니까, 없으면 내가 insert 해주자.
                if(rc2 == 0){
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnApprove(approvalsVO);
                }//end if

            }else if(approvalsVO.getApprove().equals("N")){
                //거절
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

            if(approvalsVO.getApprove().equals("Y")){
                //승인
                rc1 = am.updateProductDetailOnReturn(approvalsVO);
                rc2 = am.updateUseHistoryOnReturn(approvalsVO);
                if(rc2 == 0){
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnRejectYes(approvalsVO);
                }//end if
            }else if(approvalsVO.getApprove().equals("N")){
                // 거절
                rc1 = am.updateProductDetailOnReturnReject(approvalsVO); // 이미 approve는 n이야....
                rc2 = am.updateUseHistoryOnReturnReject(approvalsVO);

                if(rc2 == 0){
                    approvalsVO.setUsage_code(am.getUsageCodeCount(approvalsVO)+1); //이건 insert일 때 필요하지
                    rc2 = am.insertUseHistoryOnRejectNo(approvalsVO);
                }//end if
            }
            //만약 resultCnt가 2라면 잘 실행된것.!
        }//end else if
        resultCnt = rc1+rc2;

        return resultCnt;
    }//clickApprovalsBtn

    /* 검색 버튼을 눌렀을 때 */
    public ApprovalResponseVO<List<ApprovalsVO>> clickSearchBtn(ApprovalSearchVO aSearchVO){
        ApprovalResponseVO<List<ApprovalsVO>> list;
        //enhanced switch 방식
        list= switch (aSearchVO.getSearchStr()){
            case "all","itCode","name", "requestDate" -> searchList(aSearchVO);
            default -> throw new IllegalArgumentException("잘못된 검색 조건" + aSearchVO.getSearchStr());
        };

        return list;
    }//end clickSearchBtn


    /* 검색 기능 */
    private ApprovalResponseVO<List<ApprovalsVO>> searchList(ApprovalSearchVO aSearchVO){

        if(aSearchVO.getSearchStr().equals("itCode")){
            processSearchItCode(aSearchVO);
            if(aSearchVO.getSearchStr().equals("wrong")){
                return ApprovalResponseVO.error(aSearchVO.getSearchWordStr());
            }//end if
        }//end if
        List<ApprovalsVO> list = am.selectSearchList(aSearchVO);
        return ApprovalResponseVO.success(list);
    }//end searchList


    private void processSearchItCode(ApprovalSearchVO aSearchVO){
        String[] tmpStr;
        int product_detail_code;

        //만약 '-'가 2개 이상이거나, '-'이 아예 없거나, 영문/숫자/- 외의 문자가 있을 경우
        //if(aSearchVO.getSearchWordStr().matches(".*-.*-.*") || !aSearchVO.getSearchWordStr().contains("-") || aSearchVO.getSearchWordStr().matches(".*[^a-zA-Z0-9-].*")){
        if(!aSearchVO.getSearchWordStr().matches("^[A-Za-z]+-[0-9]+$")){
            aSearchVO.setSearchStr("wrong");
            aSearchVO.setSearchWordStr("잘못된 검색어입니다.\n다시 입력해주세요.\nex)MN-23 과 같은 형태로 검색해주세요.");
            return;
        }//end if

        if(aSearchVO.getSearchWordStr().contains("-")){
            tmpStr=aSearchVO.getSearchWordStr().split("-");

            //숫자일 경우에는 product_detail_code에 넣어주고, 문자일 경우에는 category_code에 넣어준다.
            for (String str : tmpStr) {
                try {
                    product_detail_code = NumberUtils.parseNumber(str, Integer.class);
                    aSearchVO.setProduct_detail_code(product_detail_code);
                } catch (IllegalArgumentException e) {
                    aSearchVO.setCategory_code(str);
                }//try~catch
            }//end for
        }//end if
    }// end processSearchItCode





}//end class
