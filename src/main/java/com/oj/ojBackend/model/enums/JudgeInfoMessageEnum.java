package com.oj.ojBackend.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * enum for possible condition of solution judge
 */
public enum JudgeInfoMessageEnum {
    ACCEPTED("success", "accepted"),
    WRONG_ANSWER("solution produce at least one wrong answer", "solution produce at least one wrong answer"),
    COMPILE_ERROR("code provided can't be compiled", "code provided can't be compiled"),
    MEMORY_LIMIT_EXCEEDED("solution provided exceed memory limit", "solution provided exceed memory limit"),
    TIME_LIMIT_EXCEEDED("solution provided exceed time limit", "solution provided exceed time limit"),
    PRESENTATION_ERROR("presentation of result is wrong", "presentation of result is wrong"),
    OUTPUT_LIMIT_EXCEEDED("display of result exceed limit", "display of result exceed limit"),
    WAITING("waiting for the result", "waiting for the result"),
    DANGEROUS_OPERATION("dangerous operation detected", "dangerous operation detected"),
    RUNTIME_ERROR("runtime error occur", "runtime error occur"),
    SYSTEM_ERROR("system error occur", "system error occur");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * get list of all the values
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * get Enum object based on the value
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
