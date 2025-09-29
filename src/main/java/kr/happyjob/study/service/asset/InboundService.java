package kr.happyjob.study.service.asset;

import kr.happyjob.study.repository.asset.InboundMapper;
import kr.happyjob.study.vo.asset.InboundDetailModel;
import kr.happyjob.study.vo.asset.InboundModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InboundService{

    @Autowired
    InboundMapper inboundMapper;


    public List<InboundModel> searchImport(Map<String, Object> paramMap) throws Exception {
        return inboundMapper.searchImport(paramMap);
    }


    public int countImportList(Map<String, Object> paramMap) throws Exception {
        return inboundMapper.countImportList(paramMap);
    }


    public InboundDetailModel searchImportDetail(Map<String, Object> paramMap) throws Exception {

        InboundDetailModel inboundDetail = inboundMapper.searchImportDetail(paramMap);

        inboundDetail.setTotalPrice(inboundDetail.getQuantity() * inboundDetail.getPrice());

        return inboundDetail;
    }


    public int registerImportDetail(Map<String, Object> paramMap) throws Exception {

        List<String> vendors = inboundMapper.searchVendors("vendor");
        boolean isExistVendor = false;
        for(String vendor : vendors) {
            if(vendor.equals(paramMap.get("vendor_name"))) {
                isExistVendor = true;
            }
        }

        if(!isExistVendor) {
            int checkInsertNewVendor = inboundMapper.insertNewVendor(paramMap);
            if(checkInsertNewVendor > 0) {
                if(insertProductInfo(paramMap) > 0 ) {
                    return 1;
                }
            }
        }

        if(insertProductInfo(paramMap) > 0 ) {
            return 1;
        }

        return 0;
    }

    private int insertProductInfo(Map<String, Object> paramMap) {
        String newProductNo = inboundMapper.getNextProductNo();
        paramMap.put("newProductNo", newProductNo);

        int checkInsertProductInfo = inboundMapper.insertProductInfo(paramMap);

        if(checkInsertProductInfo > 0) {
            if(insertProductImport(paramMap) > 0) {
                return 1;
            };
        }

        return 0;
    }

    private int insertProductImport(Map<String, Object> paramMap) {

        int quantity = Integer.parseInt((String)paramMap.get("import_quantity"));

        for(int i = 0; i < quantity; i++) {
            inboundMapper.insertProductDetail(paramMap);
        }

        return inboundMapper.insertProductImport(paramMap);
    }

}