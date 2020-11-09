package com.pdsu.csc.bean;

import java.io.Serializable;

/**
 * 文章标签对照
 * @author Admin
 *
 */
public class WebLabelControl implements Serializable {
    private Integer id;

    private Integer wid;

    private Integer lid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

	@Override
	public String toString() {
		return "WebLabelControl [id=" + id + ", wid=" + wid + ", lid=" + lid + "]";
	}

	public WebLabelControl(Integer wid, Integer lid) {
		super();
		this.wid = wid;
		this.lid = lid;
	}
    
}