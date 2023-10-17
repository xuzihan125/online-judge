package com.oj.ojBackend.judge.strategy;

import com.oj.ojBackend.judge.strategy.impl.DefaultJudgeStrategyImpl;
import com.oj.ojBackend.judge.strategy.impl.JavaJudgeStrategyImpl;

public class JudgeManager {
    public static JudgeStrategy judgeStrategyManager(String language){
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if(language.equals("java")){
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy;
    }
}
