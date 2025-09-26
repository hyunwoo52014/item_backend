package kr.happyjob.study.repository.returns;

import kr.happyjob.study.vo.returns.ReturnsModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReturnsMapper {
    /**
     * 반납 목록 조회
     */
    public List<ReturnsModel> returnsList(Map<String, Object> paramMap) throws Exception;

    /**
     * 반납 목록 총 갯수 조회
     */
    public int returnsCnt(Map<String, Object> paramMap) throws Exception;

    /**
     * 장비 일괄 반납 신청
     */
    public int returnAll(Map<String, Object> paramMap) throws Exception;

}
