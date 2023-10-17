package com.oj.ojBackend.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL util
 */
public class SqlUtils {

    /**
     * check if the sort part of SQL is valid
     *
     * @param sortField SQL to be checked
     * @return false if: 1. empty. 2. contains illegal operation
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
