package com.tms.common.service;


import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.repository.ActionRepository;
import com.tms.common.models.Action;
import com.tms.common.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ActionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
    private final ActionRepository actionRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }


    public List<Action> saveAll(List<Action> actions) {
        try {
            if (CollectionUtils.isEmpty(actions)) {
                return actions;
            }
//            if (actionRepository.findById(actions.get(FIRST_INDEX).getId()).isPresent()) {
//                return Collections.emptyList();
//            }

            return actionRepository.saveAll(actions);
        } catch (Exception e) {
            String entityName = actions.get(0).getClass().getSimpleName();
            LOGGER.info("Save failed for entity {}", entityName);
            LOGGER.error("Error message: {}", e.getMessage());
            throw AppServerException
                    .dataSaveException(Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC, entityName));
        }
    }
}
