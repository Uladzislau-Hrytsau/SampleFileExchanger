package com.exchange.dao;

import java.util.List;

public interface UserRoleDao {

    List<String> getRolesByUserName(String userName);

}
