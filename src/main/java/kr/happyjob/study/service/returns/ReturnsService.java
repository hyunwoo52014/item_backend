package kr.happyjob.study.service.returns;

import kr.happyjob.study.repository.returns.ReturnsMapper;
import kr.happyjob.study.vo.returns.ReturnsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReturnsService {

    @Autowired
    ReturnsMapper returnsMapper;

    // 현재 페이지 데이터 조회
    public List<ReturnsModel> returnsList(Map<String, Object> paramMap) throws Exception{
        return returnsMapper.returnsList(paramMap);
    }

    // 전체 데이터 수 조회 (totalCount용)
    public int getTotalCount(Map<String, Object> paramMap) throws Exception {
        return returnsMapper.returnsCnt(paramMap); // 기존 returnsCnt 쿼리 그대로 사용
    }

    // 기존 returnsCnt 메서드 유지 (호환용)
    public int returnsCnt(Map<String, Object> paramMap) throws Exception{
        return returnsMapper.returnsCnt(paramMap);
    }

    // 전체 반납 처리
    public int returnAll(Map<String, Object> paramMap) throws Exception {
        return returnsMapper.returnAll(paramMap);
    }

    // 장비 반납 신청 취소 (추가)
    public int cancelReturn(Map<String, Object> paramMap) throws Exception {
        return returnsMapper.cancelReturn(paramMap);
    }
}