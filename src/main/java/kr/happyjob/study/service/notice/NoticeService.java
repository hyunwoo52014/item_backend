package kr.happyjob.study.service.notice;

import kr.happyjob.study.repository.notice.NoticeMapper;
import kr.happyjob.study.repository.usermgr.UsermgrMapper;
import kr.happyjob.study.vo.notice.NoticeVO;
import kr.happyjob.study.vo.usermgr.UserModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    // Set logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get class name for logger
    private final String className = this.getClass().toString();

    @Autowired
    private NoticeMapper noticeMapper;
    // 전체 리스트
    public List<NoticeVO> noticeList(Map<String, Object> paramMap) throws Exception {
        System.out.println("--------------------------------------NoticeService---------------------------");
        return noticeMapper.noticeList(paramMap);
    }
    // 개수
    public int noticeCnt(Map<String, Object> paramMap) throws Exception {
        return noticeMapper.noticeCnt(paramMap);
    }

    // 추가
    public int insertNotice(Map<String, Object> paramMap) throws Exception {
        return noticeMapper.insertNotice(paramMap);
    }

    //삭제
    public int deleteNotice(Map<String, Object> paramMap) throws Exception {
        return noticeMapper.deleteNotice(paramMap);
    }

    // 수정
    public int updateNotice(Map<String, Object> paramMap) throws Exception {
        return noticeMapper.updateNotice(paramMap);
    }

    // 상세
    public NoticeVO noticeDetail(Map<String, Object> paramMap) throws Exception {
        return noticeMapper.noticeDetail(paramMap);
    }

}
