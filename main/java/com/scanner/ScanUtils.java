package com.scanner;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.model.Analysis;
import com.model.SignatureInfo;
import okhttp3.*;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ScanUtils {
    public static final String fileURL = "https://www.virustotal.com/api/v3/files";
    public static final String getReportURL = "https://www.virustotal.com/api/v3/files/%s";
    protected final static String apiKey = System.getenv("API");
 
    static OkHttpClient client = new OkHttpClient();

    @SuppressWarnings("deprecation")
	public static void uploadFile(File file) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .url(fileURL)
                .header("x-apikey", apiKey)
                .header("accept", "application/json")
                .post(requestBody)
                .build();
        client.newCall(request).execute();
    }

    public static Response getReport(String hash) throws IOException, InterruptedException{
        String reportURL = String.format(getReportURL,hash);
        Request request = new Request.Builder()
                .url(reportURL)
                .get()
                .header("x-apikey",apiKey)
                .header("accept","application/json")
                .build();
        return client.newCall(request).execute();
    }

    public static String buildHash(File file) throws IOException{
        byte[]fileBytes = Files.toByteArray(file);
        return Hashing.sha256()
                .hashBytes(fileBytes)
                .toString();
    }

    public static String buildHash(String pass){
        return Hashing.sha256()
                .hashString(pass, StandardCharsets.UTF_8)
                .toString();
    }

    public static List<Analysis> parser(String json){
        JSONObject jsonObject = new JSONObject(json);
        JSONObject attributes = jsonObject.getJSONObject("data").getJSONObject("attributes");
        JSONObject lastAnalysis = attributes.getJSONObject("last_analysis_results");
        List<Analysis> analysisList = new ArrayList<>();
        SignatureInfo signature = new SignatureInfo();
        String type = attributes.getString("type_description");
       
       
        if(type.contains("EXE")) {
            JSONObject sigInfo = attributes.getJSONObject("signature_info");
            signature.setMagic(attributes.getString("magic"));
            signature.setComments(sigInfo.optString("comments"));
            signature.setDescription(sigInfo.getString("description"));
            signature.setOriginalName(sigInfo.getString("original name"));
            signature.setFileVersion(sigInfo.getString("file version"));
            signature.setType(type);
        }
        else {
            signature.setType(type);
        }
        for(String key: lastAnalysis.keySet()){
            Analysis analysis = new Analysis();
            analysis.setCategory(lastAnalysis.getJSONObject(key).getString("category"));
            analysis.setEngineName(lastAnalysis.getJSONObject(key).getString("engine_name"));
            analysis.setResult(lastAnalysis.getJSONObject(key).optString("result","undetected"));
            analysis.setSignatureInfo(signature);
            analysisList.add(analysis);
        }
        return analysisList;
    }
    public static int counter(List<Analysis> analysisList){
        int count = 0;
        for(Analysis an: analysisList){
            if(an.getCategory().equals("malicious")){
                count++;
            }
        }
        return count;
    }
  
}
