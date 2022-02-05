package test;

import static org.junit.Assert.*;

import java.time.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import datacenter.*;

public class ServiceTest {

	private Service service;
	private Professional professional;
	private Member member;
	private Registration registration;

	@Before
	public void setUp() {
		this.professional = new Professional("TEST PROF", "test@email.com", "5141234567", null, null, null, null);
		this.member = new Member("TEST MEMBER", "test2@email.com", "5141234567", null, null, null, null);

		ArrayList<DayOfWeek> serviceRecurrence = new ArrayList<>();
		for (Integer i = 1; i < 8; i++) {
			serviceRecurrence.add(DayOfWeek.of(i));
		}
		service = new Service(LocalDate.now(), LocalDate.now().minusWeeks(2), LocalDate.now().plusWeeks(2),
				serviceRecurrence, "TEST SERVICE", professional, "", 0, 0,"15:00");

		this.registration = new Registration(LocalDateTime.now(), this.member, this.professional,
				this.service.getTodaysSession(), "");
		service.getTodaysSession().addRegistration(this.registration);
	}

	@Test
	public void testSessionsListSize() {
		assertTrue(!this.service.getSessions().isEmpty());
	}

	@Test
	public void testHasSessionToday() {
		assertTrue(this.service.hasSessionToday());
	}

	@Test
	public void testGetTodaysSession() {
		assertEquals(this.service.getTodaysSession().getDate(), LocalDate.now());
	}

	@Test
	public void testAddRegistration() {
		this.service.getTodaysSession().addRegistration(this.registration);
		assertEquals(this.service.getTodaysSession().getRegistrationForMember(this.member), this.registration);
	}

	@Test
	public void testRegistrationConfirmation() {
		this.registration.confirmation();
		assertTrue(this.service.getTodaysSession().getRegistrationForMember(this.member).isConfirmed());
	}

}
