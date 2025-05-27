package integrationTesting;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectionTest {
	
	
	
	
	public ConnectionTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static DataSource dataSource;
	
	public void connessione() {
		MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/dreamandfly_test");
        ds.setUser("root"); // Sostituisci con il tuo utente
        ds.setPassword("Basididati01"); // Sostituisci con la tua password
        setDataSource(ds);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		ConnectionTest.dataSource = dataSource;
	}

}
