package com.newlecture.web.entity;

import java.util.Date;

public class NoticeView extends Notice {
	
	private int replyCount;

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public NoticeView() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeView(int id, String title, Date regDate, String writerId, String hit, String files,boolean pub, int replyCount) {
		super(id, title, regDate, writerId, hit, files, "", pub);
		this.replyCount = replyCount;
	}
	
}
