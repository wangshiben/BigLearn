package com.Service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LearnService {
    @Autowired
    @Qualifier("HeadMapPut")
    private Map<String, String> headers;
    @Autowired
    @Qualifier("TokenMap")
    private Map<String ,String>tokenMap;

    public String BigLeaningByTokens(String token) throws IOException {
        headers.put("token",token);
        StringBuilder sb=new StringBuilder();

        Document post1 = Jsoup.connect("https://dxx.scyol.com/api/student/studyHostory").headers(headers).requestBody("{\"pageNo\":1,\"pageSize\":1}").ignoreContentType(true).post();
//        System.out.println(post1.body().text());
        JSONObject data1 = JSON.parseObject(post1.body().text()).getJSONArray("data").getJSONObject(0);
        Integer id = data1.getInteger("id");
        Integer stageId = data1.getInteger("stageId");


        Document post = Jsoup.connect("https://dxx.scyol.com/api/student/showStudyStageOrg?id="+id+"&stageId="+stageId).headers(headers).ignoreContentType(true).post();
//        System.out.println(post.body().text());
        String studentVO = JSON.parseObject(post.body().text()).getString("data");
//        System.out.println(studentVO);


        Document document = Jsoup.connect("https://dxx.scyol.com/api/stages/currentInfo").headers(headers).ignoreContentType(true).requestBody("{}").post();
        JSONObject jsonObject = JSON.parseObject(document.body().text());
//        System.out.println(document.body().text());
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject currentStages = data.getJSONObject("currentStages");
        String snum = currentStages.getString("snum");
//        System.out.println(snum);
//        System.out.println(studentVO);

        //第三步
        Map<String,String> headers=new HashMap<>();
        headers.put("access-control-allow-credentials","true");
        headers.put("content-encoding","gzip");
        headers.put("content-type","application/json;charset=UTF-8");
        headers.put("token",token);
        Document res = Jsoup.connect("https://dxx.scyol.com/api/student/commit").ignoreContentType(true).requestBody(studentVO).headers(headers).post();
        JSONObject jsonObject1 = JSON.parseObject(res.body().text());
        String msg = jsonObject1.getString("msg");
        sb.append("大学习").append(snum).append(":");
        if (msg==null){
            sb.append("成功!");
        }else {
            sb.append(msg);
        }
//        System.out.println(msg);
        return sb.toString();
    }

    public String getTokens(String code,String uid) throws IOException{
        String InsertCode="{\"code\":\""+code+"\"}";
        Document post = Jsoup.connect("https://dxx.scyol.com/api/wechat/login").ignoreContentType(true).headers(headers).requestBody(InsertCode).post();
        JSONObject jsonObject = JSON.parseObject(post.body().text());
        try {
            String token = jsonObject.getJSONObject("data").getString("token");
            tokenMap.put(uid+"",token);
            WriteIntoJson();
            return "token获取成功:"+token;

        } catch (NullPointerException e) {
            log.warn(post.body().text());
            log.warn(post.body().text());
            return null;
        }
    }
    private void WriteIntoJson(){
        String jsonString = JSON.toJSONString(tokenMap);
        BufferedWriter writer= null;
        try {
            writer = new BufferedWriter(new FileWriter("data/bigLearn.json"));
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
