package kr.happyjob.study.service.requests;
import kr.happyjob.study.repository.requests.ApplyMapper;
import kr.happyjob.study.vo.requests.ApplyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int applyReg(Map<String, Object> paramMap) throws Exception {
        return applyMapper.applyReg(paramMap);
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
