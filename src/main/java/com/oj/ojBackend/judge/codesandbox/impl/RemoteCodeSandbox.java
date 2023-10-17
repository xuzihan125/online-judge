package com.oj.ojBackend.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.exception.BusinessException;
import com.oj.ojBackend.judge.codesandbox.CodeSandbox;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import org.apache.commons.lang3.StringUtils;

public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeRespond executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://192.168.80.134:8082/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        try{
            String respondStr = HttpUtil.createPost(url)
                    .body(json)
                    .execute()
                    .body();
            if(StringUtils.isBlank(respondStr)) {
                throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
            }
            ExecuteCodeRespond executeCodeRespond = JSONUtil.toBean(respondStr, ExecuteCodeRespond.class);
            return executeCodeRespond;
        }
        catch (Exception e){
            throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
        }
    }
}
