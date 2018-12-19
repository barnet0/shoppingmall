package com.shoppingmall.zsy.service.api;

import com.shoppingmall.zsy.VO.ProductDetailVo;
import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.entity.MmallProductWithBLOBs;

public interface IProductService {
    public ServerResponse<String> saveOrUpdateProduct(MmallProductWithBLOBs product);
    public ServerResponse<String> setSaleStatus(Integer productId,Integer status);
    public ServerResponse<ProductDetailVo> mangeProductDetail(Integer productId);

}
