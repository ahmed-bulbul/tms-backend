package com.tms.common.generics;


import com.tms.common.payload.response.PageData;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ISearchController<E extends AbstractEntity, D extends IDto, S extends SDto> extends IController<E, D> {
    ResponseEntity<PageData> search(S s, Pageable pageable);
}
