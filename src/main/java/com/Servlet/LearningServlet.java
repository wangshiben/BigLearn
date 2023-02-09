package com.Servlet;

import com.Resp.BaseRespones;
import com.Service.LearnService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
public class LearningServlet {
    @Autowired
    private LearnService service;
    @Value("${idhead}")
    private String HeaderGet;
    @Autowired
    @Qualifier("TokenMap")
    private Map<String ,String> tokenMap;

    @PostMapping("/getLearningToken")
    public BaseRespones<String> getTokens(HttpServletRequest request){
        String code = request.getHeader("code");
        String id = request.getHeader(HeaderGet);
        String[] split = code.split("code=");
        String[] codes = split[1].split("&");
        String tokens=null;
        try {
            tokens = service.getTokens(codes[0], id);
        } catch (IOException e) {
            log.error(e.getMessage());
            return BaseRespones.failed("获取token失败捏");
        }
        return BaseRespones.success("成功",tokens);
    }
    @GetMapping("/learning")
    public BaseRespones<String> Learning(HttpServletRequest request){
        String ID = request.getHeader(HeaderGet);
        String res=null;
        try {
            res = service.BigLeaningByTokens(tokenMap.get(ID));
        } catch (IOException e) {
            log.error(e.getMessage());
//            return BaseRespones.failed("学习失败:"+e.getMessage());
        }
        return BaseRespones.success("成功",res);
    }


}
