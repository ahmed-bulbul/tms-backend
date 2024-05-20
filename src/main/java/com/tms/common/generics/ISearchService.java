package com.tms.common.generics;


import com.tms.common.payload.response.PageData;
import org.springframework.data.domain.Pageable;

public interface ISearchService<E extends AbstractEntity, D extends IDto, S extends SDto> extends IService<E, D> {
    PageData search(S s, Pageable pageable);
}
