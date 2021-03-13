package com.pdsu.csc.impl;

import com.pdsu.csc.bean.*;
import com.pdsu.csc.dao.WebCommentMapper;
import com.pdsu.csc.dao.WebCommentReplyMapper;
import com.pdsu.csc.dao.WebInformationMapper;
import com.pdsu.csc.exception.web.blob.NotFoundBlobIdException;
import com.pdsu.csc.exception.web.blob.comment.NotFoundCommentIdException;
import com.pdsu.csc.service.CommentService;
import com.pdsu.csc.service.WebCommentReplyService;
import com.pdsu.csc.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author 半梦
 *
 */
@Service("webCommentReplyService")
public class WebCommentReplyServiceImpl implements
		WebCommentReplyService, WebService, CommentService {

	@Autowired
	private WebCommentReplyMapper webCommentReplyMapper;
	
	@Autowired
	private WebInformationMapper webInformationMapper;
	
	@Autowired
	private WebCommentMapper webCommentMapper;
	
	@Override
	public boolean insert(@NonNull WebCommentReply webCommentReply) throws NotFoundBlobIdException, NotFoundCommentIdException {
		if(!isExistByWebId(webCommentReply.getWid())) {
			throw new NotFoundBlobIdException();
		}
		if(!isExistByCommentId(webCommentReply.getCid())) {
			throw new NotFoundCommentIdException();
		}
		return webCommentReplyMapper.insertSelective(webCommentReply) > 0;
	}

	@Override
	public boolean isExistByCommentId(@NonNull Integer cid) {
		WebCommentExample example = new WebCommentExample();
		com.pdsu.csc.bean.WebCommentExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(cid);
		return webCommentMapper.countByExample(example) > 0;
	}

	@Override
	public List<WebCommentReply> selectCommentReplysByWebComments(@NonNull List<WebComment> commentList) {
		if(commentList.size() == 0) {
			return Collections.emptyList();
		}
		List<Integer> commentid = new ArrayList<>();
		for(WebComment w : commentList) {
			commentid.add(w.getId());
		}
		WebCommentReplyExample example = new WebCommentReplyExample();
		WebCommentReplyExample.Criteria criteria = example.createCriteria();
		criteria.andCidIn(commentid);
		return selectListByExample(example);
	}
	
	@Override
	public List<WebCommentReply> selectCommentReplysByWebId(@NonNull Integer webid) throws NotFoundBlobIdException {
		if(!isExistByWebId(webid)) {
			throw new NotFoundBlobIdException();
		}
		WebCommentReplyExample example = new WebCommentReplyExample();
		WebCommentReplyExample.Criteria criteria = example.createCriteria();
		criteria.andWidEqualTo(webid);
		return selectListByExample(example);
	}

	@Override
	public Integer countByWebsAndUid(@NonNull List<Integer> webs) {
		if(webs.size() == 0) {
			return 0;
		}
		WebCommentReplyExample example = new WebCommentReplyExample();
		WebCommentReplyExample.Criteria criteria = example.createCriteria();
		criteria.andWidIn(webs);
		return (int) countByExample(example);
	}

	@Override
	public boolean deleteByExample(@Nullable WebCommentReplyExample example) {
		return webCommentReplyMapper.deleteByExample(example) > 0;
	}

	@Override
	public boolean updateByExample(@NonNull WebCommentReply webCommentReply, @Nullable WebCommentReplyExample example) {
		return webCommentReplyMapper.updateByExampleSelective(webCommentReply, example) > 0;
	}

	@Override
	@NonNull
	public List<WebCommentReply> selectListByExample(@Nullable WebCommentReplyExample example) {
		return webCommentReplyMapper.selectByExample(example);
	}

	@Override
	public long countByExample(WebCommentReplyExample example) {
		return webCommentReplyMapper.countByExample(example);
	}

	@Override
	public boolean isExistByWebId(@NonNull Integer webId) {
		WebInformationExample example = new WebInformationExample();
		com.pdsu.csc.bean.WebInformationExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(webId);
		return webInformationMapper.countByExample(example) > 0;
	}

}
