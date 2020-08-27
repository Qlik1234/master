package de.hrw.swep.verein.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author andriesc
 *
 */
public class DataStoreTest {
	private final String connectionString = "jdbc:hsqldb:file:../vereinsverwaltung.db-layer/database/clubdb";
	private final String dbUser = "sa";
	private final String dbPassword = "";

	private DataStore db;
	private IDatabaseTester databaseTester;

	// id des Gerichts in der Datenbank entspricht Position im Array
	// künstliche Position 0 jeweils eingefügt, die nicht verwendet wird
	private final Integer[] allMemberIds = { 0, 1, 2, 3, 4, 5 };
	private final String[] allLastNames = { "", "Müller", "Schmidt", "Andriessens", "Drögel", "Müller-Lüdenscheid" };
	private final String[] allFirstNames = { "", "Moritz", "Alexander", "Christoph", "Karl", "Karl" };
	private final int[] allPoints = { 0, 1252, 1253, 1050, 1050, 990 };
	private final int[] allFirstYears = { 0, 2013, 2009, 2010, 2008, 2007 };
	private final int[] allLastYears = { 0, 0, 0, 0, 2015, 0 };

	private final int[][] allYearsWithPaidFees = { { 0 }, { 2013, 2014, 2015, 2016 },
			{ 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016 }, { 2010, 2011, 2012, 2013, 2014, 2015 },
			{ 2008, 2009, 2010, 2011, 2012, 2013, 2014 },
			{ 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016 } };
	private final boolean[] activeStates = { true, true, true, true, false, false };

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		db = new DataStore();

		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", connectionString, dbUser, dbPassword);
		// TODO A2 a): Vervollständigen Sie die Initialisierung von DBUnit.
		databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(new File("full_testdb_export.xml")));
		databaseTester.onSetup();
	}

	@Test
	public void testGetAllMembers() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());

		for (Integer member : allMembers) {
			assertEquals(allMemberIds[member], member);
		}
	}

	@Test
	public void testGetFirstNameOfMitglied() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());

		for (Integer member : allMembers) {
			String firstName = db.getFirstNameOfMitglied(member);
			assertEquals(allFirstNames[member], firstName);
		}
	}

	@Test
	public void testGetLastNameOfMitglied() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());

		for (Integer member : allMembers) {
			String lastName = db.getLastNameOfMitglied(member);
			assertEquals(allLastNames[member], lastName);
		}
	}

	@Test
	public void testIsMemberActive() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		for (Integer member : allMembers) {
			Boolean memberStatus = db.isMemberActive(member);
			assertEquals(activeStates[member], memberStatus);
		}
	}

	@Test
	public void testGetFirstYearOfMembership() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		for (Integer member : allMembers) {
			int firstYear = db.getFirstYearOfMembership(member);
			assertEquals(allFirstYears[member], firstYear);
		}
	}

	@Test
	public void testGetLastYearOfMembership() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		for (Integer member : allMembers) {
			int lastYear = db.getLastYearOfMembership(member);
			assertEquals(allLastYears[member], lastYear);
		}
	}

	@Test
	public void testGetAllYearsWithPaidFees() {
		int yearsCounter = 0;

		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());

		for (Integer member : allMembers) {
			HashSet<Integer> yearsWithPaidFeesForMember = new HashSet<Integer>(db.getAllYearsWithPaidFees(member));
			assertEquals(allYearsWithPaidFees[member].length, yearsWithPaidFeesForMember.size());
			for (int paidYear : allYearsWithPaidFees[member]) {
				assertTrue(yearsWithPaidFeesForMember.contains(paidYear));
				yearsCounter++;
			}
		}
		assertEquals(35, yearsCounter);
	}

	@Test
	public void testGetPointsForRanking() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		for (Integer member : allMembers) {
			int points = db.getPointsForRanking(member);
			assertEquals(allPoints[member], points);
		}
	}

	@Test
	public void testUpsertMemberWithDbUnit() throws Exception {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());

		// Test upsert
		assertEquals("Moritz", db.getFirstNameOfMitglied(1));
		int id = db.upsertMember(1, "Karl-Heinz", db.getLastNameOfMitglied(1), db.getPointsForRanking(1),
				db.getFirstYearOfMembership(1));
		assertEquals(1, id);

		allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		assertEquals("Karl-Heinz", db.getFirstNameOfMitglied(1));

		// Test insert
		id = db.upsertMember(-1, "Heinz", "Ohrenkötter", 2450, 2016);
		

		// TODO A2 c): Diese Testmethode verändert in der Datenbank Daten. Der
		// erwartete Zustand nach den Änderungen wurde mit DBUnit bereits in die
		// Datei changed.xml gespeichert. Stellen Sie zum Ende der Testmethode
		// abschließend mit einer Assertion von DBUnit sicher, dass der Zustand
		// der Datenbank dem in changed.xml gespeicherten Zustand entspricht.
		
	}
		
		

	@Test
	public void testSetLastYearOfMembership() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		int member = allMembers.iterator().next();
		int lastYear = db.getLastYearOfMembership(member);
		db.setLastYearOfMembership(lastYear + 1, member);
		assertEquals(lastYear + 1, db.getLastYearOfMembership(member));
	}

	@Test
	public void testSetActive() {
		List<Integer> allMembers = db.getAllMembers();
		assertEquals(5, allMembers.size());
		int member = allMembers.iterator().next();
		boolean active = db.isMemberActive(member);
		db.setActive(!active, member);
		assertEquals(!active, db.isMemberActive(member));
	}

	@Test
	public void testSetActiveForNotExistingMember() {
		// TODO A2 b): Implementieren Sie die Testmethode zuende: Diese
		// Testmethode soll testen, ob die Methode DataStore.setActive() sich
		// richtig verhält und die Statusänderung eines Mitglieds, dessen ID
		// nicht in der Datenbank vorkommt, nicht akzeptiert. Überprüfen Sie
		// dabei, dass DataStore.setActive() die erwartete Fehlermeldung
		// produziert, sonst sollte der Test fehlschlagen.
		//
		// Tipp: Schauen Sie sich DataStore.getMaximumID() an, um eine nicht
		// existierende ID zu produzieren.
		
		
		
	}
}
