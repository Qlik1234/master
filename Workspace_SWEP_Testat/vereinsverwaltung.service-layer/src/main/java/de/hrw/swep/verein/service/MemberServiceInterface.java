package de.hrw.swep.verein.service;

import java.util.List;

import de.hrw.swep.verein.business.Member;

public interface MemberServiceInterface {

	/**
	 * 
	 * Liefert alle gespeicherten Mitglieder zur�ck
	 * 
	 * @return Eine Liste mit allen Mitgliederobjekten aus der Datenbank
	 */
	List<Member> getAllMembers();

	/**
	 * Liefert eine Liste aller aktiven Mitglieder zur�ck
	 * 
	 * @return
	 */
	List<Member> getActiveMembers();

	/**
	 * Liefert eine Liste aller Mitglieder zur�ck, die noch Mitgliedsjahre ohne
	 * bezahlten Beitrag haben
	 * 
	 * @return Die Liste aller Mitglieder mit noch offenen Beitr�gen
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
	 * Erzeugt ein neues Mitgliedsobjekt, das au�er einer ID und Zustand "aktiv"
	 * nichts enth�lt und noch nicht abgespeichert ist.
	 * 
	 * @return Das neue Mitglied
	 */
	Member createNewMember();
}
