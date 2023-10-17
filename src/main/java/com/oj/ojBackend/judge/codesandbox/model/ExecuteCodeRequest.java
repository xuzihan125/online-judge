package com.oj.ojBackend.judge.codesandbox.model;

import com.oj.ojBackend.model.dto.question.JudgeCase;
import com.oj.ojBackend.model.dto.question.JudgeConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    private String code;

    private List<String> inputList;

    private String language;
}
