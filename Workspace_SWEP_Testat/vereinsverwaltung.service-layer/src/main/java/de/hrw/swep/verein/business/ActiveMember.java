/**
 * 
 */
package de.hrw.swep.verein.business;

/**
 * @author andriesc
 *
 */
public class ActiveMember implements MemberStatusInterface {
	private Member member;

	public ActiveMember(Member member) {
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
		member.points = points;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setMemberToPassive()
	 */
	@Override
	public void setMemberToPassive() {
		member.setCurrentState(new PassiveMember(member));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.business.MemberStatusInterface#setMemberToActive()
	 */
	@Override
	public void setMemberToActive() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.business.MemberStatusInterface#isActive()
	 */
	@Override
	public boolean isActive() {
		return true;
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
		if (Member.getCurrentYear() > year)
			setMemberToFormer();
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
