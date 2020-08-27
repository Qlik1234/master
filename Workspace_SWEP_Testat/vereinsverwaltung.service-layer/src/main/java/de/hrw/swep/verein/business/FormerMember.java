/**
 * 
 */
package de.hrw.swep.verein.business;

/**
 * @author andriesc
 *
 */
public class FormerMember implements MemberStatusInterface {
	private Member member;

	public FormerMember(Member member) {
		
		// TODO A4: State-Pattern zuende implementieren
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setPointsForRanking(
	 * int)
	 */
	@Override
	public void setPointsForRanking(int points) {
		throw new RuntimeException("Ehemalige Mitglieder erhalten keine Punkte im Ranking.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setMemberToPassive()
	 */
	@Override
	public void setMemberToPassive() {
		// TODO A4: State-Pattern zuende implementieren
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setMemberToActive()
	 */
	@Override
	public void setMemberToActive() {
		// TODO A4: State-Pattern zuende implementieren
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.business.MemberStatusInterface#isActive()
	 */
	@Override
	public boolean isActive() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setLastYearOfMembership
	 * (int)
	 */
	@Override
	public void setLastYearOfMembership(int year) {
		member.lastYearOfMembership = year;
		if (Member.getCurrentYear() <= year)
			setMemberToPassive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.business.MemberStatusInterface#isFormerMember()
	 */
	@Override
	public boolean isFormerMember() {
		
		// TODO A4: State-Pattern zuende implementieren
	}

	@Override
	public void setMemberToFormer() {
		// TODO A4: State-Pattern zuende implementieren
		member.
	}

}
