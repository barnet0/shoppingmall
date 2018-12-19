package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallOrder;
import com.shoppingmall.zsy.entity.MmallOrderTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallOrderMapper {
    int countByExample(MmallOrderTemplate example);

    int deleteByExample(MmallOrderTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallOrder record);

    int insertSelective(MmallOrder record);

    List<MmallOrder> selectByExample(MmallOrderTemplate example);

    MmallOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallOrder record, @Param("example") MmallOrderTemplate example);

    int updateByExample(@Param("record") MmallOrder record, @Param("example") MmallOrderTemplate example);

    int updateByPrimaryKeySelective(MmallOrder record);

    int updateByPrimaryKey(MmallOrder record);
}