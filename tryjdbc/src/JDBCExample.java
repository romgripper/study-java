import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBCExample {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/test?useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "xingbin";

	private static void printException(Exception e) {
		System.out.println(e.getMessage());
		for (Throwable ex : e.getSuppressed()) {
			System.out.println("Supressed: " + ex.getMessage());
		}
		e.printStackTrace();
	}

	static class MusicDb implements AutoCloseable {
		private Connection connection;

		static {
			try {
				Class.forName(DB_DRIVER);
			} catch (ClassNotFoundException e) {
				System.out.println("JDBC driver not found");
				printException(e);
				System.exit(1);
			}
		}

		public MusicDb() throws SQLException {
			try {
				connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			} catch (SQLException e) {
				System.out.println("Cannot connect to " + DB_CONNECTION);
				throw e;
			}
			createTable();
			createProcedures();
		}

		@Override
		public void close() throws SQLException {
			System.out.println("MusicDb auto closed");
			connection.close();
		}

		private void createTable() {
			try (Statement s = connection.createStatement()) {
				s.execute("DROP TABLE IF EXISTS artist");
				System.out.println("Drop table artist successfully");
				s.execute("CREATE TABLE artist ("
						+ "id SMALLINT(5) NOT NULL AUTO_INCREMENT,"
						+ "name CHAR(20) UNIQUE NOT NULL,"
						+ "PRIMARY KEY (id),"
						+ "KEY by_name (name))");
				System.out.println("Table artist is created successfully");
			} catch (SQLException e) {
				printException(e);
			}
		}

		private void createProcedures() {
			try (Statement s = connection.createStatement()) {
				s.execute("DROP PROCEDURE IF EXISTS list_all");
				System.out.println("Drop procedure list_all successfully");
				s.execute("CREATE PROCEDURE list_all()"
						+ "BEGIN "
						+ "SELECT * FROM artist;"
						+ "END");
				System.out.println("Create procedure list_all successfully");

				s.execute("DROP PROCEDURE IF EXISTS search");
				System.out.println("Drop procedure search successfully");
				s.execute("CREATE PROCEDURE search("
						+ "IN in_id INT,"
						+ "OUT out_name CHAR(10))" // If CHAR, Data truncation: Data too long for column 'out_name'
						+ "BEGIN "
						+ "SELECT name into out_name FROM artist WHERE id=in_id;"
						+ "END");
				System.out.println("Create procedure search successfully");
			} catch (Exception e) {
				printException(e);
			}
		}

		public void insertArtist(String name) {
			String sql = "INSERT INTO artist (name) VALUES (?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setString(1, name);
				ps.executeUpdate();
			} catch (SQLException e) {
				printException(e);
			}
			System.out.printf("%s are inserted successfully\n", name);
		}

		public void removeArtist(String name) {
			String sql = "DELETE FROM artist WHERE name=?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setString(1, name);
				int deleted = ps.executeUpdate();
				System.out.printf("%s artist(s) is deleted\n", deleted);
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void insertArtists(List<String> names) {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO artist (name) VALUES");
			for (int i = 0; i < names.size(); i++) {
				sql.append("(?),");
			}
			sql.deleteCharAt(sql.length() - 1);
			try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
				for (int i = 1; i <= names.size(); i++) {
					ps.setString(i, names.get(i - 1));
				}
				int inserted = ps.executeUpdate();
				System.out.printf("%d artists are inserted successfully\n", inserted);
				/*
				 * if (count == names.size()) {
				 * System.out.printf("%d artists are inserted successfully\n", count);
				 * } else {
				 * System.out.printf("Only %d of %d artists are inserted\n", count,
				 * names.size());
				 * }
				 */
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void insertArtistsBatch(List<String> names) {
			String sql = "INSERT INTO artist (name) VALUES (?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				connection.setAutoCommit(false);
				for (String name : names) {
					ps.setString(1, name);
					ps.addBatch();
				}
				int[] inserted = ps.executeBatch();
				connection.commit();
				connection.setAutoCommit(true);
				System.out.printf("%d artists are batch-inserted successfully\n", inserted.length);
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void listArtists() {
			System.out.println("List artist:");
			try (Statement s = connection.createStatement()) {
				ResultSet results = s.executeQuery("SELECT * FROM artist ORDER BY id");
				printResults(results);
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void listArtistsWithProcedure() {
			System.out.println("List artist with procedure");
			String sql = "CALL list_all()";
			try (CallableStatement cs = connection.prepareCall(sql)) {
				ResultSet rs = cs.executeQuery();
				printResults(rs);
			} catch (SQLException e) {
				printException(e);
			}
		}

		private static void printResults(ResultSet results) throws SQLException {
			ResultSetMetaData meta = results.getMetaData();
			List<String> labels = new ArrayList<>();
			List<Integer> sizes = new ArrayList<>();
			int columns = meta.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				String lable = meta.getColumnLabel(i);
				labels.add(lable);
				int size = meta.getColumnDisplaySize(i);
				sizes.add(size);
				System.out.printf("%-" + size + "s", lable);
			}
			System.out.println();

			while (results.next()) {
				for (int i = 1; i <= columns; i++) {
					String display = results.getString(i);
					System.out.printf("%-" + sizes.get(i - 1) + "s", display);
				}
				System.out.println();
			}
		}

		public int search(String name) {
			String sql = "SELECT id FROM artist WHERE name=?";
			int id = -1;
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setString(1, name);
				ResultSet ret = ps.executeQuery();
				if (ret.next()) {
					id = ret.getInt(1);
				}

			} catch (SQLException e) {
				printException(e);
			}
			if (id > 0) {
				System.out.printf("The id of %s is %d\n", name, id);
			} else {
				System.out.printf("%s not found\n", name);
			}
			return id;
		}

		public String getNameById(int id) {
			System.out.println("Get name by id");
			String name = null;
			String sql = "SELECT name FROM artist WHERE id=?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setInt(1, id);
				ResultSet ret = ps.executeQuery();
				if (ret.next()) {
					name = ret.getString("name");
				}
			} catch (SQLException e) {
				printException(e);
			}
			System.out.printf("%d : %s\n", id, name);
			return name;
		}

		public String getNameByIdWithProcedure(int id) {
			System.out.println("Get name by id with procedure");
			String name = null;
			String sql = "CALL search(?,?)";
			try (CallableStatement cs = connection.prepareCall(sql)) {
				cs.setInt(1, id);
				cs.registerOutParameter(2, Types.CHAR);
				cs.execute();
				name = cs.getString(2);
			} catch (SQLException e) {
				printException(e);
			}
			System.out.printf("%d : %s\n", id, name);
			return name;
		}

		public void increaseAllIdBy(int inc) {
			String sql = "SELECT * FROM artist";
			try (Statement s = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
				ResultSet rs = s.executeQuery(sql);
				rs.last();
				int count = rs.getRow();
				System.out.printf("There are %d artists in total\n", count);
				// rs.beforeFirst();
				// while (rs.next()) {
				rs.afterLast();
				while (rs.previous()) {
					rs.updateInt("id", rs.getInt("id") + inc);
					rs.updateRow();
				}
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void renameWithTransaction(String oldName, String newName) {
			int id = search(oldName);
			if (id < 0) {
				return;
			}
			String deleteSql = "DELETE FROM artist WHERE id=?";
			String insertSql = "INSERT INTO artist VALUES (?,?)";
			try (PreparedStatement deletePs = connection.prepareStatement(deleteSql);
					PreparedStatement insertPs = connection.prepareStatement(insertSql)) {
				connection.setAutoCommit(false);
				deletePs.setInt(1, id);
				deletePs.executeUpdate();
				insertPs.setInt(1, id);
				insertPs.setString(2, newName);
				insertPs.executeUpdate();
				connection.commit();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					printException(ex);
				}
				printException(e);
			}
		}
	}

	public static void main(String[] argv) {
		try (MusicDb db = new MusicDb()) {
			db.insertArtist("John");
			db.insertArtist("Tom");
			/*
			 * List<String> artists = new ArrayList<>();
			 * artists.add("Jim");
			 * artists.add("Suzy");
			 * artists.add("Richard");
			 */
			String[] artists = { "Jim", "Suzy", "Richard" };
			db.insertArtists(Arrays.asList(artists));
			db.listArtists();

			String[] artists1 = { "Jerry", "Hulk", "Allen" };
			db.insertArtistsBatch(Arrays.asList(artists1));
			db.listArtists();
			/*
			 * String[] artists1 = { "Jim", "Richard", "Tom" };
			 * db.insertArtists(Arrays.asList(artists1));
			 * db.listArtists();
			 */
			db.getNameById(2);
			db.getNameByIdWithProcedure(7);

			db.search("Richard");
			db.search("Jason");

			db.removeArtist("Richard");
			db.search("Richard");

			db.increaseAllIdBy(100);
			db.listArtistsWithProcedure();

			db.renameWithTransaction("Tom", "Thomas");
			db.listArtistsWithProcedure();
		} catch (SQLException e) {
			printException(e);
			System.exit(1);
		}
	}

}
