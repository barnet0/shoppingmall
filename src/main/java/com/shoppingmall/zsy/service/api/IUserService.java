package com.shoppingmall.zsy.service.api;

import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.entity.MmallUser;

public interface IUserService {

    ServerResponse<MmallUser> login(String userName, String passWord);

    ServerResponse<String> register(MmallUser user);

    ServerResponse<String> checkValid(String str,String type);

    public ServerResponse<String> selectQestionByUserName(String userName);

    public ServerResponse<String> checkAnswer(String userName,String question,String answer);

    public ServerResponse<String> forgetResetPassWord(String userName,String passWordNew,String forgetToken);

    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,MmallUser user);

    public ServerResponse<MmallUser> updateUserInfo(MmallUser user);

    public ServerResponse<MmallUser> getInformation(Integer id);

    public ServerResponse checkAdminRole(MmallUser user);
}
