/**
 * 
 */
package de.hrw.swep.verein.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import de.hrw.swep.verein.business.Member;
import de.hrw.swep.verein.persistence.DataStore;

/**
 * @author andriesc
 *
 */
public class MemberServiceImplIntegrationTest {
	private final String connectionString = "jdbc:hsqldb:file:../vereinsverwaltung.db-layer/database/clubdb";
	private final String dbUser = "sa";
	private final String dbPassword = "";
	private IDatabaseTester databaseTester;

	private MemberServiceInterface memberService;

	@Before
	public void setUp() throws Exception {
		DataStore db = new DataStore();

		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", connectionString, dbUser, dbPassword);
		databaseTester
				.setDataSet(new FlatXmlDataSetBuilder().build(new File("../vereinsverwaltung.db-layer/full.xml")));
		databaseTester.onSetup();

		memberService = new MemberServiceImpl(db, db);
	}

	@Test
	public void testGetAllMembers() {
		List<Member> allMembers = memberService.getAllMembers();
		assertEquals(5, allMembers.size());

		// Alle Mitglieder nach ID im Array ablegen
		Member[] allMembersArray = new Member[5];
		for (Member member : allMembers) {
			allMembersArray[member.getID() - 1] = member;
		}

		assertEquals("Moritz", allMembersArray[0].getFirstName());
		assertEquals("Schmidt", allMembersArray[1].getLastName());
		assertEquals(1050, allMembersArray[2].getPointsForRanking());
		assertEquals(2008, allMembersArray[3].getFirstYearOfMembership());
		assertTrue(allMembersArray[0].isActive());
		assertFalse(allMembersArray[4].isActive());
		assertEquals(0, allMembersArray[4].getLastYearOfMembership());
		assertEquals(4, allMembersArray[0].getYearsWithPaidFees().size());
		assertEquals(8, allMembersArray[1].getYearsWithPaidFees().size());
		assertEquals(6, allMembersArray[2].getYearsWithPaidFees().size());
		assertEquals(7, allMembersArray[3].getYearsWithPaidFees().size());
		assertEquals(10, allMembersArray[4].getYearsWithPaidFees().size());
	}

	@Test
	public void testGetActiveMembers() {
		List<Member> allMembers = memberService.getActiveMembers();
		assertEquals(3, allMembers.size());

		// Alle Mitglieder nach ID im Array ablegen
		Member[] allMembersArray = new Member[5];
		for (Member member : allMembers) {
			allMembersArray[member.getID() - 1] = member;
		}

		assertEquals("Moritz", allMembersArray[0].getFirstName());
		assertEquals("Schmidt", allMembersArray[1].getLastName());
		assertEquals(1050, allMembersArray[2].getPointsForRanking());
		assertTrue(allMembersArray[0].isActive());
		assertTrue(allMembersArray[1].isActive());
		assertTrue(allMembersArray[2].isActive());
		assertEquals(0, allMembersArray[0].getLastYearOfMembership());
		assertEquals(4, allMembersArray[0].getYearsWithPaidFees().size());
		assertEquals(8, allMembersArray[1].getYearsWithPaidFees().size());
		assertEquals(6, allMembersArray[2].getYearsWithPaidFees().size());

	}

	@Test
	public void testGetMembersWithUnpaidFees() {
		List<Member> allMembers = memberService.getMembersWithUnpaidFees();
		assertEquals(2, allMembers.size());

		// Alle Mitglieder nach ID im Array ablegen
		Member[] allMembersArray = new Member[5];
		for (Member member : allMembers) {
			allMembersArray[member.getID() - 1] = member;
		}

		assertEquals("Christoph", allMembersArray[2].getFirstName());
		assertEquals("Drögel", allMembersArray[3].getLastName());
		assertEquals(1050, allMembersArray[2].getPointsForRanking());
		assertEquals(2008, allMembersArray[3].getFirstYearOfMembership());
		assertTrue(allMembersArray[2].isActive());
		assertFalse(allMembersArray[3].isActive());
		assertEquals(0, allMembersArray[2].getLastYearOfMembership());
		assertEquals(2015, allMembersArray[3].getLastYearOfMembership());
		assertEquals(6, allMembersArray[2].getYearsWithPaidFees().size());
		assertEquals(7, allMembersArray[3].getYearsWithPaidFees().size());
	}

	@Test
	public void testCreateNewMember() {
		Member newMember = memberService.createNewMember();
		assertTrue(newMember.getID() > 0);

		List<Member> allMembers = memberService.getAllMembers();
		assertEquals(5, allMembers.size());
		for (Member member : allMembers) {
			// Sicherstellen dass es die ID des neuen Mitglieds noch nicht gibt
			assertTrue(newMember.getID() != member.getID());
		}
	}

	@Test
	public void testStoreMember() {
		List<Member> allMembers = memberService.getAllMembers();
		assertEquals(5, allMembers.size());

		// update durchführen
		boolean foundMueller = false;
		for (Member member : allMembers) {
			if ((member.getFirstName().equals("Moritz")) && (member.getLastName().equals("Müller"))) {
				foundMueller = true;
				member.setFirstName("Magnus Maria");
				member.setFirstYearOfMembership(1870);
				member.setLastYearOfMembership(1899);
				memberService.storeMember(member);
			}
		}
		assertTrue(foundMueller);

		// insert durchführen
		Member newMember = memberService.createNewMember();
		newMember.setFirstName("Edsger");
		newMember.setLastName("Dijkstra");
		newMember.setFirstYearOfMembership(1960);
		newMember.setPointsForRanking(2340);
		newMember.setLastYearOfMembership(1962);
		newMember.setYearsWithPaidFees(Arrays.asList(1960, 1961, 1962));
		assertTrue(newMember.isFormerMember());

		memberService.storeMember(newMember);

		// Ergebnis überprüfen
		allMembers = memberService.getAllMembers();
		assertEquals(6, allMembers.size());

		foundMueller = false;
		boolean foundNewMember = false;
		for (Member member : allMembers) {
			if ((member.getFirstName().equals("Magnus Maria")) && (member.getLastName().equals("Müller"))) {
				foundMueller = true;
				assertEquals(1870, member.getFirstYearOfMembership());
				assertEquals(1899, member.getLastYearOfMembership());
			} else if ((member.getFirstName().equals("Edsger")) && (member.getLastName().equals("Dijkstra"))) {
				foundNewMember = true;
				assertEquals(1960, member.getFirstYearOfMembership());
				assertEquals(1962, member.getLastYearOfMembership());
				assertEquals(2340, member.getPointsForRanking());
				assertEquals(3, member.getYearsWithPaidFees().size());
				assertEquals(Arrays.asList(1960, 1961, 1962), member.getYearsWithPaidFees());
				assertTrue(member.isFormerMember());
			}
		}
		assertTrue(foundMueller);
		assertTrue(foundNewMember);
	}

}
