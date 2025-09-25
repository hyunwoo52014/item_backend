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

    public List<ReturnsModel> returnsList(Map<String, Object> paramMap) throws Exception{
        return returnsMapper.returnsList(paramMap);
    }

    public int returnsCnt(Map<String, Object> paramMap) throws Exception{
        return returnsMapper.returnsCnt(paramMap);
    }

    public int returnAll(Map<String, Object> paramMap) throws Exception {
        return returnsMapper.returnAll(paramMap);
    }
}