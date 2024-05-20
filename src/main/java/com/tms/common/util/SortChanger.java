package com.tms.common.util;

import com.tms.common.constant.ApplicationConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortChanger {
    public static Pageable descendingSortByCreatedAt(Pageable pageable){
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(ApplicationConstant.CREATED_DATE).descending());
    }
}
