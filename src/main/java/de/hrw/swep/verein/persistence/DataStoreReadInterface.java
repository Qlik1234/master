/**
 * @author andriesc
 */
package de.hrw.swep.verein.persistence;

import java.util.List;

/**
 * Read interface for database layer
 * 
 * 
 * @author andriesc
 *
 */
public interface DataStoreReadInterface {

	/**
	 * Gibt eine Liste mit den Mitgliedsnummern aller Mitglieder zurück.
	 * 
	 * @return Liste mit den Mitgliedsnummern aller Mitglieder
	 */
	List<Integer> getAllMembers();

	/**
	 * Gibt die Jahre zurück, für die ein Mitglied seine Jahresgebühren
	 * entrichtet hat.
	 * 
	 * @param member
	 *            Die Mitgliedsnummer eines Mitglieds
	 * @return eine Liste der Jahre, für die das Mitglied seine Jahresgebühren
	 *         entrichtet hat.
	 */
	List<Integer> getAllYearsWithPaidFees(int member);

	/**
	 * Gibt den Vornamen eines Mitglieds zurück.
	 * 
	 * @param member
	 *            Die Nummer des Mitglieds.
	 * @return <code>null</code>, wenn das Mitglied nicht existiert, sonst der
	 *         Vorname.
	 */
	String getFirstNameOfMitglied(int member);

	/**
	 * Gibt den Beginn der Mitgliedschaft eines Mitglieds zurück.
	 * 
	 * @param member
	 *            Die Nummer des Mitglieds.
	 * @return <code>-1</code>, wenn das Mitglied nicht existiert, sonst das
	 *         erste Jahr der Mitgliedschaft.
	 */
	int getFirstYearOfMembership(int member);

	/**
	 * Gibt den Nachnamen eines Mitglieds zurück.
	 * 
	 * @param member
	 *            Die Nummer des Mitglieds.
	 * @return <code>null</code>, wenn das Mitglied nicht existiert, sonst der
	 *         Nachname.
	 */
	String getLastNameOfMitglied(int member);

	/**
	 * Gibt den Beginn der Mitgliedschaft eines Mitglieds zurück.
	 * 
	 * @param member
	 *            Die Nummer des Mitglieds.
	 * @return
	 *         <ul>
	 *         <li><code>-1</code>, wenn das Mitglied nicht existiert,</li>
	 *         <li><code>0</code>, wenn es noch kein letztes Jahr gibt,</li>
	 *         <li>sonst das letzte Jahr der Mitgliedschaft.</li>
	 *         </ul>
	 */
	int getLastYearOfMembership(int member);

	/**
	 * Gibt die Punktezahl eines Mitglieds auf der Rangliste zurück.
	 * 
	 * @param member
	 *            Die Mitgliedsnummer eines Mitglieds
	 * @return Die Punktezahl des Mitglieds oder -1, wenn Mitglied nicht
	 *         vorhanden ist
	 */
	int getPointsForRanking(int member);

	boolean isMemberActive(int member);
}
