package com.shoppingmall.zsy.service.api;

import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.entity.MmallCategory;

import java.util.List;

public interface ICategoryService {

    public ServerResponse<String> addCategory(String categoryName, Integer parentId);
    public ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    public ServerResponse<List<MmallCategory>> getChildrenParallelCategory(Integer categoryId);
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
