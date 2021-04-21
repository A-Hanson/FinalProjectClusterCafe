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

class GroupMessageTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private GroupMessage groupMessage;

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
		groupMessage = em.find(GroupMessage.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		groupMessage = null;
	}

	@Test
	@DisplayName("test GroupMessage mapping")
	void test_1() {
//		mysql> select * from group_message;
//		+----+--------------------+------------+------------+---------------------+------------+---------+-------------------+------------------+
//		| id | title              | content    | creator_id | created_at          | updated_at | enabled | parent_message_id | cluster_group_id |
//		+----+--------------------+------------+------------+---------------------+------------+---------+-------------------+------------------+
//		|  1 | Test Group Message | Lalalalala |          1 | 2021-04-20 00:00:00 | NULL       |       1 |              NULL |                1 |
//		+----+--------------------+------------+------------+---------------------+------------+---------+-------------------+------------------+
		assertNotNull(groupMessage);
		assertEquals("Test Group Message", groupMessage.getTitle());
		assertNotNull(groupMessage.getCreator());
		assertEquals(4, groupMessage.getCreatedAt().getMonthValue());
		assertTrue(groupMessage.getEnabled());
		assertNotNull(groupMessage.getClusterGroup());
	}
	
	@Test
	@DisplayName("test GroupMessage to User mapping")
	void test_2() {
		assertNotNull(groupMessage.getCreator());
		assertEquals("test", groupMessage.getCreator().getUsername());
	}
	
	@Test
	@DisplayName("test GroupMessage to ClusterGroup mapping")
	void test_3() {
		assertNotNull(groupMessage.getClusterGroup());
		assertEquals("Aurora Runners", groupMessage.getClusterGroup().getName());
	}

}
