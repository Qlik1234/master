/**
 * @author andriesc
 */
package de.hrw.swep.verein.persistence;

import java.util.List;

/**
 * Write interface for database layer 
 * 
 * @author andriesc
 *
 */
public interface DataStoreWriteInterface {

	/**
	 * Gibt die h�chste derzeit verwendete ID der Mitglieder zur�ck.
	 * 
	 * @return Die h�chste derzeit verwendete ID bei den Mitgliedern.
	 */
	int getMaximumID();

	/**
	 * Falls das Mitglied mit der ID <tt>number</tt> schon in der Datenbank
	 * vorhanden ist, wird es mit den angegebenen Daten aktualisiert ("UPdate").
	 * Falls es noch nicht vorhanden ist, wird es eingef�gt ("inSERT").
	 * 
	 * @param id
	 *            Die Nummer f�r das neue Mitglied. -- bei einer ID von -1 wird
	 *            ein Insert durchgef�hrt
	 * @param firstName
	 *            Der Vorname des neuen Mitglieds.
	 * @param lastName
	 *            Der Nachname des neuen Mitglieds.
	 * @param points
	 *            Die Punkte des Mitglieds
	 * @param firstYearOfMembership
	 *            Das Jahr, ab dem das Mitglied aufgenommen wird
	 * @return Die id des ver�nderten oder eingef�gten Datensatzes
	 */
	int upsertMember(int id, String firstName, String lastName, int points, int firstYearOfMembership);

	/**
	 * Setzt das Ende der Mitgliedschaft auf das angegebene Jahr.
	 * 
	 * @param lastYearOfMembership
	 *            Das Jahr, in dem die Mitgliedschaft endet.
	 * @param member
	 *            Die ID des Mitglieds, dessen Mitgliedschaft ver�ndert werden
	 *            soll
	 */
	void setLastYearOfMembership(int lastYearOfMembership, int member);

	/**
	 * Setzt den Status eines Mitglieds auf "aktiv" oder "passiv".
	 * 
	 * @param active
	 *            <ul>
	 *            <li>falls <code>true</code>, ist der neue Zustand des
	 *            Mitglieds "aktiv".</li>
	 *            <li>falls <code>false</code>, ist der neue Zustand des
	 *            Mitglieds "passiv".</li>
	 *            </ul>
	 * @param member
	 *            Die ID des Mitglieds, dessen Status ver�ndert werden soll
	 */
	void setActive(boolean active, int member);

	/**
	 * Speichert die Jahre mit bezahlten Mitgliedsgeb�hren f�r ein Mitglied.
	 * 
	 * @param yearsWithPaidFees
	 *            Eine Liste der Jahre mit bezahlten Mitgliedsgeb�hren als
	 *            Integer
	 * @param member
	 *            Die ID des betroffenen Mitglieds
	 */
	void setYearsWithPaidFees(List<Integer> yearsWithPaidFees, int member);
}
