package kr.happyjob.study.service.system;

import kr.happyjob.study.repository.system.AccountsMapper;
import kr.happyjob.study.vo.system.AccountsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountsService {

    @Autowired
    private AccountsMapper accountsMapper;
    public List<AccountsModel> accountsList (Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountsList(paramMap);
    }

    public int accountsCount(Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountsCount(paramMap);
    }

    public AccountsModel accountDetail (Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountDetail(paramMap);
    }
    public List<AccountsModel> accountEquip (Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountEquip(paramMap);
    }
    public int accountEquipCount(Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountEquipCount(paramMap);
    }
    public int accountDelete(Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountDelete(paramMap);
    }
    public int accountRestore(Map<String, Object> paramMap) throws Exception {
        return accountsMapper.accountRestore(paramMap);
    }
}
