package com.pdsu.csc.service.impl;

import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.WebCommentMapper;
import com.pdsu.csc.dao.WebCommentReplyMapper;
import com.pdsu.csc.dao.WebInformationMapper;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.blob.comment.NotFoundCommentIdException;
import com.pdsu.csc.service.WebCommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author 半梦
 *
 */
@Service("webCommentReplyService")
public class WebCommentReplyServiceImpl implements WebCommentReplyService {

	@Autowired
	private WebCommentReplyMapper webCommentReplyMapper;
	
	@Autowired
	private WebInformationMapper webInformationMapper;
	
	@Autowired
	private WebCommentMapper webCommentMapper;
	
	@Override
	public boolean insert(@NonNull WebCommentReply webCommentReply) throws NotFoundBlobIdException, NotFoundCommentIdException {
		if(!countByWebid(webCommentReply.getWid())) {
			throw new NotFoundBlobIdException();
		}
		if(!countByCid(webCommentReply.getCid())) {
			throw new NotFoundCommentIdException();
		}
		return webCommentReplyMapper.insertSelective(webCommentReply) != 0;
	}

	@Override
	public boolean countByWebid(@NonNull Integer webid) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webid);
		return webInformationMapper.countByExample(example) != 0;
	}

	@Override
	public boolean countByCid(@NonNull Integer cid) {
		WebCommentExample example = new WebCommentExample();
		com.pdsu.csc.bean.WebCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(cid);
		return webCommentMapper.countByExample(example) != 0;
	}

	@Override
	public List<WebCommentReply> selectCommentReplysByWebComments(@NonNull List<WebComment> commentList) {
		if(commentList.size() == 0) {
			return new ArrayList<>();
		}
		List<Integer> commentid = new ArrayList<>();
		for(WebComment w : commentList) {
			commentid.add(w.getId());
		}
		WebCommentReplyExample example = new WebCommentReplyExample();
		WebCommentReplyExample.Criteria criteria = example.createCriteria();
		criteria.andCidIn(commentid);
		return webCommentReplyMapper.selectByExample(example);
	}
	
	@Override
	public List<WebCommentReply> selectCommentReplysByWebId(@NonNull Integer webid) throws NotFoundBlobIdException {
		if(!countByWebid(webid)) {
			throw new NotFoundBlobIdException();
		}
		WebCommentReplyExample example = new WebCommentReplyExample();
		WebCommentReplyExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return webCommentReplyMapper.selectByExample(example);
	}

	@Override
	public Integer countByWebsAndUid(@NonNull List<Integer> webs) {
		if(Objects.isNull(webs) || webs.size() == 0) {
			return 0;
		}
		WebCommentReplyExample example = new WebCommentReplyExample();
		WebCommentReplyExample.Criteria criteria = example.createCriteria();
		criteria.andWidIn(webs);
		return (int) webCommentReplyMapper.countByExample(example);
	}
	
}
