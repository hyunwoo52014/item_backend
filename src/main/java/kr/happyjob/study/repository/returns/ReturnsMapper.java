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

    /**
     * 장비 개별 반납 신청
     */
    public int returnOne(Map<String, Object> paramMap) throws Exception;


    /**
     * 장비 반납 신청 취소 (추가)
     */
    public int cancelReturn(Map<String, Object> paramMap) throws Exception;


    /**
     * 모달 상세 조회
     */
    public ReturnsModel selectProductStateDetail(Map<String, Object> paramMap) throws Exception;
}
