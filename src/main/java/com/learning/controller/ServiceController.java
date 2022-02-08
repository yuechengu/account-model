package com.learning.controller;

import com.learning.entity.ApplyTxResEntity;
import com.learning.service.TransferService;
import com.learning.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        Map<String, String> result = new HashMap<String, String>();
        String txResult = "OK";
        String txTime = JsonUtils.getJson(new Date());
        String txHashCode = "01x111111f";

        result.put("result", txResult);
        result.put("transaction-time", txTime);
        result.put("hash-code", txHashCode);
        return result;
    }

    @RequestMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public ApplyTxResEntity transfer(@RequestBody Map<String, Object> map) {
        String originAccountId = (String) map.get("origin-accountId");
        String destinationAccountId = (String) map.get("destination-accountId");
        String trasactionType = (String) map.get("trasaction-type");
        String amount = (String) map.get("amount");

        ApplyTxResEntity result = transferService.transfer(originAccountId, destinationAccountId, trasactionType, amount);
        return result;
    }
}
