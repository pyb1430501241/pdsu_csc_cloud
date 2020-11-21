package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 文件下载数据
 * @author 半梦
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDownload implements Serializable {
    private Integer id;

    private Integer fileid;

    private Integer bid;

    private Integer uid;


	public FileDownload(Integer fileid, Integer bid, Integer uid) {
		super();
		this.fileid = fileid;
		this.bid = bid;
		this.uid = uid;
	}

}