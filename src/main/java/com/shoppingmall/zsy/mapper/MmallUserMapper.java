package com.shoppingmall.zsy.mapper;

import com.shoppingmall.zsy.entity.MmallUser;
import com.shoppingmall.zsy.entity.MmallUserTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface MmallUserMapper {
    int countByExample(MmallUserTemplate example);

    int deleteByExample(MmallUserTemplate example);

    int deleteByPrimaryKey(Integer id);

    int insert(MmallUser record);

    int insertSelective(MmallUser record);

    List<MmallUser> selectByExample(MmallUserTemplate example);

    MmallUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MmallUser record, @Param("example") MmallUserTemplate example);

    int updateByExample(@Param("record") MmallUser record, @Param("example") MmallUserTemplate example);

    int updateByPrimaryKeySelective(MmallUser record);

    int updateByPrimaryKey(MmallUser record);

    int checkUserName(String userName);

    MmallUser selectLogin(@Param("userName")String userName,@Param("passWord")String passWord);

    int checkEmail(String email);

    String selectQuestionByUserName(String userName);

    int checkAnswer(@Param("username") String userName,@Param("question") String question, @Param("answer") String answer);

    int updatePassWordByUserName(@Param("userName") String userName,@Param("passWord") String passWordNew);

    int checkPassword(String password, Integer id);

    int checkEmailByUserid(@Param("email") String email, @Param("id") Integer id);
}