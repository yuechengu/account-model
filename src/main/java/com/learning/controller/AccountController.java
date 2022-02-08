package com.learning.controller;

import com.learning.entity.Account;
import com.learning.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class AccountController {

    // mybatis
    @Autowired
    AccountMapper accountMapper;

    @RequestMapping("/accounts")
    public String list(Model model) {

        Collection<Account> accounts = accountMapper.queryAccountList();
        model.addAttribute("accounts",accounts);
        return "account/list";
    }
}
