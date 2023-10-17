package com.oj.ojBackend.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.oj.ojBackend.judge.strategy.JudgeContext;
import com.oj.ojBackend.judge.strategy.JudgeStrategy;
import com.oj.ojBackend.model.dto.question.JudgeCase;
import com.oj.ojBackend.model.dto.question.JudgeConfig;
import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;
import com.oj.ojBackend.model.entity.Question;
import com.oj.ojBackend.model.enums.JudgeInfoMessageEnum;

import java.util.List;

public class JavaJudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        judgeInfo.setMemory(memory);
        judgeInfo.setTime(time);
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        //correctness
        if(outputList.size() != inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        for(int i=0;i<outputList.size();i++){
            if(!judgeCaseList.get(i).getOutput().equals(outputList.get(i))){
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfo;
            }
        }
        // Limitation
        Long JAVA_TIME_COST = 1000L;
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit() ;
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (memoryLimit < memory){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        if ( timeLimit < time - JAVA_TIME_COST){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfo;
    }
}
