/**
 * 
 */
package de.hrw.swep.verein.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database layer implementation 
 * 
 * @author andriesc
 *
 */
public class DataStore implements DataStoreReadInterface, DataStoreWriteInterface {

	private final String connectionString = "jdbc:hsqldb:file:../vereinsverwaltung.db-layer/database/clubdb";
	private final String dbUser = "sa";
	private final String dbPassword = "";

	private Connection conn = null;

	public DataStore() throws SQLException, ClassNotFoundException {
		// connect to the database. This will load the db files and start the
		// database if it is not already running.
		conn = DriverManager.getConnection(connectionString, dbUser, dbPassword);
	}

	public void finalize() {
		if (conn != null)
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

	private ResultSet executeQuery(String sql) throws SQLException {
		Connection c = null;
		try {
			if (conn == null)
				c = DriverManager.getConnection(connectionString, dbUser, dbPassword);
			else
				c = conn;
			ResultSet rs = c.createStatement().executeQuery(sql);
			c.commit();
			return rs;
		} finally {
			try {
				if (c != null && conn == null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private int executeUpdate(String sql) throws SQLException {
		Connection c = null;
		try {
			if (conn == null)
				c = DriverManager.getConnection(connectionString, dbUser, dbPassword);
			else
				c = conn;
			int result = c.createStatement().executeUpdate(sql);
			c.commit();
			return result;
		} finally {
			try {
				if (c != null && conn == null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean getBoolean(ResultSet rs) throws SQLException {
		if (rs != null && rs.next())
			return rs.getBoolean(1);

		throw new PersistenceException("result set error");
	}

	private List<Integer> getResultAsInts(String sql) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ResultSet result = executeQuery(sql);
			while (result.next())
				list.add(result.getInt(1));
		} catch (SQLException e) {
			throw new PersistenceException("No integers could be read.", e);
		}
		return list;
	}

	private List<String> getResultAsStrings(String sql) {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet result = executeQuery(sql);
			while (result.next())
				list.add(result.getString(1));
		} catch (SQLException e) {
			throw new PersistenceException("No strings could be read.", e);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.persistence.DataStoreWriteInterface#addMember(int,
	 * java.lang.String, java.lang.String, int)
	 */
	@Override
	public int upsertMember(int id, String firstName, String lastName, int points, int firstYearOfMembership) {
		if ((id > -1) && getResultAsInts("SELECT id FROM MEMBERS WHERE id=" + id).iterator().hasNext()) {
			// Perform update
			try {
				// Mitglied aktualisieren

				int res = executeUpdate("UPDATE MEMBERS SET (id, firstName, lastName, points, firstYear) =(" + id
						+ ", \'" + firstName + "\', \'" + lastName + "\', " + points + ", " + firstYearOfMembership
						+ ") WHERE id=" + id);

				if (res == 0)
					throw new PersistenceException("Mitglied konnte nicht gespeichert werden.");

				return id;

			} catch (SQLException e) {
				throw new PersistenceException("Mitglied konnte nicht gespeichert werden.", e);
			}

		} else {
			// Mitglied mit übergebener id nicht gefunden, übergebene id wird
			// deshalb ignoriert und neue id wird ermittelt
			try {
				int maxId = getMaximumID();

				maxId++;

				int res = executeUpdate(
						"INSERT INTO MEMBERS (id, firstName, lastName, points, firstYear, lastYear, active) VALUES("
								+ maxId + ", \'" + firstName + "\', \'" + lastName + "\', " + points + ", "
								+ firstYearOfMembership + ", 0, true)");

				if (res == 0)
					throw new PersistenceException("Mitglied konnte nicht hinzugefügt werden.");

				return maxId;

			} catch (SQLException e) {
				throw new PersistenceException("Mitglied konnte nicht hinzugefügt werden.", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.persistence.DataStoreWriteInterface#
	 * setLastYearOfMembership(int)
	 */
	@Override
	public void setLastYearOfMembership(int lastYearOfMembership, int member) {
		try {
			int res = executeUpdate("UPDATE MEMBERS SET (lastYear) =(" + lastYearOfMembership + ") WHERE id=" + member);

			if (res == 0)
				throw new PersistenceException("Jahr der letzten Mitgliedschaft konnte nicht gespeichert werden.");

		} catch (SQLException e) {
			throw new PersistenceException("Jahr der letzten Mitgliedschaft konnte nicht gespeichert werden.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.persistence.DataStoreWriteInterface#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active, int member) {
		try {
			int res = executeUpdate("UPDATE MEMBERS SET (active) =(" + active + ") WHERE id=" + member);

			if (res == 0)
				throw new PersistenceException("Status der Mitgliedschaft konnte nicht gespeichert werden.");

		} catch (SQLException e) {
			throw new PersistenceException("Status der Mitgliedschaft konnte nicht gespeichert werden.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.persistence.DataStoreReadInterface#getAllMembers()
	 */
	@Override
	public List<Integer> getAllMembers() {
		return new ArrayList<Integer>(getResultAsInts("SELECT id FROM MEMBERS"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.persistence.DataStoreReadInterface#
	 * getFirstNameOfMitglied(int)
	 */
	@Override
	public String getFirstNameOfMitglied(int member) {
		return getResultAsStrings("SELECT firstname FROM MEMBERS WHERE id=" + member).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.persistence.DataStoreReadInterface#
	 * getLastNameOfMitglied(int)
	 */
	@Override
	public String getLastNameOfMitglied(int member) {
		return getResultAsStrings("SELECT lastname FROM MEMBERS WHERE id=" + member).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.persistence.DataStoreReadInterface#isMemberActive(int)
	 */
	@Override
	public boolean isMemberActive(int member) {
		try {
			return getBoolean(executeQuery("SELECT active FROM MEMBERS WHERE id=" + member));
		} catch (SQLException e) {
			throw new PersistenceException("Member status could not be read.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.persistence.DataStoreReadInterface#
	 * getFirstYearOfMembership(int)
	 */
	@Override
	public int getFirstYearOfMembership(int member) {
		return getResultAsInts("SELECT firstyear FROM MEMBERS WHERE id=" + member).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.persistence.DataStoreReadInterface#
	 * getLastYearOfMembership(int)
	 */
	@Override
	public int getLastYearOfMembership(int member) {
		return getResultAsInts("SELECT lastyear FROM MEMBERS WHERE id=" + member).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hrw.swep.verein.persistence.DataStoreReadInterface#
	 * getAllYearsWithPaidFees(int)
	 */
	@Override
	public List<Integer> getAllYearsWithPaidFees(int member) {
		return getResultAsInts("SELECT year FROM FEES WHERE member=" + member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.persistence.DataStoreReadInterface#getPointsForRanking
	 * (int)
	 */
	@Override
	public int getPointsForRanking(int member) {
		return getResultAsInts("SELECT points FROM MEMBERS WHERE id=" + member).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hrw.swep.verein.persistence.DataStoreWriteInterface#getMaximumID()
	 */
	@Override
	public int getMaximumID() {
		return getResultAsInts("SELECT MAX(id) FROM MEMBERS").get(0);
	}

	@Override
	public void setYearsWithPaidFees(List<Integer> yearsWithPaidFees, int member) {

		List<Integer> members = getResultAsInts("SELECT id FROM MEMBERS WHERE id=" + member);
		if (members.size() == 0)
			throw new PersistenceException("Mitglied nicht gefunden.");

		try {
			executeUpdate("DELETE FROM FEES WHERE member =" + member);
		} catch (SQLException e) {
			throw new PersistenceException("Mitgliedsgebühren konnten vor Aktualisierung nicht gelöscht werden.");
		}
		if (yearsWithPaidFees != null) {
			int maxIdForFees = getResultAsInts("SELECT MAX(id) FROM FEES").get(0);

			for (Integer year : yearsWithPaidFees) {
				int res;
				try {
					res = executeUpdate("INSERT INTO FEES (id, member, year) VALUES (" + ++maxIdForFees + ", " + member
							+ ", " + year + ")");
					if (res == 0)
						throw new PersistenceException("Mitgliedsgebühren konnten nicht gespeichert werden.");
				} catch (SQLException e) {
					throw new PersistenceException("Mitgliedsgebühren konnten nicht gespeichert werden.");
				}

			}
		}
	}

}
