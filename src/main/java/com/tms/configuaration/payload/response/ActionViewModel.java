package com.tms.configuaration.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionViewModel {
    private Integer actionId;
    private String actionName;
    private Integer accessRightId;
}
