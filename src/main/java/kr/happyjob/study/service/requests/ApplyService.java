package kr.happyjob.study.service.requests;

import kr.happyjob.study.vo.requests.ApplyModel;
import java.util.List;
import java.util.Map;

public interface ApplyService {

    List<ApplyModel> applyList(Map<String, Object> paramMap) throws Exception;

    int applyCnt(Map<String,Object> paramMap) throws Exception;

    ApplyModel applyDetail(Map<String, Object> paramMap) throws Exception;

    int applyReg(Map<String, Object> paramMap) throws Exception;

    int applyReturn(Map<String, Object> paramMap) throws Exception;

    int applyCancel(Map<String, Object> paramMap) throws Exception;
}

