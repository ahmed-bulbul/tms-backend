package com.tms.configuaration.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleViewModel {
    private Long moduleId;
    private String moduleName;
    private Integer order;
    private String image;
    List<SubModuleViewModel> subModuleList;
}
