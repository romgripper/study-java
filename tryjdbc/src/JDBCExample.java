import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
		}

		@Override
		public void close() throws SQLException {
			System.out.println("MusicDb auto closed");
			connection.close();
		}

		public void createTable() {
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
				 * System.out.printf("%d artists are inserted successfully\n", count); } else {
				 * System.out.printf("Only %d of %d artists are inserted\n", count,
				 * names.size()); }
				 */
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void insertArtistsBatch(List<String> names) {
			String sql = "INSERT INTO artist (name) VALUES (?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				for (String name : names) {
					ps.setString(1, name);
					ps.addBatch();
				}
				int[] inserted = ps.executeBatch();
				System.out.printf("%d artists are batch-inserted successfully\n", inserted.length);
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void listArtists() {
			System.out.println("Artist list:");
			try (Statement s = connection.createStatement()) {
				ResultSet results = s.executeQuery("SELECT * FROM artist ORDER BY id");
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
			} catch (SQLException e) {
				printException(e);
			}
		}

		public boolean search(String name) {
			String sql = "SELECT * FROM artist WHERE name=?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setString(1, name);
				ResultSet ret = ps.executeQuery();
				if (ret.next()) {
					return true;
				}

			} catch (SQLException e) {
				printException(e);
			}
			return false;
		}

		public String getNameById(int id) {
			String sql = "SELECT name FROM artist WHERE id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setInt(1, id);
				ResultSet ret = ps.executeQuery();
				if (ret.next()) {
					return ret.getString("name");
				}
			} catch (SQLException e) {
				printException(e);
			}
			return null;
		}
	}

	public static void main(String[] argv) {
		try (MusicDb db = new MusicDb()) {
			db.createTable();
			db.insertArtist("John");
			db.insertArtist("Tom");
			/*
			 * List<String> artists = new ArrayList<>(); artists.add("Jim");
			 * artists.add("Suzy"); artists.add("Richard");
			 */
			String[] artists = { "Jim", "Suzy", "Richard" };
			db.insertArtists(Arrays.asList(artists));
			db.listArtists();

			String[] artists1 = { "Jerry", "Hulk", "Allen" };
			db.insertArtistsBatch(Arrays.asList(artists1));
			db.listArtists();
			/*
			 * String[] artists1 = { "Jim", "Richard", "Tom" };
			 * db.insertArtists(Arrays.asList(artists1)); System.out.println();
			 * db.listArtists();
			 */
			System.out.printf("%d: %s\n", 2, db.getNameById(2));
			System.out.printf("%d: %s\n", 7, db.getNameById(7));
			String found = (db.search("Richard") ? "Found:" : "Not found:") + " Richard";
			System.out.println(found);
			found = (db.search("Jason") ? "Found:" : "Not found:") + " Jason";
			System.out.println(found);
			db.removeArtist("Richard");
			found = (db.search("Richard") ? "Found:" : "Not found:") + " Richard";
			System.out.println(found);
		} catch (SQLException e) {
			printException(e);
			System.exit(1);
		}
	}

}
