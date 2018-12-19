package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallProduct;
import com.shoppingmall.zsy.entity.MmallProductTemplate;
import com.shoppingmall.zsy.entity.MmallProductWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallProductMapper {
    int countByExample(MmallProductTemplate example);

    int deleteByExample(MmallProductTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallProductWithBLOBs record);

    int insertSelective(MmallProductWithBLOBs record);

    List<MmallProductWithBLOBs> selectByExampleWithBLOBs(MmallProductTemplate example);

    List<MmallProduct> selectByExample(MmallProductTemplate example);

    MmallProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallProductWithBLOBs record, @Param("example") MmallProductTemplate example);

    int updateByExampleWithBLOBs(@Param("record") MmallProductWithBLOBs record, @Param("example") MmallProductTemplate example);

    int updateByExample(@Param("record") MmallProduct record, @Param("example") MmallProductTemplate example);

    int updateByPrimaryKeySelective(MmallProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MmallProductWithBLOBs record);

    int updateByPrimaryKey(MmallProduct record);
}