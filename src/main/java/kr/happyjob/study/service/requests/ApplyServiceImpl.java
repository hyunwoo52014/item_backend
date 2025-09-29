package kr.happyjob.study.service.requests;

import kr.happyjob.study.repository.requests.ApplyMapper;
import kr.happyjob.study.vo.requests.ApplyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public List<ApplyModel> applyList(Map<String, Object> paramMap) throws Exception {
        return applyMapper.applyList(paramMap);
    }

    @Override
    public int applyCnt(Map<String, Object> paramMap) throws Exception {
        return applyMapper.applyCnt(paramMap);
    }

    @Override
    public ApplyModel applyDetail(Map<String, Object> paramMap) throws Exception {
        return applyMapper.applyDetail(paramMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int applyReg(Map<String, Object> paramMap) throws Exception {
        System.out.println("=== ApplyServiceImpl.applyReg 시작 ===");
        System.out.println("전달받은 파라미터: " + paramMap);

        // 1. tb_product_detail 상태 업데이트
        int updateResult = applyMapper.applyReg(paramMap);
        System.out.println("tb_product_detail 업데이트 결과: " + updateResult);

        if (updateResult > 0) {
            // 2. tb_use_history에 이력 삽입
            System.out.println("tb_use_history 삽입 시도 중...");
            System.out.println("삽입할 데이터: product_detail_code=" + paramMap.get("product_detail_code")
                    + ", loginID=" + paramMap.get("loginID"));

            int insertResult = applyMapper.insertUseHistory(paramMap);
            System.out.println("tb_use_history 삽입 결과: " + insertResult);

            if (insertResult <= 0) {
                System.out.println("ERROR: tb_use_history 삽입 실패!");
                throw new Exception("사용이력 등록에 실패했습니다.");
            }
            System.out.println("tb_use_history 삽입 성공!");
        }

        System.out.println("=== ApplyServiceImpl.applyReg 종료 ===");
        return updateResult;
    }

    @Override
    public int applyReturn(Map<String, Object> paramMap) throws Exception {
        return applyMapper.applyReturn(paramMap);
    }

    @Override
    public int applyCancel(Map<String, Object> paramMap) throws Exception {
        return applyMapper.applyCancel(paramMap);
    }

    @Override
    public List<Map<String, Object>> getCategories() throws Exception {
        return applyMapper.getCategories();
    }
}