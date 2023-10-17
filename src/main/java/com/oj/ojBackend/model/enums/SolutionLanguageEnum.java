package com.oj.ojBackend.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * usable language enum
 */
public enum SolutionLanguageEnum {
    JAVA("java", "java"),
    CPLUSPLUS("cpp", "cpp"),
    GOLANG("go", "go");

    private final String text;

    private final String value;

    SolutionLanguageEnum(String text, String value) {
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
    public static SolutionLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SolutionLanguageEnum anEnum : SolutionLanguageEnum.values()) {
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
