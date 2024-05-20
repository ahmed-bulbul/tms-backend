package com.tms.common.service;


import com.tms.common.models.AccessRight;

import java.util.List;
import java.util.Set;

/**
 * access right service
 *
 * @author Pranoy Das
 */
public interface AccessRightService {
    Set<AccessRight> findAllAccessRightsByIds(Set<Integer> accessRightIds);
    void saveAccessRightList(List<AccessRight> accessRights);
}
