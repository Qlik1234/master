/**
 * 
 */
package de.hrw.swep.verein.business;

/**
 * @author andriesc
 *
 */
public class PassiveMember implements MemberStatusInterface {
	private Member member;

	public PassiveMember(Member member) {
		this.member = member;
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
		throw new RuntimeException("Passive Mitglieder können keine neuen Punkte im Ranking erhalten.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setMemberToPassive()
	 */
	@Override
	public void setMemberToPassive() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setMemberToActive()
	 */
	@Override
	public void setMemberToActive() {
		member.setCurrentState(new ActiveMember(member));

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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.business.MemberStatusInterface#isFormerMember()
	 */
	@Override
	public boolean isFormerMember() {
		return false;
	}

	@Override
	public void setMemberToFormer() {
		member.setCurrentState(new FormerMember(member));
	}

}
