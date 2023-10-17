package com.oj.ojBackend.judge;

import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import com.oj.ojBackend.model.vo.SolutionSubmitVO;

public interface JudgeService {

    SolutionSubmit doJudge(long questionSubmitId);
}
