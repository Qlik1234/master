package de.hrw.swep.verein.service;

import java.util.List;

import de.hrw.swep.verein.business.Member;

public interface MemberServiceInterface {

	/**
	 * 
	 * Liefert alle gespeicherten Mitglieder zurück
	 * 
	 * @return Eine Liste mit allen Mitgliederobjekten aus der Datenbank
	 */
	List<Member> getAllMembers();

	/**
	 * Liefert eine Liste aller aktiven Mitglieder zurück
	 * 
	 * @return
	 */
	List<Member> getActiveMembers();

	/**
	 * Liefert eine Liste aller Mitglieder zurück, die noch Mitgliedsjahre ohne
	 * bezahlten Beitrag haben
	 * 
	 * @return Die Liste aller Mitglieder mit noch offenen Beiträgen
	 */
	List<Member> getMembersWithUnpaidFees();

	/**
	 * Speichert das Mitglied in der Datenbank
	 * 
	 * @param member
	 *            Das zu speichernde Mitgliedsobjekt
	 */
	void storeMember(Member member);

	/**
	 * Erzeugt ein neues Mitgliedsobjekt, das außer einer ID und Zustand "aktiv"
	 * nichts enthält und noch nicht abgespeichert ist.
	 * 
	 * @return Das neue Mitglied
	 */
	Member createNewMember();
}
