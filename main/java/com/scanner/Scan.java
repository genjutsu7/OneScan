package com.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.dao.ScanDAO;
import com.model.Analysis;
import com.model.User;

import okhttp3.Response;

@WebServlet("/Scan")
@MultipartConfig(
		maxFileSize = 1024 * 1024 * 34
)
public class Scan extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  String uploadDir;
    
	@Override
    public void init() {
        uploadDir = getServletContext().getRealPath("/") + "uploads";
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
	try {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		User user = (User)session.getAttribute("user");
		String uploadedBy = user.getName();	
		File file = processFileUpload(request, uploadedBy);
		String getFileName = file.getName();
		String hash = ScanUtils.buildHash(file);
		
	if(!ScanDAO.fetchResult(hash)) {
			System.out.println("Didn't find the data now generating report");
			generateReport(getFileName, uploadedBy, hash, file);
			System.out.println("Report generation done now redirecting");
			response.sendRedirect("analysis.jsp?hash=" + hash);
		}
	else {
		ScanDAO.copyScan(hash, uploadedBy, getFileName);
		response.sendRedirect("analysis.jsp?hash=" + hash);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private void generateReport(String fileName, String uploadedBy, String hash, File file) throws IOException {
	while(true) {
		try {
			Response reportResponse = ScanUtils.getReport(hash);			
			if (!reportResponse.isSuccessful()) {	
				System.out.println("Uploading File");
				ScanUtils.uploadFile(file); 
			}
			System.out.println("File Upload done now awaitng for analysis");
			String responseString = reportResponse.body().string();
			JSONObject json = new JSONObject(responseString);
			if (!json.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_results").isEmpty()) {
				List<Analysis> analysisList = ScanUtils.parser(responseString);
                String analysis = (ScanUtils.counter(analysisList) > 0) ? "malicious" : "safe";
    			System.out.println("Now going to insert the scan");
    			ScanDAO.insertScan(fileName, uploadedBy, hash, analysis, responseString);
    			deleteFile(file);
                break;
			}
			Thread.sleep(30000); 
			System.out.println("Sleeping for 30 secs till analysis");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

	private File processFileUpload(HttpServletRequest request, String uploadedBy) throws IOException, ServletException {
		String fileName = null;
		for(Part part: request.getParts()) {	
			fileName = part.getSubmittedFileName();
			part.write(uploadDir+File.separator+fileName);
		}
		return new File(uploadDir + File.separator + fileName);
	}
	private void deleteFile(File file) {
	    if(file.delete()) {
	        System.out.println("File deleted successfully");
	    } else {
	        System.out.println("Failed to delete the file");
	    }
	}

}
