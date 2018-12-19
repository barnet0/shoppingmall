package com.shoppingmall.zsy.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.entity.MmallCategory;
import com.shoppingmall.zsy.mapper.MmallCategoryMapper;
import com.shoppingmall.zsy.service.api.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private MmallCategoryMapper mmallCategoryMapper;

    //添加内幕
    public ServerResponse<String> addCategory(String categoryName,Integer parentId){
        if (StringUtils.isBlank(categoryName)||parentId == null){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        MmallCategory category = new MmallCategory();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);
        int resultCount = mmallCategoryMapper.insertSelective(category);
        if (resultCount >0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
        if (categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        MmallCategory category = new MmallCategory();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount = mmallCategoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0){
            return ServerResponse.createBySuccessMessage("更新品名成功");
        }
        return ServerResponse.createByErrorMessage("更新品名失败");
    }


    public ServerResponse<List<MmallCategory>> getChildrenParallelCategory(Integer categoryId){
        List<MmallCategory> categoryList = mmallCategoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return
     */

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<MmallCategory> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryList = Lists.newArrayList();
        if (categoryId != null){
            for (MmallCategory categoryItem : categorySet){
                categoryList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryList);
    }



    private Set<MmallCategory> findChildCategory(Set<MmallCategory> categorySet,Integer categoryId){
        MmallCategory category = mmallCategoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        List<MmallCategory> categoryList = mmallCategoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (MmallCategory categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }


}
