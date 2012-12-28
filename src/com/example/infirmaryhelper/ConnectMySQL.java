package com.example.infirmaryhelper;

import java.sql.DriverManager;
import java.util.ArrayList;

import android.util.Log;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class ConnectMySQL {
	private final String PAGETAG = "ConnectMySQL";

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public static final String MYSQL_IP = "140.118.175.194";
	public static final String MYSQL_DBNAME = "infirmaryhelper";
	public static final String MYSQL_USERNAME = "root";
	public static final String MYSQL_PASSWORD = "rb303147258";

	public ArrayList<MyObj> getList() throws Exception {
		ArrayList<MyObj> results = new ArrayList<MyObj>();
		try {
				String script = "SELECT * FROM taoyuan";

				// This will load the MySQL driver, each DB has its own driver
				Class.forName("com.mysql.jdbc.Driver");

				String path = "jdbc:mysql://"+MYSQL_IP+"/"+MYSQL_DBNAME+"?"+ "user="+MYSQL_USERNAME+"&password="+MYSQL_PASSWORD;
				Log.e(PAGETAG, path);

				// Setup the connection with the DB
				connect = (Connection) DriverManager.getConnection(path);

				Log.e(PAGETAG, "connection is success");

				// Statements allow to issue SQL queries to the database
				statement = (Statement) connect.createStatement();

				// Result set get the result of the SQL query
				resultSet = (ResultSet) statement.executeQuery(script);

				while (resultSet.next()) {
					MyObj obj = new MyObj();
					String name = resultSet.getString("name");
					String cities = resultSet.getString("cities");
					String city = resultSet.getString("city");
					String category = resultSet.getString("category");
					String address = resultSet.getString("address");
					String telephone = resultSet.getString("telephone");

					obj.name = name;
					obj.cities = cities;
					obj.city = city;
					obj.category = category;
					obj.address = address;
					obj.telephone = telephone;
					results.add(obj);
				}

				Log.e(PAGETAG, "results size = "+results.size());
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return results;
	}

	/* close connect */
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public class MyObj{

		public String name = "";
		String cities = "";
		String city = "";
		String category = "";
		String address = "";
		String telephone = "";
	}
}