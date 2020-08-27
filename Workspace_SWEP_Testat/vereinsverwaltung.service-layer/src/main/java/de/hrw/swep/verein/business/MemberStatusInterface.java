/**
 * 
 */
package de.hrw.swep.verein.business;

/**
 * Schnittstelle für Zustandsbehandlung von Sportvereins-Mitgliedern
 * 
 * @author andriesc
 *
 */
public interface MemberStatusInterface {

	/**
	 * Darf neue Punkte nur akzeptieren, wenn das Mitglied aktiv und kein
	 * ehemaliges Mitglied ist.
	 * 
	 * @param points
	 */
	void setPointsForRanking(int points);

	/**
	 * Versetzt das Mitglied in den Zustand "passiv". Nur erlaubt, wenn es sich
	 * nicht um ein ehemaliges Mitglied handelt.
	 * 
	 * Bei ehemaligen Mitgliedern löst diese Operation eine
	 * <code>RuntimeException</code> aus.
	 *
	 */
	void setMemberToPassive();

	/**
	 * Versetzt das Mitglied in den Zustand "aktiv". Nur erlaubt, wenn es sich
	 * nicht um ein ehemaliges Mitglied handelt.
	 * 
	 * Bei ehemaligen Mitgliedern löst diese Operation eine
	 * <code>RuntimeException</code> aus.
	 * 
	 */
	void setMemberToActive();

	/**
	 * Prüft, ob es sich um ein aktives Mitglied handelt.
	 * 
	 * @return <code>true</code>, wenn es ein aktives Mitglied ist;
	 *         <code>false</code> bei passiven und ehemaligen Mitgliedern.
	 */
	boolean isActive();

	/**
	 * Setzt das letzte Jahr der Mitgliedschaft. Liegt das Jahr in der
	 * Vergangenheit, wird der Status des Mitglieds automatisch auf
	 * "ehemaliges Mitglied" gesetzt.
	 * 
	 * Bei ehemaligen Mitgliedern löst diese Operation eine
	 * <code>RuntimeException</code> aus.
	 * 
	 * @param year
	 */
	void setLastYearOfMembership(int year);

	/**
	 * Versetzt das Mitglied in den Zustand "ehemaliges Mitglied".
	 */
	void setMemberToFormer();

	/**
	 * Prüft, ob es sich um ein ehemaliges Mitglied handelt.
	 * 
	 * @return <code>true</code>, wenn es ein ehemaliges Mitglied ist.
	 */
	boolean isFormerMember();

}
