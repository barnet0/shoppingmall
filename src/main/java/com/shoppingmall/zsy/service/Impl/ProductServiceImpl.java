package com.shoppingmall.zsy.service.Impl;

import com.shoppingmall.zsy.VO.ProductDetailVo;
import com.shoppingmall.zsy.common.ResponseCode;
import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.common.util.DateTimeUtil;
import com.shoppingmall.zsy.entity.MmallCategory;
import com.shoppingmall.zsy.entity.MmallProduct;
import com.shoppingmall.zsy.entity.MmallProductWithBLOBs;
import com.shoppingmall.zsy.mapper.MmallCategoryMapper;
import com.shoppingmall.zsy.mapper.MmallProductMapper;
import com.shoppingmall.zsy.service.api.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private MmallProductMapper mmallProductMapper;
    @Autowired
    private MmallCategoryMapper mmallCategoryMapper;

    public ServerResponse<String> saveOrUpdateProduct(MmallProductWithBLOBs product){
        if (product == null){
            return ServerResponse.createByErrorMessage("参数传入出错");
        }
        if (StringUtils.isNotBlank(product.getSubImages())){
            String[] subImgageArray = product.getSubImages().split(";");
            if (subImgageArray.length > 0){
                product.setMainImage(subImgageArray[0]);
            }
        }
        if (product.getId() != null){
            int rowCount = mmallProductMapper.updateByPrimaryKeyWithBLOBs(product);
            if (rowCount > 0){
                return ServerResponse.createBySuccessMessage("更新商品成功");
            }
            return ServerResponse.createByErrorMessage("更新商品失败");
        }else {
            int rowCount = mmallProductMapper.insert(product);
            if (rowCount > 0){
                return ServerResponse.createBySuccessMessage("商品新增成功");
            }
            return ServerResponse.createByErrorMessage("商品新增失败");
        }
    }

    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if (productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        MmallProductWithBLOBs product = new MmallProductWithBLOBs();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = mmallProductMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0){
            return ServerResponse.createBySuccessMessage("更新商品状态成功");
        }
        return ServerResponse.createByErrorMessage("更新商品状态失败");
    }

    public ServerResponse<ProductDetailVo> mangeProductDetail(Integer productId){
        if (productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        MmallProductWithBLOBs product = mmallProductMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createByErrorMessage("商品下架或者已经移除");
        }
        ProductDetailVo productDetailVo = assemableProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assemableProductDetailVo(MmallProductWithBLOBs product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());




        MmallCategory categoryName = mmallCategoryMapper.selectByPrimaryKey(product.getId());
        if (categoryName != null){
            productDetailVo.setParentCategoryId(categoryName.getParentId());
        }else{
            productDetailVo.setParentCategoryId(0);//默认放到根节点
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;
    }

}
