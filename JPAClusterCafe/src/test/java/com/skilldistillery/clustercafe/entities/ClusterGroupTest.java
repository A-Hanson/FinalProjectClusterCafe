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

class ClusterGroupTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private ClusterGroup clusterGroup;

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
		clusterGroup = em.find(ClusterGroup.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		clusterGroup = null;
	}

	@Test
	@DisplayName("Test ClusterGroup Mapping")
	void test_1() {
//		mysql> select * from cluster_group;
//		+----+----------------+-------------------------+---------------------+---------+---------+--------------+
//		| id | name           | description             | created_at          | enabled | img_url | moderator_id |
//		+----+----------------+-------------------------+---------------------+---------+---------+--------------+
//		|  1 | Aurora Runners | Low-key casual running! | 2021-04-20 00:00:00 |       1 | NULL    |            1 |
//		+----+----------------+-------------------------+---------------------+---------+---------+--------------+
		assertNotNull(clusterGroup);
		assertEquals("Aurora Runners", clusterGroup.getName());
		assertEquals("Low-key casual running!", clusterGroup.getDescription());
		assertEquals(4, clusterGroup.getCreatedAt().getMonthValue());
		assertTrue(clusterGroup.getEnabled());
		assertNotNull(clusterGroup.getModerator());
	}
	@Test
	@DisplayName("Test User cluster group Join table")
	void test_3() {
//		mysql> select store.name from user join store on user.store_id = store.id WHERE user.id=1;
//		+--------------------------+
//		| name                     |
//		+--------------------------+
//		| Volcano Tea House Aurora |
		assertNotNull(clusterGroup);
		assertNotNull(clusterGroup.getUsers());
		assertTrue(clusterGroup.getUsers().size() > 0);

	}
}
