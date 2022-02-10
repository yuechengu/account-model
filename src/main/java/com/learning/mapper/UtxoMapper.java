package com.learning.mapper;

import com.learning.entity.Utxo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UtxoMapper {

    List<Utxo> queryUtxoList();

    Utxo queryUtxoById(int id);

    int addUtxo(Utxo utxo);

    int updateUtxo(Utxo utxo);

    int deleteUtxo(String id);

    int queryUxtosNumber();
}
