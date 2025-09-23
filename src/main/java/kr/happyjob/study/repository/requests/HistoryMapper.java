package kr.happyjob.study.repository.requests;

import kr.happyjob.study.vo.requests.HistoryModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface HistoryMapper {

    List<HistoryModel> historyList(@RequestParam Map<String, Object> paramMap);

    int historyCnt(@RequestParam Map<String, Object> paramMap);

}
