package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallCategory;
import com.shoppingmall.zsy.entity.MmallCategoryTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MmallCategoryMapper {
    int countByExample(MmallCategoryTemplate example);

    int deleteByExample(MmallCategoryTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallCategory record);

    int insertSelective(MmallCategory record);

    List<MmallCategory> selectByExample(MmallCategoryTemplate example);

    MmallCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallCategory record, @Param("example") MmallCategoryTemplate example);

    int updateByExample(@Param("record") MmallCategory record, @Param("example") MmallCategoryTemplate example);

    int updateByPrimaryKeySelective(MmallCategory record);

    int updateByPrimaryKey(MmallCategory record);

    List<MmallCategory> selectCategoryChildrenByParentId(Integer parentId);
}