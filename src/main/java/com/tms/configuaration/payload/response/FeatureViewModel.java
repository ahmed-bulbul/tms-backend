package com.tms.configuaration.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureViewModel {
    private Long featureId;
    private String featureName;
    private String urlPath;
    private Integer order;
    private Boolean isBase;
    private List<ActionViewModel> actionViewModelList;
}
