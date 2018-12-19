package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallCart;
import com.shoppingmall.zsy.entity.MmallCartTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallCartMapper {
    int countByExample(MmallCartTemplate example);

    int deleteByExample(MmallCartTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallCart record);

    int insertSelective(MmallCart record);

    List<MmallCart> selectByExample(MmallCartTemplate example);

    MmallCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallCart record, @Param("example") MmallCartTemplate example);

    int updateByExample(@Param("record") MmallCart record, @Param("example") MmallCartTemplate example);

    int updateByPrimaryKeySelective(MmallCart record);

    int updateByPrimaryKey(MmallCart record);
}