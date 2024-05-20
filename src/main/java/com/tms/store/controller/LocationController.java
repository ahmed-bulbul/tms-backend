package com.tms.store.controller;

import com.tms.store.service.LocationService;
import com.tms.common.constant.ApplicationConstant;
import com.tms.common.generics.AbstractSearchController;
import com.tms.common.generics.ISearchService;
import com.tms.configuaration.payload.request.ApprovalRequestDto;
import com.tms.store.dto.request.LocationRequestDto;
import com.tms.store.dto.search.LocationSearchDto;
import com.tms.store.entity.Location;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController extends AbstractSearchController<Location, LocationRequestDto, LocationSearchDto> {

    private final LocationService locationService;
    public LocationController(ISearchService<Location, LocationRequestDto, LocationSearchDto> iSearchService, LocationService locationService) {
        super(iSearchService);
        this.locationService = locationService;
    }

    @PutMapping("decide/{id}")
    public ResponseEntity<String> makeDecision(@PathVariable Long id, @Valid @RequestBody ApprovalRequestDto approvalRequestDto) {
        locationService.makeDecision(id, approvalRequestDto);
        return ResponseEntity.ok(ApplicationConstant.STATUS_CHANGED);
    }
}
