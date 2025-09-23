package kr.happyjob.study.repository.usermgr;

import kr.happyjob.study.vo.usermgr.UserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsermgrreactMapper {

    // 리스트 조회
    public List<UserModel> userListreact(Map<String, Object> paramMap);

    // 카운트 조회
    public int userListreactcnt(Map<String, Object> paramMap);

    public UserModel selectuser(Map<String, Object> paramMap);

    public int insertuser(Map<String, Object> paramMap);

    public int updateuser(Map<String, Object> paramMap);

    public int deleteuser(Map<String, Object> paramMap);

    public int dupcheck(Map<String, Object> paramMap);

}