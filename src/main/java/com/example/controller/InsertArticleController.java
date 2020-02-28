package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.ArticleDomain;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;


@Controller
public class InsertArticleController {

	@Autowired
	 private ArticleRepository articleRepository;
	
	@Autowired
	private ShowBbsController showBbsController;
	
	/**
	 * 記事を投稿します.
	 * 
	 * @param form
	 *            フォーム
	 * @param result
	 *            リザルト
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@RequestMapping(value = "/postarticle")
	public String postarticle(@Validated ArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showBbsController.form(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:/joinedbbs";
	}


	
}
