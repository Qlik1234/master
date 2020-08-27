/**
 * 
 */
package de.hrw.swep.verein.business;

import java.util.List;

/**
 * Mitglied eines Sportvereins
 * 
 * @author andriesc
 *
 */
public class Member {
	private MemberStatusInterface state = null;
	private int number = -1;
	private String firstName = null;
	private String lastName = null;
	private int firstYearOfMembership = -1;
	private List<Integer> yearsWithPaidFees = null;

	protected int lastYearOfMembership = -1;
	protected int points = -1;

	public Member(int number, String firstName, String lastName, int firstYearOfMembership, int lastYearOfMembership,
			int points, List<Integer> yearsWithPaidFees, boolean active) {
		this.number = number;
		this.lastName = lastName;
		this.firstName = firstName;
		this.firstYearOfMembership = firstYearOfMembership;
		this.points = points;
		this.lastYearOfMembership = lastYearOfMembership;
		this.yearsWithPaidFees = yearsWithPaidFees;

		if ((getCurrentYear() > lastYearOfMembership) && (lastYearOfMembership != 0)) {
			setCurrentState(new FormerMember(this));
		} else if (active) {
			setCurrentState(new ActiveMember(this));
		} else {
			setCurrentState(new PassiveMember(this));
		}
	}

	void setCurrentState(MemberStatusInterface state) {
		this.state = state;
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
	static protected int getCurrentYear() {
		return java.time.Year.now().getValue();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getFirstYearOfMembership() {
		return firstYearOfMembership;
	}

	public void setFirstYearOfMembership(int firstYearOfMembership) {
		this.firstYearOfMembership = firstYearOfMembership;
	}

	public int getLastYearOfMembership() {
		return lastYearOfMembership;
	}

	public int getPointsForRanking() {
		return points;
	}

	public void setPointsForRanking(int points) {
		state.setPointsForRanking(points);
	}

	public void setMemberToPassive() {
		state.setMemberToPassive();
	}

	public void setMemberToActive() {
		state.setMemberToActive();
	}

	public boolean isActive() {
		return state.isActive();
	}

	public void setLastYearOfMembership(int lastYearOfMembership) {
		state.setLastYearOfMembership(lastYearOfMembership);
	}

	public boolean isFormerMember() {
		return state.isFormerMember();
	}

	public List<Integer> getYearsWithPaidFees() {
		return yearsWithPaidFees;
	}

	public void setYearsWithPaidFees(List<Integer> yearsWithPaidFees) {
		this.yearsWithPaidFees = yearsWithPaidFees;
	}

	public int getID() {
		return number;
	}

}
