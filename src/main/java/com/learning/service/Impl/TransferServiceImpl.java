package com.learning.service.Impl;

import com.learning.entity.Account;
import com.learning.entity.ApplyTxResEntity;
import com.learning.entity.Utxo;
import com.learning.mapper.AccountMapper;
import com.learning.mapper.UtxoMapper;
import com.learning.service.TransferService;

import com.google.common.hash.Hashing;
import com.learning.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UtxoMapper utxoMapper;

    @Override
    public ApplyTxResEntity transfer(String originAccountId, String destinationAccountId, String transactionType, String amount) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        BigDecimal txAmount = new BigDecimal(amount);
        String txTime;
        String txHashCode;
        Utxo utxo1 = new Utxo(); // ※初次登录，交易额==原始账户余额：只可能存在一条记录
        Utxo utxo2 = new Utxo();
        int initSubTx = 1; // 初始子交易顺序

        Account originAccount = accountMapper.queryAccountById(originAccountId);
        Account destinationAccount = accountMapper.queryAccountById(destinationAccountId);

        // 获取余额，用于判断
        BigDecimal originAccountBalance = originAccount.getBalance();
        BigDecimal destinationAccountBalance = destinationAccount.getBalance();

        // 第一种情况：初次登录奖励
        if (transactionType == "currency"
                && originAccountId == destinationAccountId
                && txAmount.compareTo(BigDecimal.ZERO) == 1) {
            // 开始交易
            originAccountBalance = originAccountBalance.add(new BigDecimal(amount));
            txTime = formatter.format(new Date());
            // 更新账户
            originAccount.setBalance(originAccountBalance);
            accountMapper.updateAccount(originAccount);
            // 开始记账
            utxo1.setTransactionId(OrderUtils.getOrderCode(utxoMapper.queryUxtosNumber()));
            utxo1.setUtxoId("初次登录游戏奖励");
            utxo1.setSubTransaction(String.valueOf(initSubTx++));
            utxo1.setAmount(new BigDecimal(amount));
            utxo1.setDestinationAccountId(destinationAccountId);
            utxo1.setTransactionTime(formatter.parse(txTime));
            // 记账用Hashcode生成
            txHashCode = Hashing.sha1().hashString(
                    utxo1.getTransactionId()
                            .concat(utxo1.getUtxoId())
                            .concat(utxo1.getSubTransaction())
                            .concat(amount)
                            .concat(destinationAccountId)
                            .concat(txTime)
                    , StandardCharsets.UTF_8).toString();
            utxo1.setHash(txHashCode);
            utxoMapper.addUtxo(utxo1);
            // response返回结果
            return new ApplyTxResEntity("OK", txTime, txHashCode);

        } else if (transactionType == "currency"
                && originAccountId != destinationAccountId
                && txAmount.compareTo(BigDecimal.ZERO) == 1) {// 另一种情况：转账
            if (originAccountBalance.compareTo(new BigDecimal(amount)) == -1) {
                txTime = formatter.format(new Date());
                return new ApplyTxResEntity("NG", txTime, "余额不足");
            }
            // 开始正常交易
            originAccountBalance = originAccountBalance.subtract(new BigDecimal(amount));
            destinationAccountBalance = destinationAccountBalance.add(new BigDecimal(amount));
            txTime = formatter.format(new Date());
            // 更新账户
            originAccount.setBalance(originAccountBalance);
            destinationAccount.setBalance(destinationAccountBalance);
            accountMapper.updateAccount(originAccount);
            accountMapper.updateAccount(destinationAccount);
            // 开始记账：utxo1
            utxo1.setTransactionId(OrderUtils.getOrderCode(utxoMapper.queryUxtosNumber()));
            utxo1.setUtxoId("");//※这里题目有逻辑上的缺陷
            utxo1.setSubTransaction(String.valueOf(initSubTx++));
            utxo1.setAmount(new BigDecimal(amount));
            utxo1.setDestinationAccountId(destinationAccountId);
            utxo1.setTransactionTime(formatter.parse(txTime));
            // 记账用Hashcode生成
            txHashCode = Hashing.sha1().hashString(
                    utxo1.getTransactionId()
                            .concat(utxo1.getUtxoId())
                            .concat(utxo1.getSubTransaction())
                            .concat(amount)
                            .concat(destinationAccountId)
                            .concat(txTime)
                    , StandardCharsets.UTF_8).toString();
            utxo1.setHash(txHashCode);
            utxoMapper.addUtxo(utxo1);
            if (originAccountBalance.compareTo(BigDecimal.ZERO) == 1) {
                // 开始记账：utxo2
                utxo2.setTransactionId(utxo1.getTransactionId());
                utxo2.setUtxoId(utxo1.getUtxoId());
                utxo2.setSubTransaction(String.valueOf(initSubTx++));
                utxo2.setAmount(originAccountBalance);
                utxo2.setDestinationAccountId(originAccountId);
                utxo2.setTransactionTime(new Date());
                // 记账用Hashcode生成
                txHashCode = Hashing.sha1().hashString(
                        utxo2.getTransactionId()
                                .concat(utxo2.getUtxoId())
                                .concat(utxo2.getSubTransaction())
                                .concat(utxo2.getAmount().toString())
                                .concat(originAccountId)
                                .concat(txTime)
                        , StandardCharsets.UTF_8).toString();
                utxo2.setHash(txHashCode);
                utxoMapper.addUtxo(utxo2);
            }
            return new ApplyTxResEntity("OK", txTime, txHashCode);
        }
        return new ApplyTxResEntity("NG", formatter.format(new Date()), "非法交易");
    }


}
