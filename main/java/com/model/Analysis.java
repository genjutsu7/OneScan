package com.model;

public class Analysis {
    private String engineName;
    private String category;
    private String result;
    private SignatureInfo signatureInfo;

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SignatureInfo getSignatureInfo() {
        return signatureInfo;
    }

    public void setSignatureInfo(SignatureInfo signatureInfo) {
        this.signatureInfo = signatureInfo;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
