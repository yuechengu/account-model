package com.learning.controller;

import com.learning.entity.Utxo;
import com.learning.mapper.UtxoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class TransactionController {

    @Autowired
    UtxoMapper utxoMapper;

    @RequestMapping("/transaction")
    public String list(Model model) {

        Collection<Utxo> utxos = utxoMapper.queryUtxoList();
        model.addAttribute("utxos",utxos);
        return "transaction/list";
    }
}
