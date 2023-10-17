//package com.oj.ojBackend.model.enums;
//
//import org.apache.commons.lang3.ObjectUtils;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * user role enum
// *
// */
//public enum CodeSandboxTypeEnum {
//
//    Example("user", "user"),
//    Remote("admin", "admin"),
//    ThirdParty("banned", "ban");
//
//    private final String text;
//
//    private final String value;
//
//    CodeSandboxTypeEnum(String text, String value) {
//        this.text = text;
//        this.value = value;
//    }
//
//    /**
//     * get value list
//     *
//     * @return
//     */
//    public static List<String> getValues() {
//        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
//    }
//
//    /**
//     * get enum based on value
//     *
//     * @param value
//     * @return
//     */
//    public static CodeSandboxTypeEnum getEnumByValue(String value) {
//        if (ObjectUtils.isEmpty(value)) {
//            return null;
//        }
//        for (CodeSandboxTypeEnum anEnum : CodeSandboxTypeEnum.values()) {
//            if (anEnum.value.equals(value)) {
//                return anEnum;
//            }
//        }
//        return null;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public String getText() {
//        return text;
//    }
//}
