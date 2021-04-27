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

class MeetingTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Meeting meeting;

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
		meeting = em.find(Meeting.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		meeting = null;
	}

	@Test
	@DisplayName("Test Meeting mapping")
	void test_1() {
		assertNotNull(meeting);
		assertEquals("Jogging", meeting.getName());
	}
	
	@Test
	@DisplayName("Test Meeting to User(Attendees) mapping")
	void test_2() {
		assertNotNull(meeting);
		assertNotNull(meeting.getAttendees());
		assertTrue(meeting.getAttendees().size() > 0);
	}


}
