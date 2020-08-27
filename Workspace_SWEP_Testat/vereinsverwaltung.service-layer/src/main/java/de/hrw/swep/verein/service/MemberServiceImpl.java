/**
 * 
 */
package de.hrw.swep.verein.service;

import java.util.ArrayList;
import java.util.List;

import de.hrw.swep.verein.business.Member;
import de.hrw.swep.verein.persistence.DataStoreReadInterface;
import de.hrw.swep.verein.persistence.DataStoreWriteInterface;

/**
 * @author andriesc
 *
 */
public class MemberServiceImpl implements MemberServiceInterface {
	private DataStoreReadInterface dbRead;
	private DataStoreWriteInterface dbWrite;

	public MemberServiceImpl(DataStoreReadInterface dbRead, DataStoreWriteInterface dbWrite) {
		this.dbRead = dbRead;
		this.dbWrite = dbWrite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.service.MemberServiceInterface#getAllMembers()
	 */
	@Override
	public List<Member> getAllMembers() {
		List<Member> allMembers = new ArrayList<Member>();
		List<Integer> ids = dbRead.getAllMembers();

		for (int i : ids) {
			Member member = new Member(i, dbRead.getFirstNameOfMitglied(i), dbRead.getLastNameOfMitglied(i),
					dbRead.getFirstYearOfMembership(i), dbRead.getLastYearOfMembership(i),
					dbRead.getPointsForRanking(i), dbRead.getAllYearsWithPaidFees(i), dbRead.isMemberActive(i));

			allMembers.add(member);
		}

		return allMembers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.service.MemberServiceInterface#getActiveMembers()
	 */
	@Override
	public List<Member> getActiveMembers() {
		List<Member> allMembers = new ArrayList<Member>();
		List<Integer> ids = dbRead.getAllMembers();

		// Nur aktive Mitglieder zurückgeben
		for (int i : ids) {
			if (dbRead.isMemberActive(i)) {
				Member member = new Member(i, dbRead.getFirstNameOfMitglied(i), dbRead.getLastNameOfMitglied(i),
						dbRead.getFirstYearOfMembership(i), dbRead.getLastYearOfMembership(i),
						dbRead.getPointsForRanking(i), dbRead.getAllYearsWithPaidFees(i), true);
				allMembers.add(member);
			}
		}

		return allMembers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.service.MemberServiceInterface#
	 * getMembersWithUnpaidFees()
	 */
	@Override
	public List<Member> getMembersWithUnpaidFees() {
		List<Member> allMembers = new ArrayList<Member>();
		List<Integer> ids = dbRead.getAllMembers();

		// Nur Mitglieder mit unbezahlten Beiträge zurückgeben
		for (int i : ids) {
			int firstYearOfMembership = dbRead.getFirstYearOfMembership(i);
			int lastYearOfMembership = dbRead.getLastYearOfMembership(i);
			List<Integer> yearsWithPaidFees = dbRead.getAllYearsWithPaidFees(i);

			// Prüfen, ob es mehr Mitgliedsjahre gibt als bezahlte Jahre. Dann
			// sind noch Mitgliedsgebühren zu bezahlen.
			boolean hasUnpaidFees = false;
			if (lastYearOfMembership == 0) {
				// ist Mitglied ohne bekanntes Austrittsdatum
				if (getCurrentYear() - firstYearOfMembership + 1 > yearsWithPaidFees.size())
					hasUnpaidFees = true;
			} else {
				if (getCurrentYear() < lastYearOfMembership) {
					// letztes Mitgliedsjahr liegt in der Zukunft
					if (getCurrentYear() - firstYearOfMembership + 1 > yearsWithPaidFees.size())
						hasUnpaidFees = true;
				} else {
					// letztes Mitgliedsjahr ist aktuelles Jahr oder in der
					// Vergangenheit
					if (lastYearOfMembership - firstYearOfMembership + 1 > yearsWithPaidFees.size())
						hasUnpaidFees = true;
				}
			}

			if (hasUnpaidFees) {
				// Unbezahlte Mitgliedsbeiträge gefunden
				Member member = new Member(i, dbRead.getFirstNameOfMitglied(i), dbRead.getLastNameOfMitglied(i),
						firstYearOfMembership, lastYearOfMembership, dbRead.getPointsForRanking(i),
						dbRead.getAllYearsWithPaidFees(i), dbRead.isMemberActive(i));
				allMembers.add(member);
			}
		}

		return allMembers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.service.MemberServiceInterface#storeMember(de.hrw.swep
	 * .verein.business.Member)
	 */
	@Override
	public void storeMember(Member member) {
		dbWrite.upsertMember(member.getID(), member.getFirstName(), member.getLastName(), member.getPointsForRanking(),
				member.getFirstYearOfMembership());
		dbWrite.setLastYearOfMembership(member.getLastYearOfMembership(), member.getID());
		dbWrite.setActive(member.isActive(), member.getID());
		dbWrite.setYearsWithPaidFees(member.getYearsWithPaidFees(), member.getID());
	}

	/**
	 * Gibt das aktuelle Jahr zurück, um es etwa mit dem ersten oder letzten
	 * Jahr der Mitgliedschaft vergleichen zu können
	 * 
	 * Benutzt neue API für Datum und Zeit von Java 8 und gibt einfach das
	 * aktuelle Jahr als int zurück.
	 * 
	 * @return das aktuelle Jahr als ganze Zahl
	 */
	static private int getCurrentYear() {
		return java.time.Year.now().getValue();
	}

	@Override
	public Member createNewMember() {
		Member member = new Member(dbWrite.getMaximumID()+1, "", "", 0, 0, 0, null, true);
		return member;
	}

}
