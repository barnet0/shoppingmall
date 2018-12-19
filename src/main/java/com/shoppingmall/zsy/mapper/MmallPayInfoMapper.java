package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallPayInfo;
import com.shoppingmall.zsy.entity.MmallPayInfoTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallPayInfoMapper {
    int countByExample(MmallPayInfoTemplate example);

    int deleteByExample(MmallPayInfoTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallPayInfo record);

    int insertSelective(MmallPayInfo record);

    List<MmallPayInfo> selectByExample(MmallPayInfoTemplate example);

    MmallPayInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallPayInfo record, @Param("example") MmallPayInfoTemplate example);

    int updateByExample(@Param("record") MmallPayInfo record, @Param("example") MmallPayInfoTemplate example);

    int updateByPrimaryKeySelective(MmallPayInfo record);

    int updateByPrimaryKey(MmallPayInfo record);
}