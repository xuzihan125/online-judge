package com.oj.ojBackend.judge.strategy;

import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;
import com.oj.ojBackend.model.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

}
