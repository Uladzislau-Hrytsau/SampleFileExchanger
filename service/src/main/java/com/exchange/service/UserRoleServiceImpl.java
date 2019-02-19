package com.exchange.service;

import com.exchange.dao.UserRoleDao;
import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleDao userRoleDao;

    @Value("${userRileService.incorrectUserName}")
    private String incorrectUserName;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public List<String> getRolesByUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new ValidationException(incorrectUserName);
        }
        return userRoleDao.getRolesByUserName(userName);
    }

}
