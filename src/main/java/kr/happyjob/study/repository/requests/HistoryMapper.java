package kr.happyjob.study.repository.requests;

import kr.happyjob.study.vo.requests.HistoryModel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface HistoryMapper {

    Set<String> subCategory(@RequestParam Map<String, Object> paramMap);

    public List<HistoryModel> historyList(@RequestParam Map<String, Object> paramMap);

    public List<String> statusCodeList();

    int historyCnt(@RequestParam Map<String, Object> paramMap);

    HistoryModel detailHistory(@RequestParam Map<String, Object> paramMap);

    int deleteHistory(@RequestParam Map<String, Object> paramMap);

}
