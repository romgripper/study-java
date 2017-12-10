import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {

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
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("JDBC driver not found");
				printException(e);
				System.exit(1);
			}
		}

		public MusicDb() throws SQLException {
			String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
			try {
				connection = DriverManager.getConnection(url, "root", "xingbin");
			} catch (SQLException e) {
				System.out.println("Cannot connect to " + url);
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
				s.execute("CREATE TABLE artist (" + "id SMALLINT(5) NOT NULL AUTO_INCREMENT,"
						+ "name CHAR(20) NOT NULL," + "PRIMARY KEY (id)," + "KEY by_name (name))");
				System.out.println("Table artist is created successfully");
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void insertArtist(String name) {
			try (PreparedStatement s = connection.prepareStatement("INSERT INTO artist (name) VALUES (?)")) {
				s.setString(1, name);
				s.executeUpdate();
			} catch (SQLException e) {
				printException(e);
			}
			System.out.printf("%s are inserted successfully\n", name);
		}

		public void insertArtists(List<String> names) {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO artist (name) VALUES");
			for (int i = 0; i < names.size(); i++) {
				sql.append("(?),");
			}
			sql.deleteCharAt(sql.length() - 1);
			try (PreparedStatement s = connection.prepareStatement(sql.toString())) {
				for (int i = 1; i <= names.size(); i++) {
					s.setString(i, names.get(i - 1));
				}
				s.executeUpdate();
				System.out.println("Artists are inserted successfully");
			} catch (SQLException e) {
				printException(e);
			}
		}

		public void listArtists() {
			try (Statement s = connection.createStatement()) {
				ResultSet results = s.executeQuery("SELECT * FROM artist ORDER BY id");
				System.out.printf("%-10s  %s\n", "ID", "NAME");
				System.out.printf("%-10s  %s\n", "--", "----");
				while (results.next()) {
					String id = results.getString("id");
					String name = results.getString("name");
					System.out.printf("%-10s  %s\n", id, name);
				}
			} catch (SQLException e) {
				printException(e);
			}
		}

		public boolean search(String name) {
			return false;
		}

		public String getNameById(int id) {
			try (PreparedStatement s = connection.prepareStatement("SELECT name FROM artist WHERE id = ?")) {
				s.setInt(1, id);
				ResultSet ret = s.executeQuery();
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
			List<String> artists = new ArrayList<String>();
			artists.add("Jim");
			artists.add("Suzy");
			artists.add("Richard");
			db.insertArtists(artists);
			System.out.println();
			db.listArtists();
			System.out.printf("%d: %s\n", 2, db.getNameById(2));
			System.out.printf("%d: %s\n", 7, db.getNameById(7));
		} catch (SQLException e) {
			printException(e);
			System.exit(1);
		}
	}

}
