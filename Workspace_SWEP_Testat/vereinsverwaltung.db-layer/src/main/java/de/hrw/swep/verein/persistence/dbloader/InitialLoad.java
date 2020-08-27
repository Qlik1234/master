package de.hrw.swep.verein.persistence.dbloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitialLoad {
	private static final String connectionString = "jdbc:hsqldb:file:../vereinsverwaltung.db-layer/database/clubdb";
	private static final String dbUser = "sa";
	private static final String dbPassword = "";

	public static void main(String[] args) throws SQLException {

		Connection c = DriverManager.getConnection(connectionString, dbUser, dbPassword);
		c.setAutoCommit(false);
		System.out.println("Autocommit " + (c.getAutoCommit() ? "on" : "off"));

		c.createStatement().executeQuery("DROP TABLE FEES IF EXISTS");
		c.createStatement().executeQuery("DROP TABLE MEMBERS IF EXISTS");

		c.createStatement().executeQuery(
				"CREATE TABLE MEMBERS (id INTEGER PRIMARY KEY, lastname varchar(255), firstname varchar(100), points INTEGER, firstYear INTEGER, lastYear INTEGER, active BOOLEAN)");
		c.createStatement().executeQuery("CREATE TABLE FEES (id INTEGER PRIMARY KEY, member INTEGER, year INTEGER, "
				+ "constraint FK_MEMBERS FOREIGN KEY (member) REFERENCES MEMBERS(id))");

		c.createStatement().executeQuery("INSERT INTO MEMBERS VALUES (1, 'Müller', 'Moritz', 1252, 2013, 0, true)");
		// c.createStatement().executeQuery("INSERT INTO MEMBERS VALUES (1,
		// 'Müller', 'Karl-Heinz', 1252, 2013, 0, true)");
		c.createStatement().executeQuery("INSERT INTO MEMBERS VALUES (2, 'Schmidt', 'Alexander', 1253, 2009, 0, true)");
		c.createStatement()
				.executeQuery("INSERT INTO MEMBERS VALUES (3, 'Andriessens', 'Christoph', 1050, 2010, 0, true)");
		c.createStatement().executeQuery("INSERT INTO MEMBERS VALUES (4, 'Drögel', 'Karl', 1050, 2008, 2015, false)");
		c.createStatement()
				.executeQuery("INSERT INTO MEMBERS VALUES (5, 'Müller-Lüdenscheid', 'Karl', 990, 2007, 0, false)");

		// c.createStatement().executeQuery("INSERT INTO MEMBERS VALUES (6,
		// 'Ohrenkötter', 'Heinz',
		// 2450, 2016, 0, true)");

		c.createStatement().executeQuery("INSERT INTO FEES VALUES (1, 1, 2013)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (2, 1, 2014)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (3, 1, 2015)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (4, 1, 2016)");

		c.createStatement().executeQuery("INSERT INTO FEES VALUES (5, 2, 2009)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (6, 2, 2010)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (7, 2, 2011)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (8, 2, 2012)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (9, 2, 2013)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (10, 2, 2014)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (11, 2, 2015)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (12, 2, 2016)");

		c.createStatement().executeQuery("INSERT INTO FEES VALUES (13, 3, 2010)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (14, 3, 2011)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (15, 3, 2012)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (16, 3, 2013)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (17, 3, 2014)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (18, 3, 2015)");

		c.createStatement().executeQuery("INSERT INTO FEES VALUES (19, 4, 2008)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (20, 4, 2009)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (21, 4, 2010)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (22, 4, 2011)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (23, 4, 2012)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (24, 4, 2013)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (25, 4, 2014)");

		c.createStatement().executeQuery("INSERT INTO FEES VALUES (26, 5, 2007)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (27, 5, 2008)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (28, 5, 2009)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (29, 5, 2010)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (30, 5, 2011)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (31, 5, 2012)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (32, 5, 2013)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (33, 5, 2014)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (34, 5, 2015)");
		c.createStatement().executeQuery("INSERT INTO FEES VALUES (35, 5, 2016)");

		c.commit();
		c.close();
		System.out.println("ready");
	}
}
