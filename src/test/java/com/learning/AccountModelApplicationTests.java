package com.learning;

import com.google.common.hash.Hashing;
import com.learning.controller.ServiceController;
import com.learning.entity.Account;
import com.learning.entity.Utxo;
import com.learning.mapper.AccountMapper;
import com.learning.mapper.UtxoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

@SpringBootTest
class AccountModelApplicationTests {

    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    void hashTest() {
        String hash = Hashing.sha256().hashString("testing", StandardCharsets.UTF_8).toString();
        System.out.println(hash);
    }

    @Autowired
    AccountMapper accountMapper;
    // ※新增账户
    @Test
    void accountMapperAddTest() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 365);

        accountMapper.addAccount(new Account("覃老师", new BigDecimal(0), "currency", 1, date, calendar.getTime()));
    }

    @Autowired
    UtxoMapper utxoMapper;
    // 登录奖励记账
    @Test
    void utxoMapperAddTest() {
        String hashTest001 = Hashing.sha256().hashString("testing", StandardCharsets.UTF_8).toString();
        String hashTest002 = Hashing.sha256().hashString("testing2", StandardCharsets.UTF_8).toString();

        utxoMapper.addUtxo(new Utxo("#1001", "初次登录游戏奖励", "1", new BigDecimal("12.5"), "张三", new Date(),hashTest001));
        utxoMapper.addUtxo(new Utxo("#2001", "#1001-1", "1", new BigDecimal("2.5"), "李四", new Date(),hashTest002));
        utxoMapper.addUtxo(new Utxo("#2001", "#1001-1", "2", new BigDecimal("10"), "张三", new Date(),hashTest002));
    }

    // 删记录
    @Test
    void utxoMapperDeleteTest() {
        //utxoMapper.deleteUtxo("2022020821291475345580072378799");
    }

    // 删账户
    @Test
    void accountMapperDeleteTest() {
        //accountMapper.deleteAccount("张三");
    }

    @Autowired
    ServiceController serviceController;

    // ※初次登录奖励
    @Test
    void transferServiceTest001() throws ParseException {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", "覃老师");
        testMap.put("destination-accountId", "覃老师");
        testMap.put("transaction-type", "currency");
        testMap.put("amount", "250");
        serviceController.transfer(testMap);
    }

    // ※一对一转账
    @Test
    void transferServiceTest002() throws ParseException {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", "覃老师");
        testMap.put("destination-accountId", "孙文力");
        testMap.put("transaction-type", "currency");
        testMap.put("amount", "200");
        serviceController.transfer(testMap);
    }



}
