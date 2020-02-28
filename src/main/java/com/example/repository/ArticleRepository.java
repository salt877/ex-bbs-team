package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.ArticleDomain;
import com.example.demo.domain.CommentDomain;

/**
 * articlesテーブルを操作するリポジトリ.
 * 
 * @author sota_adachi
 *
 */
@Repository
public class ArticleRepository {
	/**
	 * ArticleDomainオブジェクトを生成するローマッパー.
	 */
	public static final RowMapper<ArticleDomain> ARTICLE_ROW_MAPPER = (rs, i) -> {
		ArticleDomain articleDomain = new ArticleDomain();
		articleDomain.setId(rs.getInt("id"));
		articleDomain.setName(rs.getString("name"));
		articleDomain.setContent(rs.getString("content"));
		return articleDomain;
	};

	/**
	 * ArticleDomainオブジェクトリストを生成するリザルトセットエクストラクター.
	 */
	public static final ResultSetExtractor<List<ArticleDomain>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {
		List<ArticleDomain> articleList = new ArrayList<>();
		List<CommentDomain> commentList = null;
		ArticleDomain articleDomain = null;
		int beforeId = 0;
		while (rs.next()) {
			int nowArticleId = rs.getInt("id");
			if (nowArticleId != beforeId) {
				articleDomain = new ArticleDomain();
				articleDomain.setId(nowArticleId);
				articleDomain.setName(rs.getString("name"));
				articleDomain.setContent(rs.getString("content"));
				commentList = new ArrayList<>();
				articleDomain.setCommentList(commentList);
				
				articleList.add(articleDomain);
			}
			if (rs.getInt("com_id") != 0) {
				System.out.println(rs.getInt("com_id"));
				CommentDomain commentDomain = new CommentDomain();
				commentDomain.setId(rs.getInt("com_id"));
				commentDomain.setName(rs.getString("com_name"));
				commentDomain.setContent(rs.getString("com_content"));
				commentDomain.setArticleId(rs.getInt("article_id"));
				commentList.add(commentDomain);
			}
			beforeId = nowArticleId;
		}
		return articleList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 記事を投稿順に一覧で表示します.
	 * 
	 * @return 記事の一覧
	 */
	public List<ArticleDomain> findAll() {
		String sql = "SELECT id, name, content FROM articles ORDER BY id DESC";
		List<ArticleDomain> developmentList = template.query(sql, ARTICLE_ROW_MAPPER);
		return developmentList;
	}

	/**
	 * 記事を投稿します.
	 * 
	 * @param articleDomain 投稿情報
	 */
	public void insert(ArticleDomain articleDomain) {
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		SqlParameterSource source = new BeanPropertySqlParameterSource(articleDomain);
		template.update(sql, source);
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param id
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id = :id";
		SqlParameterSource source = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, source);
	}

	public List<ArticleDomain> findAllArticlesAndComments() {
		String sql = "SELECT a.id, a.name, a.content, c.id com_id, c.name com_name, c.content com_content, article_id FROM articles a FULL OUTER JOIN comments c ON a.id = c.article_id ORDER BY a.id, com_id";
		List<ArticleDomain> developmentList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
		return developmentList;
	}
}
