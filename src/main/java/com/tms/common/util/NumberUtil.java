package com.tms.common.util;


import com.tms.common.constant.NumberConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Number Utils
 *
 * @author Pranoy Das
 */
public class NumberUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtil.class);
    private static final String DOT_REGEX = "\\.";
    private static final char CHAR_ZERO = '0';

    public static final Integer convertToInteger(String numberStr) {
        return convertToInteger(numberStr, -1);
    }

    public static final Integer convertToInteger(String numberStr, Integer defaultValue) {
        try {
            if (StringUtils.isNotBlank(numberStr)) {
                return Integer.valueOf(numberStr);
            } else {
                return defaultValue;
            }
        } catch (NumberFormatException ex) {
            LOGGER.info("Can't convert to integer from given string {}", numberStr);
            return defaultValue;
        }
    }

    public static final Integer getValidPageNumber(Integer number) {
        if (Objects.isNull(number)) {
            return 1;
        }
        return number <= 0 ? 1 : number - 1;
    }

    public static final Integer getValidPageSize(Integer number) {
        if (Objects.isNull(number)) {
            return 1;
        }
        return number <= 0 ? 1 : number;
    }

    public static <T> T getDefaultIfNull(T input, T defaultVal) {
        return Optional.ofNullable(input).orElse(defaultVal);
    }

    public static Double parseDoubleValue(String stringValue) {
        if (StringUtils.isBlank(stringValue)) {
            return null;
        }
        return Double.valueOf(stringValue);
    }



    public static Long parseLongValue(String stringValue) {
        if (StringUtils.isBlank(stringValue)) {
            return null;
        }
        return Long.valueOf(stringValue);
    }

    public static Integer parseIntValue(String stringValue) {
        if (StringUtils.isBlank(stringValue)) {
            return null;
        }
        return Integer.valueOf(stringValue);
    }

    public static Boolean parseBooleanValue(String stringValue) {
        if (StringUtils.isBlank(stringValue)) {
            return null;
        }
        return Boolean.valueOf(stringValue);
    }

    public static Boolean checkValidAirTime(Double time) {
        if (Objects.isNull(time)) {
            LOGGER.info("invalid time(time is null)");
            return false;
        }
        String timeStr = StringUtil.valueOf(time);
        String[] strTimeArray = timeStr.split(DOT_REGEX);
        long value = 0L;
        if (strTimeArray.length == 2) {
            if (strTimeArray[1].length() > 2 || strTimeArray[1].length() < 1) {
                return false;
            }
            value = (strTimeArray[1].charAt(0) - CHAR_ZERO) * 10L;
            if (strTimeArray[1].length() == 2) {
                value += (strTimeArray[1].charAt(1) - CHAR_ZERO);
            }

            if (value > 59) {
                LOGGER.info("invalid time");
                return false;
            }

        }
        return true;
    }





    public static String formatDecimalValue(Double value, String pattern) {
        if (Objects.isNull(value)) {
            LOGGER.error("decimal value is null: {}", value);
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = NumberConstant.TWO_DECIMAL_FORMAT;
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(value);
    }
 }
