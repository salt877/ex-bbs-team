package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.CommentForm;
import com.example.repository.CommentRepository;

import jp.co.runy.bbs.joined.domain.JoinedComment;
import jp.co.runy.bbs.joined.form.JoinedCommentForm;

@Controller
public class InsertCommentController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ShowBbsController showBbsController;

	/**
	 * コメントを投稿します.
	 * 
	 * @param form   フォーム
	 * @param result リザルト
	 * @param model  モデル
	 * @return 掲示板画面
	 */
	@RequestMapping(value = "/postcomment")
	public String postcomment(@Validated CommentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showBbsController.form(model);
		}
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		commentRepository.insert(comment);
		return "redirect:/joinedbbs";
	}

}
