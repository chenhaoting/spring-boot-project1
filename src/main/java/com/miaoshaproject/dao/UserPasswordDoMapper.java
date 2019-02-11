package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.UserPasswordDo;

public interface UserPasswordDoMapper {

    int deleteByPrimaryKey(Integer id);


    int insert(UserPasswordDo record);


    int insertSelective(UserPasswordDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated
     */
    UserPasswordDo selectByPrimaryKey(Integer id);
    UserPasswordDo selectByUserId(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserPasswordDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserPasswordDo record);
}