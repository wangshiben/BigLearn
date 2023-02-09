package com.Compoments;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BeanConfig {
    @Bean
    public Map<String, String>HeadMapPut(){
        //缺少token
        Map<String ,String> map=new HashMap<>();
        map.put("Sec-Fetch-Site","same-origin");
        map.put("Sec-Fetch-Mode","cors");
        map.put("Sec-Fetch-Dest","empty");
        map.put("Accept-Encoding","gzip,deflate");
        map.put("Referer","https://dxx.scyol.com/v_prod6.0.2/");
        map.put("Host","dxx.scyol.com");
        map.put("Connection","keep-alive");
        map.put("Accept","*/*");
        map.put("User-Agent","Mozilla/5.0 (Linux; Android 12; V2073A Build/SP1A.210812.003; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/3263 MMWEBSDK/20210902 Mobile Safari/537.36 MMWEBID/3139 MicroMessenger/8.0.15.2020(0x28000F31) Process/toolsmp WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64");
        map.put("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        map.put("Origin","http://scyol.com");

        map.put("X-Requested-With"," com.tencent.mm");
        map.put("Content-Length","9");
        map.put("Content-Type","application/json");
        return map;
    }

    @Bean
    public Map<String,String> TokenMap(){
        BufferedReader reader= null;
        File makeFile=new File("data");
        if (!makeFile.exists()){
            makeFile.mkdirs();
        }
        try {
            reader = new BufferedReader(new FileReader("data/bigLearn.json"));
            byte []temp={-1};
            StringBuilder stringBuilder=new StringBuilder();
            while ((temp[0]= (byte) reader.read())!=-1){
                stringBuilder.append(new String(temp)).append(reader.readLine());
            }
            Map<String ,String> map;
            String BigLearn = stringBuilder.toString();
            map = JSON.parseObject(BigLearn, HashMap.class);
            log.info("大学习json读取成功" );
            reader.close();
            return map;
        } catch (IOException e) {
            log.warn("如果是第一次启动,报错为文件未找到异常请忽略");
            log.error(e.getMessage());
            return new HashMap<>();
        }
    }

}
