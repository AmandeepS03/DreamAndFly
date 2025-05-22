package integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import com.mysql.cj.jdbc.MysqlDataSource;

import storage.AccountUserDao;
import storage.AccountUser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class AccountUserDaoIntegrationTest {

    private static DataSource dataSource;
    private AccountUserDao dao;
    private static ConnectionTest connessionetest = new ConnectionTest();

    @BeforeAll
    public static void initDataSource() {
    	connessionetest.connessione();
    	dataSource = connessionetest.getDataSource();
    }

    @BeforeEach
    public void setup() throws Exception {
        dao = new AccountUserDao(dataSource);
		/*
		 * try (Connection conn = dataSource.getConnection(); Statement stmt =
		 * conn.createStatement()) { stmt.execute("DELETE FROM user_account"); }
		 */
    }

    @Test
    public void testDoSaveAndRetrieve() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("test@email.com");
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setPassword("password123");
        user.setNumber("1234567890");

        dao.doSave(user);

        AccountUser retrieved = dao.doRetrieveByKey("test@email.com");

        assertEquals("Mario", retrieved.getName());
        assertEquals("Rossi", retrieved.getSurname());
        assertEquals("password123", retrieved.getPassword());
        assertEquals("1234567890", retrieved.getNumber());
    }

    @Test
    public void testDoUpdateNumber() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("update@email.com");
        user.setName("Luca");
        user.setSurname("Verdi");
        user.setPassword("pass");
        user.setNumber("1111111111");

        dao.doSave(user);
        dao.doUpdateNumber("update@email.com", "9999999999");

        AccountUser updated = dao.doRetrieveByKey("update@email.com");

        assertEquals("9999999999", updated.getNumber());
    }

    @Test
    public void testDoUpdatePassword() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("pwd@email.com");
        user.setName("Anna");
        user.setSurname("Bianchi");
        user.setPassword("oldpass");
        user.setNumber("3333333333");

        dao.doSave(user);
        dao.doUpdatePassword("pwd@email.com", "newpass");

        AccountUser updated = dao.doRetrieveByKey("pwd@email.com");

        assertEquals("newpass", updated.getPassword());
    }

    @Test
    public void testDoDelete() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("delete@email.com");
        user.setName("Stefano");
        user.setSurname("Blu");
        user.setPassword("deletepass");
        user.setNumber("777777777");

        dao.doSave(user);
        dao.doDelete("delete@email.com");

        AccountUser deleted = dao.doRetrieveByKey("delete@email.com");
        assertNull(deleted.getEmail());
    }

    // Test negativi

    @Test
    public void testSaveDuplicateUserThrowsExceptionOrFails() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("dup@email.com");
        user.setName("Dup");
        user.setSurname("User");
        user.setPassword("123");
        user.setNumber("111");
        dao.doSave(user);

        AccountUser duplicate = new AccountUser();
        duplicate.setEmail("dup@email.com");
        duplicate.setName("Dup2");
        duplicate.setSurname("User2");
        duplicate.setPassword("456");
        duplicate.setNumber("222");

        // Può variare: se il metodo doSave gestisce il duplicate con eccezione, verifica che venga lanciata.
        assertThrows(Exception.class, () -> dao.doSave(duplicate));
    }

    @Test
    public void testUpdateNumberWithNonexistentEmail() throws Exception {
        // Non dovrebbe lanciare eccezione
        assertDoesNotThrow(() -> dao.doUpdateNumber("nonexistent@email.com", "0000000000"));

        AccountUser retrieved = dao.doRetrieveByKey("nonexistent@email.com");
        assertNull(retrieved.getEmail());
    }

    @Test
    public void testUpdatePasswordWithNonexistentEmail() throws Exception {
        assertDoesNotThrow(() -> dao.doUpdatePassword("ghost@email.com", "secure123"));

        AccountUser retrieved = dao.doRetrieveByKey("ghost@email.com");
        assertNull(retrieved.getEmail());
    }

    @Test
    public void testDeleteNonexistentUser() throws Exception {
        assertDoesNotThrow(() -> dao.doDelete("fake@email.com"));
    }

    @Test
    public void testRetrieveNonexistentUserReturnsNull() throws Exception {
        AccountUser user = dao.doRetrieveByKey("notfound@email.com");
        assertNull(user.getEmail());
    }
}
