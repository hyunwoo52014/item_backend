package kr.happyjob.study.vo.system;

public class RemainDataModel {
    private int remainDataNum;
    private String productName;
    private String categoryCode;
    private String vendorName;
    private int quantity;

    public int getRemainDataNum() {
        return remainDataNum;
    }

    public void setRemainDataNum(int remainDataNum) {
        this.remainDataNum = remainDataNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
