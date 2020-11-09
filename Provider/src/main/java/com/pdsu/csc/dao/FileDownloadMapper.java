package com.pdsu.csc.dao;

import java.util.List;

import com.pdsu.csc.bean.FileDownload;
import com.pdsu.csc.bean.FileDownloadExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDownloadMapper {
    long countByExample(FileDownloadExample example);

    int deleteByExample(FileDownloadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FileDownload record);

    int insertSelective(FileDownload record);

    List<FileDownload> selectByExample(FileDownloadExample example);

    FileDownload selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FileDownload record, @Param("example") FileDownloadExample example);

    int updateByExample(@Param("record") FileDownload record, @Param("example") FileDownloadExample example);

    int updateByPrimaryKeySelective(FileDownload record);

    int updateByPrimaryKey(FileDownload record);
}