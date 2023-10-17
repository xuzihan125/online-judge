package com.oj.ojBackend.model.dto.question;

import lombok.Data;

@Data
public class JudgeCase {
    /**
     * suppose input of the program
     */
    private String input;

    /**
     * suppose output of the program
     */
    private String output;
}
