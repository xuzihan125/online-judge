package com.oj.ojBackend.model.dto.solutionsubmit;

import lombok.Data;

@Data
public class JudgeInfo {
    /**
     * program execution info
     */
    private String message;

    /**
     * program execution memory.(kb)
     */
    private Long memory;

    /**
     * program execution time.(ms)
     */
    private Long time;
}
