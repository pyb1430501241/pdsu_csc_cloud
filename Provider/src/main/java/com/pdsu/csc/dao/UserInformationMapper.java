package com.pdsu.csc.dao;

import com.pdsu.csc.bean.UserInformation;
import java.util.List;

import com.pdsu.csc.bean.UserInformationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationMapper {
    long countByExample(UserInformationExample example);

    int deleteByExample(UserInformationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInformation record);

    int insertSelective(UserInformation record);

    List<UserInformation> selectByExample(UserInformationExample example);

    UserInformation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserInformation record, @Param("example") UserInformationExample example);

    int updateByExample(@Param("record") UserInformation record, @Param("example") UserInformationExample example);

    int updateByPrimaryKeySelective(UserInformation record);

    int updateByPrimaryKey(UserInformation record);

	List<UserInformation> selectUsersByUids(@Param("uids") List<Integer> uids);

	List<UserInformation> selectUsersByUidsWithImage(List<Integer> uids);
	
	UserInformation selectUserByUid(Integer uid);
}