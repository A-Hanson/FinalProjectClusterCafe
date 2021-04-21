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

class PostTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Post post;

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
		post = em.find(Post.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		post = null;
	}

	@Test
	@DisplayName("Test Post mapping")
	void test_1() {
//		mysql> select * from post where id=1;
//		+----+---------------------------+-----------------------------------------------------------------+---------------------+------------+---------+---------+---------+-------------+
//		| id | title                     | content                                                         | created_at          | updated_at | enabled | flagged | user_id | category_id |
//		+----+---------------------------+-----------------------------------------------------------------+---------------------+------------+---------+---------+---------+-------------+
//		|  1 | Looking for other runners | Entry level runner seeks other entry level runners to run with. | 2021-04-20 00:00:00 | NULL       |       1 |       0 |       1 |           1 |
//		+----+---------------------------+-----------------------------------------------------------------+---------------------+------------+---------+---------+---------+-------------+		
		assertNotNull(post);
		assertEquals("Looking for other runners", post.getTitle());
		assertEquals(20, post.getCreatedAt().getDayOfMonth());
		assertTrue(post.getEnabled());
		assertFalse(post.getFlagged());
	}
	
	@Test
	@DisplayName("Test Post to User mapping")
	void test_2() {
//		mysql> select user.username from post JOIN user ON post.user_id = user.id WHERE post.id=1;
//		+----------+
//		| username |
//		+----------+
//		| test     |
//		+----------+	
		assertNotNull(post);
		assertNotNull(post.getUser());
		assertEquals("test", post.getUser().getUsername());

	}
	
	@Test
	@DisplayName("Test Post to Category mapping")
	void test_3() {
//		mysql> select category.name from post JOIN category ON post.category_id = category.id WHERE post.id=1;
//		+---------+
//		| name    |
//		+---------+
//		| Running |
//		+---------+
		assertNotNull(post);
		assertNotNull(post.getCategory());
		assertEquals("Running", post.getCategory().getName());
		
	}

}
