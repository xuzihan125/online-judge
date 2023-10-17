package com.oj.ojBackend.judge.codesandbox.model;

import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRespond {
    private List<String> outputList;

    private String message;

    private Integer status;

    private JudgeInfo judgeInfo;

}
