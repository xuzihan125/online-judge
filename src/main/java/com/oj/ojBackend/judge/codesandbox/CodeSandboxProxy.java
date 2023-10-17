package com.oj.ojBackend.judge.codesandbox;

import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox){
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeRespond executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("sandbox request info:" + executeCodeRequest.toString());
        ExecuteCodeRespond executeCodeRespond = codeSandbox.executeCode(executeCodeRequest);
        log.info("sandbox request info:" + executeCodeRespond.toString());
        return executeCodeRespond;
    }
}
