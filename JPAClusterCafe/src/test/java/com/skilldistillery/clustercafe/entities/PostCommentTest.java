package com.skilldistillery.clustercafe.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostCommentTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private PostComment postComment;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("ClusterCafePU");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		postComment = em.find(PostComment.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		postComment = null;
	}

	@Test
	@DisplayName("Test PostComment Mapping")
	void test_1() {
//		mysql> select * from post_comment where id=1;
//		+----+------------------+---------+---------+---------+---------+---------------------+------------+
//		| id | content          | post_id | user_id | enabled | flagged | created_at          | updated_at |
//		+----+------------------+---------+---------+---------+---------+---------------------+------------+
//		|  1 | No silly people. |       1 |       1 |       1 |       0 | 2021-04-20 00:00:00 | NULL       |
//		+----+------------------+---------+---------+---------+---------+---------------------+------------+
		assertNotNull(postComment);
		assertEquals("No silly people.", postComment.getContent());
		assertEquals(20, postComment.getCreatedAt().getDayOfMonth());
	}
	
	@Test
	@DisplayName("Test PostComment to User Mapping")
	void test_2() {
//		mysql> select user.username from post_comment JOIN user on post_comment.user_id = user.id WHERE post_comment.id=1;
//		+----------+
//		| username |
//		+----------+
//		| test     |
//		+----------+
		assertNotNull(postComment);
		assertNotNull(postComment.getUser());
		assertEquals("test", postComment.getUser().getUsername());
	}
	
	@Test
	@DisplayName("Test PostComment to Post Mapping")
	void test_3() {
//		mysql> select post.content from post_comment JOIN post ON post_comment.post_id = post.id WHERE post_comment.id=1;
//		+-----------------------------------------------------------------+
//		| content                                                         |
//		+-----------------------------------------------------------------+
//		| Entry level runner seeks other entry level runners to run with. |
//		+-----------------------------------------------------------------+
		assertNotNull(postComment);
		assertNotNull(postComment.getPost());
		assertEquals("Entry level runner seeks other entry level runners to run with.", postComment.getPost().getContent());
	}

}
