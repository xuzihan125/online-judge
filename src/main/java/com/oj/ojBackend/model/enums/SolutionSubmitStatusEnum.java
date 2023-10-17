package com.oj.ojBackend.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * judge solution enum
 */
public enum SolutionSubmitStatusEnum {
    WAITING("to be judged", 0),
    JUDGING("judging", 1),
    SUCCESS("success", 2),
    FAIL("fail", 3);

    private final String text;

    private final Integer value;

    SolutionSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * get list of all the values
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * get Enum object based on the value
     *
     * @param value
     * @return
     */
    public static SolutionSubmitStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SolutionSubmitStatusEnum anEnum : SolutionSubmitStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
