package kr.happyjob.study.repository.requests;

import kr.happyjob.study.vo.requests.ApplyModel;
import java.util.List;
import java.util.Map;

public interface ApplyMapper {

    List<ApplyModel> applyList(Map<String, Object> paramMap) throws Exception;

    int applyCnt(Map<String, Object> paramMap) throws Exception;

    ApplyModel applyDetail(Map<String, Object> paramMap) throws Exception;

    int applyReg(Map<String, Object> paramMap) throws Exception;

    int applyReturn(Map<String, Object> paramMap) throws Exception;

    int applyCancel(Map<String, Object> paramMap) throws Exception;

    List<Map<String, Object>> getCategories() throws Exception;

    // tb_use_history 삽입 메소드 추가
    int insertUseHistory(Map<String, Object> paramMap) throws Exception;
}