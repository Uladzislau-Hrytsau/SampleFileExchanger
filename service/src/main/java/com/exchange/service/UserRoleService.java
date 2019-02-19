package com.exchange.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface UserRoleService {

    List<String> getRolesByUserName(String userName);

}
