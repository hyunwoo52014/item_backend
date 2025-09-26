package kr.happyjob.study.repository.notice;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
import kr.happyjob.study.vo.notice.NoticeVO;

@Mapper
public interface NoticeMapper {
    List<NoticeVO> noticeList(Map<String, Object> paramMap) throws Exception;
    int noticeCnt(Map<String, Object> paramMap) throws Exception;
    NoticeVO noticeDetail(Map<String, Object> paramMap) throws Exception;
    int insertNotice(Map<String, Object> paramMap) throws Exception;
    int updateNotice(Map<String, Object> paramMap) throws Exception;
    int deleteNotice(Map<String, Object> paramMap) throws Exception;
}
