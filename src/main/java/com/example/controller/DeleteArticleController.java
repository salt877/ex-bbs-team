package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/bbs")
@Transactional
public class DeleteArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	/**
	 * 記事を削除します.
	 * 
	 * @param form 記事フォーム
	 * @return 記事登録画面
	 */
	@RequestMapping(value = "/deletearticle")
	public String deletearticle(ArticleForm form) {
		articleRepository.delete(form.getId());
		return "redirect:/joinedbbs";
	}

}
