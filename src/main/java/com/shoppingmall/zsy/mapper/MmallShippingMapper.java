package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallShipping;
import com.shoppingmall.zsy.entity.MmallShippingTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallShippingMapper {
    int countByExample(MmallShippingTemplate example);

    int deleteByExample(MmallShippingTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallShipping record);

    int insertSelective(MmallShipping record);

    List<MmallShipping> selectByExample(MmallShippingTemplate example);

    MmallShipping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallShipping record, @Param("example") MmallShippingTemplate example);

    int updateByExample(@Param("record") MmallShipping record, @Param("example") MmallShippingTemplate example);

    int updateByPrimaryKeySelective(MmallShipping record);

    int updateByPrimaryKey(MmallShipping record);
}