package com.oj.ojBackend.judge.strategy;

import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
