/**
 * 
 */
package de.hrw.swep.verein.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.hrw.swep.verein.business.Member;
import de.hrw.swep.verein.persistence.DataStoreReadInterface;
import de.hrw.swep.verein.persistence.DataStoreWriteInterface;

/**
 * @author andriesc
 *
 */
public class MemberServiceImplMockTest {
	private MemberServiceInterface memberService;
	private DataStoreReadInterface dbReadMock;
	private DataStoreWriteInterface dbWriteMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dbReadMock = Mockito.mock(DataStoreReadInterface.class);
		dbWriteMock = Mockito.mock(DataStoreWriteInterface.class);

		// TODO A5: Diese Klasse testet MemberServiceImpl, indem sie anstatt der
		// tatsächlichen Datenbank-Schicht (Lese- und Schreibinterface)
		// entsprechende Mock-Objekte über Mockito verwendet.
		// Stellen Sie die Initialisierung des Mockobjektes für das
		// Leseinterface mit der when-thenReturn-Technik fertig, so dass beide
		// Testfälle der Klasse MemberServiceImplMockTest durchlaufen.
		//
		// Tipp: Es fehlen sechs Zeilen.

		// IDs für die Mitglieder
		List<Integer> ids = new ArrayList<Integer>(Arrays.asList(1, 2, 3));

		when(dbReadMock.getFirstYearOfMembership(1)).thenReturn(2014);
		when(dbReadMock.getLastYearOfMembership(1)).thenReturn(0);

		when(dbReadMock.getFirstYearOfMembership(2)).thenReturn(2013);
		when(dbReadMock.getLastYearOfMembership(2)).thenReturn(2015);
		when(dbReadMock.getAllYearsWithPaidFees(2)).thenReturn(Arrays.asList(2013, 2014, 2015));
		when(dbReadMock.isMemberActive(2)).thenReturn(false);

		when(dbReadMock.getLastNameOfMitglied(3)).thenReturn("Ehrenbret");
		when(dbReadMock.getFirstYearOfMembership(3)).thenReturn(2013);
		when(dbReadMock.getLastYearOfMembership(3)).thenReturn(0);
		when(dbReadMock.getAllYearsWithPaidFees(3)).thenReturn(Arrays.asList(2013, 2014));
		when(dbReadMock.isMemberActive(3)).thenReturn(true);

		memberService = new MemberServiceImpl(dbReadMock, dbWriteMock);
	}

	@Test
	public void testGetAllMembers() {
		List<Member> allMembers = memberService.getAllMembers();
		assertEquals(3, allMembers.size());

		// Alle Mitglieder nach ID aufsteigend geordnet im Array ablegen.
		// Am Ende ist allMembersArray[0] == Mitglied mit "Datenbank-ID" 1,
		// Am Ende ist allMembersArray[1] == Mitglied mit "Datenbank-ID" 2,
		// Am Ende ist allMembersArray[2] == Mitglied mit "Datenbank-ID" 3,
		Member[] allMembersArray = new Member[3];
		for (Member member : allMembers) {
			allMembersArray[member.getID() - 1] = member;
		}

		assertEquals("Heiner", allMembersArray[0].getFirstName());
		assertTrue(allMembersArray[0].isActive());
		assertEquals(0, allMembersArray[0].getLastYearOfMembership());
		assertEquals(3, allMembersArray[0].getYearsWithPaidFees().size());

		assertEquals("Schneckenfeld", allMembersArray[1].getLastName());
		assertEquals(3, allMembersArray[1].getYearsWithPaidFees().size());

		assertEquals(1175, allMembersArray[2].getPointsForRanking());
		assertEquals(2, allMembersArray[2].getYearsWithPaidFees().size());
	}

	@Test
	public void testGetActiveMembers() {
		List<Member> allMembers = memberService.getActiveMembers();
		assertEquals(2, allMembers.size());

		// Alle Mitglieder nach ID aufsteigend geordnet im Array ablegen
		// Am Ende ist allMembersArray[0] == Mitglied mit "Datenbank-ID" 1,
		// Am Ende ist allMembersArray[1] == Mitglied mit "Datenbank-ID" 2,
		// Am Ende ist allMembersArray[2] == Mitglied mit "Datenbank-ID" 3,
		Member[] allMembersArray = new Member[3];
		for (Member member : allMembers) {
			allMembersArray[member.getID() - 1] = member;
		}

		assertEquals("Heiner", allMembersArray[0].getFirstName());
		assertTrue(allMembersArray[0].isActive());
		assertEquals(0, allMembersArray[0].getLastYearOfMembership());
		assertEquals(3, allMembersArray[0].getYearsWithPaidFees().size());

		assertEquals("Ehrenbret", allMembersArray[2].getLastName());
		assertEquals(1175, allMembersArray[2].getPointsForRanking());
		assertTrue(allMembersArray[2].isActive());
		assertEquals(2, allMembersArray[2].getYearsWithPaidFees().size());

		// TODO A6: Ergänzen Sie diese Testmethode so, dass Sie am Ende mit
		// verify() von Mockito überprüfen, dass während des Tests die Methode
		// getAllMembers() von dbReadMock nur genau einmal aufgerufen wurde.
	}

}
