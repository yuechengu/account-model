package com.learning.mapper;

import com.learning.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {

    List<Account> queryAccountList();

    Account queryAccountById(String id);

    int addAccount(Account account);

    int updateAccount(Account account);

    int deleteAccount(String id);
}
