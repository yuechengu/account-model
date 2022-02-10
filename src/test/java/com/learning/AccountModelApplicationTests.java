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
    void sha256Test() {
        String hash = Hashing.sha256().hashString("testing", StandardCharsets.UTF_8).toString();
        System.out.println(hash);
    }

    @Test
    void sha1Test() {
        String hash = Hashing.sha1().hashString("testing", StandardCharsets.UTF_8).toString();
        System.out.println(hash);
    }

    @Test
    void sipHash24Test() {
        String hash = Hashing.sipHash24().hashString("testing", StandardCharsets.UTF_8).toString();
        System.out.println(hash);
    }

    @Autowired
    AccountMapper accountMapper;
    // ※新增账户
    @Test
    void accountMapperAddTest() {
        String hash = Hashing.sipHash24().hashString("guyuechen", StandardCharsets.UTF_8).toString();

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 365);

        accountMapper.addAccount(new Account(hash, new BigDecimal(0), "currency", 1, date, calendar.getTime()));
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
        utxoMapper.deleteUtxo("2022021011124365776153021692181");
        utxoMapper.deleteUtxo("2022021011141322694959495018623");
        utxoMapper.deleteUtxo("2022021011153394963211186085985");
        utxoMapper.deleteUtxo("2022021011164389926683269544334");
        utxoMapper.deleteUtxo("2022021011171265213782915379212");
        utxoMapper.deleteUtxo("2022021011181585002118770091583");
        utxoMapper.deleteUtxo("2022021011325048141067204333703");
    }

    // 删账户
    @Test
    void accountMapperDeleteTest() {
        accountMapper.deleteAccount("59a89ac040cc96dc");
        accountMapper.deleteAccount("692009411618094d");
        accountMapper.deleteAccount("9a72565c525e7626");
    }

    @Autowired
    ServiceController serviceController;

    // ※铸币
    @Test
    void transferServiceTest001() throws ParseException {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", "59a89ac040cc96dc");
        testMap.put("destination-accountId", "59a89ac040cc96dc");
        testMap.put("transaction-type", "currency");
        testMap.put("amount", "120");
        serviceController.transfer(testMap);
    }

    // ※一对一转账
    @Test
    void transferServiceTest002() throws ParseException {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", "08786156651e37c2");
        testMap.put("destination-accountId", "937be13f4f3ada89");
        testMap.put("transaction-type", "currency");
        testMap.put("amount", "120");
        serviceController.transfer(testMap);
    }


    // ※初次登录+奖励测试
    @Test
    void firstTimeLoginTest() throws ParseException {
        String address = Hashing.sipHash24().hashString("acn", StandardCharsets.UTF_8).toString();

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 365);

        accountMapper.addAccount(new Account(address, new BigDecimal(0), "currency", 1, date, calendar.getTime()));

        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", address);
        testMap.put("destination-accountId", address);
        testMap.put("transaction-type", "currency");
        testMap.put("amount", "500");
        serviceController.transfer(testMap);
    }



}
