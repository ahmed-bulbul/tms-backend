package com.tms.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;

/**
 * Set Utils
 *
 * @author Masud Rana
 */
public class SetUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetUtil.class);

    /**
     * Get set difference
     *
     * @param firstSet  {@link T}
     * @param secondSet {@link T}
     * @return {@link Set}
     */
    public static <T> Set<T> getSetDifference(Set<T> firstSet, Set<T> secondSet) {
        if (CollectionUtils.isEmpty(firstSet) && CollectionUtils.isEmpty(secondSet)) {
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(secondSet)) {
            return firstSet;
        }
        firstSet.removeIf(secondSet::contains);
        return firstSet;
    }

}
