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

    /**
     * 사용신청 - tb_product_detail 업데이트 + tb_use_history 삽입
     * 트랜잭션으로 묶어서 처리 (둘 중 하나라도 실패하면 전체 롤백)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int applyReg(Map<String, Object> paramMap) throws Exception {
        // 1. tb_product_detail 상태 업데이트 (product_state = 'O')
        int updateResult = applyMapper.applyReg(paramMap);

        if (updateResult > 0) {
            // 2. tb_use_history에 이력 삽입
            int insertResult = applyMapper.insertUseHistory(paramMap);

            if (insertResult <= 0) {
                throw new Exception("사용이력 등록에 실패했습니다.");
            }
        }

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