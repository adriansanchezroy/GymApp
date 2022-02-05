package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datacenter.Member;

public class MemberTest {

	private Member member;
	
	@Before
	public void setUp() {
		member = new Member("TEST MEMBER", "test@email.com", "5141234567", null, null, null, null);
	}
	
	@Test
	public void testDeletion() {
		member.deleteUser();
		assertTrue(member.isDeleted());
	}

}
