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

class UserTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private User user;

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
		user = em.find(User.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user = null;
	}

	@Test
	@DisplayName("Test User mapping")
	void test_1() {
//mysql> mysql> select * from user;
//+----+----------+----------+---------------+------------+-----------+---------+----------+------------+---------+----------+--------+---------------------+----------+
//| id | username | password | email         | first_name | last_name | img_url | pronouns | dob        | enabled | role     | gender | created_at          | store_id |
//+----+----------+----------+---------------+------------+-----------+---------+----------+------------+---------+----------+--------+---------------------+----------+
//|  1 | test     | test     | test@test.com | Thor       | Bird      | NULL    | He / His | 2007-05-15 |       1 | standard | male   | 2021-04-20 00:00:00 |        1 |
//+----+----------+----------+---------------+------------+-----------+---------+----------+------------+---------+----------+--------+---------------------+----------+
		assertNotNull(user);
		assertEquals("test", user.getUsername());
		assertEquals(2007, user.getDob().getYear());
		assertTrue(user.getEnabled());
	}
	
	@Test
	@DisplayName("Test User to Store mapping")
	void test_2() {
//		mysql> select store.name from user join store on user.store_id = store.id WHERE user.id=1;
//		+--------------------------+
//		| name                     |
//		+--------------------------+
//		| Volcano Tea House Aurora |
		assertNotNull(user);
		assertNotNull(user.getStore());
		assertEquals("Volcano Tea House Aurora", user.getStore().getName());

	}
	@Test
	@DisplayName("Test User cluster group Join table")
	void test_3() {
//		mysql> select store.name from user join store on user.store_id = store.id WHERE user.id=1;
//		+--------------------------+
//		| name                     |
//		+--------------------------+
//		| Volcano Tea House Aurora |
		assertNotNull(user);
		assertNotNull(user.getClusterGroups());
		assertTrue(user.getClusterGroups().size() > 0);

	}

}
