package com.oj.ojBackend.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {
    /**
     * time limit of the program.(ms)
     */
    private Long timeLimit;

    /**
     * memory limit of the program.(kb)
     */
    private Long memoryLimit;

    /**
     * stack limit of the program(kb)
     */
    private Long stackLimit;
}
