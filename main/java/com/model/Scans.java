package com.model;

import java.sql.Timestamp;

public class Scans {
	private String fileName;
	private String uploadedBy;
	private String sha256;
	private String analysis;
    private Timestamp scanDate;
	
	public Scans(String fileName, String uploadedBy, String sha256, String analysis, Timestamp scanDate) {
        this.fileName = fileName;
        this.uploadedBy = uploadedBy;
        this.sha256 = sha256;
        this.analysis = analysis;
        this.scanDate = scanDate;
	}
	public String getFilename() {
		return fileName;
	}
	public void setFilename(String filename) {
		this.fileName = filename;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getSha256() {
		return sha256;
	}
	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public Timestamp getScanDate() {
		return scanDate;
	}
	public void setScanDate(Timestamp scanDate) {
		this.scanDate = scanDate;
	}
}
