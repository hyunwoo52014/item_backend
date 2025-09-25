package kr.happyjob.study.repository.system;

import kr.happyjob.study.vo.system.AccountsModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountsMapper {
    public List<AccountsModel> accountsList(Map<String, Object> paramMap);
    public int accountsCount(Map<String, Object> paramMap);
    public AccountsModel accountDetail(Map<String, Object> paramMap);
    public List<AccountsModel> accountEquip(Map<String, Object> paramMap);
    public int accountEquipCount(Map<String, Object> paramMap);
    public int accountDelete(Map<String, Object> paramMap);
    public int accountRestore(Map<String, Object> paramMap);
}
