package com.tms.store.controller;

import com.tms.common.generics.AbstractSearchController;
import com.tms.common.generics.ISearchService;
import com.tms.store.dto.request.LocationRequestDto;
import com.tms.store.dto.search.LocationSearchDto;
import com.tms.store.entity.Location;

public class CategoryController extends AbstractSearchController<Location, LocationRequestDto, LocationSearchDto> {

    public CategoryController(ISearchService<Location, LocationRequestDto, LocationSearchDto> iSearchService) {
        super(iSearchService);
    }
}
