package com.tms.configuaration.controller;


import com.tms.common.generics.AbstractSearchController;
import com.tms.configuaration.payload.request.WorkFLowActionRequestDto;
import com.tms.configuaration.payload.search.WorkFlowActionSearchDto;
import com.tms.configuaration.entity.WorkFlowAction;
import com.tms.configuaration.service.WorkFlowActionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow-actions")
public class WorkFlowActionController extends AbstractSearchController<WorkFlowAction, WorkFLowActionRequestDto, WorkFlowActionSearchDto> {
    public WorkFlowActionController(WorkFlowActionService service) {
        super(service);
    }
}
