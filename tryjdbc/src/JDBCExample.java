import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {

	static class MusicDb {
		private Connection connection;
		private PreparedStatement insertArtistStatement;
		private PreparedStatement nameByIdStatement;

		static {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("JDBC driver not found");
				e.printStackTrace();
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

		public void close() throws SQLException {
			if (insertArtistStatement != null) {
				insertArtistStatement.close();
			}
			if (nameByIdStatement != null) {
				nameByIdStatement.close();
			}
			connection.close();
		}

		public void createTable() throws SQLException {
			Statement s = connection.createStatement();
			s.execute("DROP TABLE IF EXISTS artist");
			System.out.println("Drop table artist successfully");
			s.execute("CREATE TABLE artist (" + "id SMALLINT(5) NOT NULL AUTO_INCREMENT," + "name CHAR(20) NOT NULL,"
					+ "PRIMARY KEY (id)," + "KEY by_name (name))");
			System.out.println("Table artist is created successfully");
			s.close();
		}

		public void insertArtist(String name) throws SQLException {
			if (insertArtistStatement == null) {
				insertArtistStatement = connection.prepareStatement("INSERT INTO artist (name) VALUES (?)");
			}
			insertArtistStatement.setString(1, name);
			insertArtistStatement.executeUpdate();
			System.out.printf("%s are inserted successfully\n", name);
		}
		
		public void insertArtists(List<String> names) throws SQLException {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO artist (name) VALUES");
			for (int i = 0; i < names.size(); i ++) {
				sql.append("(?),");
			}
			sql.deleteCharAt(sql.length() - 1);
			PreparedStatement s = connection.prepareStatement(sql.toString());
			for (int i = 1; i <= names.size(); i ++) {
				s.setString(i, names.get(i - 1));
			}
			s.executeUpdate();
			System.out.println("Artists are inserted successfully");
			s.close();
		}
		
		public void listArtists() throws SQLException {
			Statement s = connection.createStatement();
			ResultSet results = s.executeQuery("SELECT * FROM artist ORDER BY id");
			System.out.printf("%-10s  %s\n", "ID", "NAME");
			System.out.printf("%-10s  %s\n", "--", "----");		
			while (results.next()) {
				String id = results.getString("id");
				String name = results.getString("name");
				System.out.printf("%-10s  %s\n", id, name);
			}
			s.close();
		}
		
		public boolean search(String name) throws SQLException {
			return false;
		}
		
		public String getNameById(int id) throws SQLException {
			PreparedStatement s = connection.prepareStatement("SELECT name FROM artist WHERE id = ?");
			s.setInt(1, id);
			ResultSet ret = s.executeQuery();
			if (ret.next()) {
				return ret.getString("name");
			}
			return null;
		}
	}

	public static void main(String[] argv) {
		MusicDb db = null;
		try {
			db = new MusicDb();
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
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}
}
