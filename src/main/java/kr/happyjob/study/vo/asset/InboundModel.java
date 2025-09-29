package kr.happyjob.study.vo.asset;

public class InboundModel {

    private int importNumber;
    private String productName;
    private String vendorName;
    private String importDate;
    private int importQuantity;

    public int getImportNumber() {
        return importNumber;
    }

    public void setImportNumber(int importNumber) {
        this.importNumber = importNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public int getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }
}
