package com.oj.ojBackend.judge.codesandbox.impl;

import com.oj.ojBackend.judge.codesandbox.CodeSandbox;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;
import com.oj.ojBackend.model.enums.JudgeInfoMessageEnum;
import com.oj.ojBackend.model.enums.SolutionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeRespond executeCode(ExecuteCodeRequest executeCodeRequest) {
        String code = executeCodeRequest.getCode();
        List<String> inputList = executeCodeRequest.getInputList();
        String language = executeCodeRequest.getLanguage();

        ExecuteCodeRespond executeCodeRespond = new ExecuteCodeRespond();
        executeCodeRespond.setOutputList(inputList);
        executeCodeRespond.setMessage("test execute finish");
        executeCodeRespond.setStatus(SolutionSubmitStatusEnum.SUCCESS.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeRespond.setJudgeInfo(judgeInfo);


        return executeCodeRespond;
    }
}
