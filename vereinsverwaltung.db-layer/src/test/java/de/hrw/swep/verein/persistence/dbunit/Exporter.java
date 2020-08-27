/**
 * 
 */
package de.hrw.swep.verein.persistence.dbunit;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/**
 * @author andriesc
 *
 */
public class Exporter {

	private static final String connectionString = "jdbc:hsqldb:file:../vereinsverwaltung.db-layer/database/clubdb";
	private static final String dbUser = "sa";
	private static final String dbPassword = "";

	public static void main(String[] args) throws Exception {
		// database connection
		Connection jdbcConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

		// full database export the classic way, but might yield problems with
		// foreign keys
		// IDataSet fullDataSet = connection.createDataSet();

		// Full database export using correct traversing order on foreign keys
		IDataSet fullDataSet = new FilteredDataSet(new DatabaseSequenceFilter(connection), connection.createDataSet());
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
		connection.close();
		jdbcConnection.close();
	}

}
