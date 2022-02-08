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
import java.util.Date;
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
    @Test
    void accountMapperTest() {
        accountMapper.addAccount(new Account("张三", new BigDecimal(0), "currency", 1, new Date(), null));
        accountMapper.addAccount(new Account("李四", new BigDecimal(0), "currency", 1, new Date(), null));
        accountMapper.addAccount(new Account("王五", new BigDecimal(0), "currency", 1, new Date(), null));
    }

    @Autowired
    UtxoMapper utxoMapper;
    @Test
    void utxoMapperAddTest() {
        String hashTest001 = Hashing.sha256().hashString("testing", StandardCharsets.UTF_8).toString();
        String hashTest002 = Hashing.sha256().hashString("testing2", StandardCharsets.UTF_8).toString();

        utxoMapper.addUtxo(new Utxo("#1001", "初次登录游戏奖励", "1", new BigDecimal("12.5"), "张三", new Date(),hashTest001));
        utxoMapper.addUtxo(new Utxo("#2001", "#1001-1", "1", new BigDecimal("2.5"), "李四", new Date(),hashTest002));
        utxoMapper.addUtxo(new Utxo("#2001", "#1001-1", "2", new BigDecimal("10"), "张三", new Date(),hashTest002));
    }

    @Test
    void utxoMapperDeleteTest() {
        utxoMapper.deleteUtxo("#1001");
        utxoMapper.deleteUtxo("#2001");
    }

    @Autowired
    ServiceController serviceController;

    // 初次登录
    @Test
    void transferServiceTest001() {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", "张三");
        testMap.put("destination-accountId", "张三");
        testMap.put("trasaction-type", "currency");
        testMap.put("amount", "10");
        serviceController.transfer(testMap);
    }

    // 转账
    @Test
    void transferServiceTest002() {
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("origin-accountId", "张三");
        testMap.put("destination-accountId", "李四");
        testMap.put("trasaction-type", "currency");
        testMap.put("amount", "2");
    }



}
