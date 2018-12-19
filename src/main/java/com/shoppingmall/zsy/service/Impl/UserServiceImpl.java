package com.shoppingmall.zsy.service.Impl;

import com.shoppingmall.zsy.common.Const;
import com.shoppingmall.zsy.common.ServerResponse;
import com.shoppingmall.zsy.common.TokenCache;
import com.shoppingmall.zsy.common.util.MD5Util;
import com.shoppingmall.zsy.entity.MmallUser;
import com.shoppingmall.zsy.mapper.MmallUserMapper;
import com.shoppingmall.zsy.service.api.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("IUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private MmallUserMapper mmallUserMapper;

    @Override
    public ServerResponse<MmallUser> login(String userName, String passWord) {
        int resultCount = mmallUserMapper.checkUserName(userName);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //密码登录md5
        passWord = MD5Util.MD5EncodeUtf8(passWord);
        MmallUser user = mmallUserMapper.selectLogin(userName,passWord);
        if (user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(MmallUser user) {

        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!validResponse.isSuccess()){
            //检查姓名
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = mmallUserMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)){
            if (Const.USERNAME.equals(type)){
                int resultCount = mmallUserMapper.checkUserName(str);
                if (resultCount > 0) {
                    return ServerResponse.createBySuccessMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = mmallUserMapper.checkEmail(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("检验成功");
    }

    public ServerResponse<String> selectQestionByUserName(String userName){
        ServerResponse validResponse = this.checkValid(userName,Const.USERNAME);
        if (validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = mmallUserMapper.selectQuestionByUserName(userName);
        if (StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题为空");
    }

    public ServerResponse<String> checkAnswer(String userName,String question,String answer){
        int resultCount = mmallUserMapper.checkAnswer(userName,question,answer);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("问题回答错误");
        }
        String forgetToken = UUID.randomUUID().toString();
        TokenCache.setKey(TokenCache.TOKEN_PREFIX+userName,forgetToken);
        return ServerResponse.createBySuccess(forgetToken);
    }

    public ServerResponse<String> forgetResetPassWord(String userName,String passWordNew,String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }
        ServerResponse validResponse = this.checkValid(userName,Const.USERNAME);
        if (validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+userName);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(forgetToken,token)){
            String md5PassWord = MD5Util.MD5EncodeUtf8(passWordNew);
            int reaultCount = mmallUserMapper.updatePassWordByUserName(userName,md5PassWord);
            if(reaultCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,MmallUser user){
        int resultCount = mmallUserMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (resultCount > 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int count = mmallUserMapper.updateByPrimaryKey(user);
        if (count > 0){
            return ServerResponse.createBySuccessMessage("更新密码成功");
        }
        return ServerResponse.createByErrorMessage("更新密码失败");
    }

    public ServerResponse<MmallUser> updateUserInfo(MmallUser user){
        //username不可以更新
        //email也需要验证,检验新的email是否为新的
        int resultCount = mmallUserMapper.checkEmailByUserid(user.getEmail(),user.getId());
        if (resultCount > 0){
            return ServerResponse.createByErrorMessage("email已经存在,请尝试更新email在尝试更新");
        }
        MmallUser userUpdate = new MmallUser();
        userUpdate.setEmail(user.getEmail());
        userUpdate.setAnswer(user.getAnswer());
        userUpdate.setQuestion(user.getQuestion());
        userUpdate.setId(user.getId());
        userUpdate.setPhone(user.getPhone());
        int updateCount = mmallUserMapper.updateByPrimaryKeySelective(userUpdate);
        if (updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",userUpdate);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    public ServerResponse<MmallUser> getInformation(Integer id){
        MmallUser user = mmallUserMapper.selectByPrimaryKey(id);
        if (user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    //backend

    /**
     * 判断是否为管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(MmallUser user){
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

}
