package com.shoppingmall.zsy.controller.backend;

import com.shoppingmall.zsy.common.Const;
import com.shoppingmall.zsy.common.ResponseCode;
import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.entity.MmallProductWithBLOBs;
import com.shoppingmall.zsy.entity.MmallUser;
import com.shoppingmall.zsy.service.api.IProductService;
import com.shoppingmall.zsy.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, MmallProductWithBLOBs product){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户尚未登录,请登录管理员账号");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    public ServerResponse set_product_status(HttpSession session,Integer productId,Integer status){
        MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户尚未登录,请登录管理员账号");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
