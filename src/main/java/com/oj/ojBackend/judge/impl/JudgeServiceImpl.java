package com.oj.ojBackend.judge.impl;

import cn.hutool.json.JSONUtil;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.exception.BusinessException;
import com.oj.ojBackend.judge.JudgeService;
import com.oj.ojBackend.judge.codesandbox.CodeSandbox;
import com.oj.ojBackend.judge.codesandbox.CodeSandboxFactory;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import com.oj.ojBackend.judge.strategy.JudgeContext;
import com.oj.ojBackend.judge.strategy.JudgeManager;
import com.oj.ojBackend.judge.strategy.JudgeStrategy;
import com.oj.ojBackend.judge.strategy.impl.DefaultJudgeStrategyImpl;
import com.oj.ojBackend.model.dto.question.JudgeCase;
import com.oj.ojBackend.model.dto.question.JudgeConfig;
import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;
import com.oj.ojBackend.model.entity.Question;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import com.oj.ojBackend.model.enums.JudgeInfoMessageEnum;
import com.oj.ojBackend.model.enums.SolutionSubmitStatusEnum;
import com.oj.ojBackend.model.vo.SolutionSubmitVO;
import com.oj.ojBackend.service.QuestionService;
import com.oj.ojBackend.service.SolutionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private SolutionSubmitService solutionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public SolutionSubmit doJudge(long solutionSubmitId) {
        SolutionSubmit solutionSubmit = solutionSubmitService.getById(solutionSubmitId);
        if(solutionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "solution submitted doesn't exist");
        }
        Question question = questionService.getById(solutionSubmit.getQuestionId());
        //check if the question is under judging or already judged
        if(solutionSubmit.getStatus() != SolutionSubmitStatusEnum.WAITING.getValue()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "solution is already under judge");
        }
        //update solutionSubmit status
        SolutionSubmit solutionSubmitUpdate = new SolutionSubmit();
        solutionSubmitUpdate.setId(solutionSubmitId);
        solutionSubmitUpdate.setStatus(SolutionSubmitStatusEnum.JUDGING.getValue());
        boolean update = solutionSubmitService.updateById(solutionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "can't update solution status before execute");
        }
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "question trying to answer doesn't exist");
        }
        // setting up execute request
        String language = solutionSubmit.getLanguage();
        String code = solutionSubmit.getCode();
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .language(language)
                .code(code)

                .build();
        // use sandbox, execute solution
        CodeSandbox sandbox = CodeSandboxFactory.newInstance(type);
        ExecuteCodeRespond executeCodeRespond = sandbox.executeCode(request);
        //judge the execute result
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeRespond.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeRespond.getOutputList());
        judgeContext.setQuestion(question);
        JudgeStrategy judgeStrategy = JudgeManager.judgeStrategyManager(solutionSubmit.getLanguage());
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        // change result in database
        SolutionSubmitStatusEnum result = judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue()) ? SolutionSubmitStatusEnum.SUCCESS : SolutionSubmitStatusEnum.FAIL;
        solutionSubmitUpdate.setStatus(result.getValue());
        solutionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = solutionSubmitService.updateById(solutionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "can't update solution status after execute");
        }
        SolutionSubmit solutionSubmitFinal = solutionSubmitService.getById(solutionSubmitId);
        return solutionSubmitFinal;
    }
}
