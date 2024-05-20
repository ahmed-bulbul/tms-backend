package com.tms.common.service.impl;


import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.repository.AccessRightRepository;
import com.tms.common.models.AccessRight;
import com.tms.common.service.AccessRightService;
import com.tms.common.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Access Right Service Implementation
 *
 * @author Pranoy Das
 */
@Service
public class AccessRightServiceImpl implements AccessRightService {
    private final AccessRightRepository accessRightRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessRightServiceImpl.class);

    /**
     * Autowired constructor
     *
     * @param accessRightRepository {@link AccessRightRepository}
     */
    @Autowired
    public AccessRightServiceImpl(AccessRightRepository accessRightRepository) {
        this.accessRightRepository = accessRightRepository;
    }

    /**
     * responsible for finding all access rights by access ids
     *
     * @param accessRightIds set of access right ids
     * @return access right
     */
    @Override
    public Set<AccessRight> findAllAccessRightsByIds(Set<Integer> accessRightIds) {
        return accessRightRepository.findAllByIdIn(accessRightIds);
    }

    @Override
    public void saveAccessRightList(List<AccessRight> accessRights) {

        try {
            if (CollectionUtils.isEmpty(accessRights)) {
                return;
            }
//            if (accessRightRepository.findById(accessRights.get(FIRST_INDEX).getId()).isPresent()) {
//                return;
//            }

            accessRightRepository.saveAll(accessRights);
        } catch (Exception e) {

            String entityName = accessRights.get(0).getClass().getSimpleName();
            LOGGER.error("Save failed for entity {}", entityName);
            LOGGER.error("Error message: {}", e.getMessage());
            throw AppServerException.dataSaveException(
                    Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC, entityName)
            );
        }
    }
}
