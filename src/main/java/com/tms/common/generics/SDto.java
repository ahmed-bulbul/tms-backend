package com.tms.common.generics;


/**
 * Marker interface for search dto.
 *
 * @author Masud Rana
 */
public interface SDto {
    default Boolean getIsActive() {
        return true;
    }
}
