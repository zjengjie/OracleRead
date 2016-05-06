
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;


public class OracleRead {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URLSTR = "jdbc:oracle:thin:@xxxx:1521/XE";
	private static final String USERNAME = "xxxx";//
	private static final String USERPASSWORD = "xxxx";//
	private Connection connnection = null;

	public Connection getConnection() throws Exception {
		try {
			// 获取连接s
			// Class.forName(DRIVER);
			if (connnection == null) {
				connnection = DriverManager.getConnection(URLSTR, USERNAME, USERPASSWORD);
				System.out.println("连接成功");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connnection;
	}

	public void printTables() throws Exception {
		Connection conn = getConnection();
		String sql = "select * from all_tab_comments ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		while (resultSet.next()) {
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i < columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				String value = resultSet.getString(i);
				System.out.print(columnName + ":" + value + " ");
			}
			System.out.println("");
		}
	}

	public void printSql(String sql) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i < columnCount; i++) {
			System.out.print(metaData.getColumnName(i) + "\t");
		}
		System.out.println("");
		System.out.println("--------------------------------------------------------------------");
		while (resultSet.next()) {
			for (int i = 1; i < columnCount; i++) {
				String value = resultSet.getString(i);
				System.out.print(value + "\t");
			}
			System.out.println("");
		}
		System.out.println("--------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		OracleJDBC oj = new OracleJDBC();
		try (Scanner s = new Scanner(System.in)) {
			oj.getConnection();
			while (true) {
				try {
					System.out.println("query:");
					String sql = s.nextLine();
					if (sql.equals("exit"))
						break;
					oj.printSql(sql);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
