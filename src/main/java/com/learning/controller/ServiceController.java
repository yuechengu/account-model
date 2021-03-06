package com.learning.controller;

import com.learning.entity.ApplyTxResEntity;
import com.learning.service.TransferService;
import com.learning.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    TransferService transferService;

    @RequestMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Map test() {
        Map<String, Object> result = new HashMap<>();
        String txResult = "OK";
        Date txTime = new Date();
        String txHashCode = "01abcdef";

        result.put("result", txResult);
        result.put("transaction-time", txTime);
        result.put("hash-code", txHashCode);
        return result;
    }

    @RequestMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ApplyTxResEntity transfer(@RequestBody Map<String, Object> map) throws ParseException {
        String originAccountId = (String) map.get("origin-accountId");
        String destinationAccountId = (String) map.get("destination-accountId");
        String transactionType = (String) map.get("transaction-type");
        String amount = (String) map.get("amount");

        return transferService.transfer(originAccountId, destinationAccountId, transactionType, amount);
    }
}
