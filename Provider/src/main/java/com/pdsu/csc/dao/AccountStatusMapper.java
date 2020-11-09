package com.pdsu.csc.dao;

import com.pdsu.csc.bean.AccountStatus;
import java.util.List;

import com.pdsu.csc.bean.AccountStatusExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusMapper {
    long countByExample(AccountStatusExample example);

    int deleteByExample(AccountStatusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountStatus record);

    int insertSelective(AccountStatus record);

    List<AccountStatus> selectByExample(AccountStatusExample example);

    AccountStatus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountStatus record, @Param("example") AccountStatusExample example);

    int updateByExample(@Param("record") AccountStatus record, @Param("example") AccountStatusExample example);

    int updateByPrimaryKeySelective(AccountStatus record);

    int updateByPrimaryKey(AccountStatus record);
}