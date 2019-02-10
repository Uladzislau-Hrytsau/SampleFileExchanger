package com.exchange.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface User role service.
 */
@Service
@Transactional
public interface UserRoleService {

    /**
     * Gets roles by user name.
     *
     * @param userName the user name
     * @return the roles by user name
     */
    List<String> getRolesByUserName(String userName);

}
