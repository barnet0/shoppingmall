package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallOrderItem;
import com.shoppingmall.zsy.entity.MmallOrderItemTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallOrderItemMapper {
    int countByExample(MmallOrderItemTemplate example);

    int deleteByExample(MmallOrderItemTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallOrderItem record);

    int insertSelective(MmallOrderItem record);

    List<MmallOrderItem> selectByExample(MmallOrderItemTemplate example);

    MmallOrderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallOrderItem record, @Param("example") MmallOrderItemTemplate example);

    int updateByExample(@Param("record") MmallOrderItem record, @Param("example") MmallOrderItemTemplate example);

    int updateByPrimaryKeySelective(MmallOrderItem record);

    int updateByPrimaryKey(MmallOrderItem record);
}