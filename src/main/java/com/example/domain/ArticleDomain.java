package com.example.domain;

import java.util.List;

/**
 * 投稿情報を表すドメイン.
 * 
 * @author sota_adachi
 *
 */
public class ArticleDomain {
	/**
	 * 記事ID
	 */
	private Integer id;
	/**
	 * 投稿者名
	 */
	private String name;
	/**
	 * 記事
	 */
	private String content;
	/**
	 * コメントリスト
	 */
	private List<CommentDomain> commentList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;

	}
	
	public List<CommentDomain> getCommentList() {
		return commentList;
	}
	
	public void setCommentList(List<CommentDomain> commentList) {
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return "ArticleDomain [id=" + id + ", name=" + name + ", content=" + content + ", commentList=" + commentList
				+ "]";
	}
}
