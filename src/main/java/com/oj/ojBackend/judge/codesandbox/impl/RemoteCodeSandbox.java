package com.oj.ojBackend.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.exception.BusinessException;
import com.oj.ojBackend.judge.codesandbox.CodeSandbox;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Lazy
public class RemoteCodeSandbox implements CodeSandbox {

    @Value("${codesandbox.IP:localhost}")
    private String sandboxIP;

    @Value("${codesandbox.port:8081}")
    private String port;

//    @Autowired
//    public RemoteCodeSandbox(@Value("${codesandbox.IP:localhost}") String sandboxIP,  @Value("${codesandbox.port:8081}") String port) {
//        this.sandboxIP = sandboxIP;
//        this.port = port;
//    }

    @Async
    @Override
    public ExecuteCodeRespond executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://" + sandboxIP + ":" + port + "/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        try{
            log.info(url);
            String respondStr = HttpUtil.createPost(url)
                    .body(json)
                    .execute()
                    .body();
            if(StringUtils.isBlank(respondStr)) {
                throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
            }
            ExecuteCodeRespond executeCodeRespond = JSONUtil.toBean(respondStr, ExecuteCodeRespond.class);
            log.info(executeCodeRespond.toString());
            return executeCodeRespond;
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
        }
    }
}
