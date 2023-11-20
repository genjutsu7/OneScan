package com.model;

public class SignatureInfo {
    private String fileVersion;
    private String originalName;
    private String magic;
    private String comments;
    private String description;
    private String type;

    public String getMagic() {
        return magic;
    }
    public void setMagic(String magic) {
        this.magic = magic;
    }
    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
}
