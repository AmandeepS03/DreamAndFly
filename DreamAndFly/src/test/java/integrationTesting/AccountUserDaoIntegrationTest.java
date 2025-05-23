package integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import com.mysql.cj.jdbc.MysqlDataSource;

import storage.AccountUserDao;
import storage.AccountUser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AccountUserDaoIntegrationTest {

    private static DataSource dataSource;
    private AccountUserDao dao;
    private List<String> emailsToCleanup;
    private static ConnectionTest connessionetest = new ConnectionTest();


    @BeforeAll
    public static void initDataSource() {
    	connessionetest.connessione();
    	dataSource = connessionetest.getDataSource();
    }

    @BeforeEach
    public void setup() throws Exception {
        dao = new AccountUserDao(dataSource);
        emailsToCleanup = new ArrayList<>();
    }

    @AfterEach
    public void cleanup() throws Exception {
        for (String email : emailsToCleanup) {
            dao.doDelete(email);
        }
    }

    private void saveAndTrack(AccountUser user) throws Exception {
        dao.doSave(user);
        emailsToCleanup.add(user.getEmail());
    }
    
    private void saveAndTrackGestore(AccountUser user) throws Exception {
        dao.doSaveGestore(user);
        emailsToCleanup.add(user.getEmail());
    }

    // TC5_4_1_TC5_1_2_IT: doSaveAndRetrieve, salva una mail non presente e retrieve con una esistente
    @Test
    public void TC5_4_1_TC5_1_2_IT() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("test@email.com");
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setPassword("password123");
        user.setNumber("1234567890");

        saveAndTrack(user); 

        AccountUser retrieved = dao.doRetrieveByKey("test@email.com");

        assertEquals("Mario", retrieved.getName());
        assertEquals("Rossi", retrieved.getSurname());
        assertEquals("password123", retrieved.getPassword());
        assertEquals("1234567890", retrieved.getNumber());
    }
    
    //TC5_4_2_IT: doSave con utente già presente
    @Test
    public void TC5_4_2_IT() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("dup@email.com");
        user.setName("Dup");
        user.setSurname("User");
        user.setPassword("123");
        user.setNumber("111");

        saveAndTrack(user);

        AccountUser duplicate = new AccountUser();
        duplicate.setEmail("dup@email.com");
        duplicate.setName("Dup2");
        duplicate.setSurname("User2");
        duplicate.setPassword("456");
        duplicate.setNumber("222");

        assertThrows(Exception.class, () -> dao.doSave(duplicate));
    }
    
    //TC5_1_1_IT doRetrieveByKey con utente non presente nel DB
    @Test
    public void TC5_1_1_IT() throws Exception {
        AccountUser user = dao.doRetrieveByKey("notfound@email.com");
        assertNull(user.getEmail());
    }

    
    //TC5_2_1_IT doUpdateNumber con email non presente nel DB
    @Test
    public void TC5_2_1_IT() throws Exception {
        assertDoesNotThrow(() -> dao.doUpdateNumber("nonexistent@email.com", "0000000000"));

        AccountUser retrieved = dao.doRetrieveByKey("nonexistent@email.com");
        assertNull(retrieved.getEmail());
    }
    
    
    //TC5_2_2_IT doUpdateNumber con email presente nel DB
    @Test
    public void TC5_2_2_IT() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("update@email.com");
        user.setName("Luca");
        user.setSurname("Verdi");
        user.setPassword("pass");
        user.setNumber("1111111111");

        saveAndTrack(user);

        dao.doUpdateNumber("update@email.com", "9999999999");
        AccountUser updated = dao.doRetrieveByKey("update@email.com");

        assertEquals("9999999999", updated.getNumber());
    }

    //TC5_3_1_IT doUpdatePassword con email non presente nel DB
    @Test
    public void TC5_3_1_IT() throws Exception {
        assertDoesNotThrow(() -> dao.doUpdatePassword("ghost@email.com", "secure123"));

        AccountUser retrieved = dao.doRetrieveByKey("ghost@email.com");
        assertNull(retrieved.getEmail());
    }
    
    
    //TC5_3_2_IT doUpdatePassword con email presente nel DB
    @Test
    public void TC5_3_2_IT() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("pwd@email.com");
        user.setName("Anna");
        user.setSurname("Bianchi");
        user.setPassword("oldpass");
        user.setNumber("3333333333");

        saveAndTrack(user);

        dao.doUpdatePassword("pwd@email.com", "newpass");
        AccountUser updated = dao.doRetrieveByKey("pwd@email.com");

        assertEquals("newpass", updated.getPassword());
    }
    
    //TC5_5_1_IT doDelete con email non presente nel DB
    @Test
    public void TC5_5_1_IT() throws Exception {
        assertDoesNotThrow(() -> dao.doDelete("fake@email.com"));
    }

    //TC5_5_2_IT doDelete con email presente nel DB
    @Test
    public void TC5_5_2_IT() throws Exception {
        AccountUser user = new AccountUser();
        user.setEmail("delete@email.com");
        user.setName("Stefano");
        user.setSurname("Blu");
        user.setPassword("deletepass");
        user.setNumber("777777777");

        saveAndTrack(user);

        dao.doDelete("delete@email.com");

        AccountUser deleted = dao.doRetrieveByKey("delete@email.com");
        assertNull(deleted.getEmail());
    }
    
    
    // TC5_6_1_IT: doSaveGestore con email non presente nel DB
    @Test
    public void TC5_6__IT() throws Exception {
        AccountUser gestore = new AccountUser();
        gestore.setEmail("gestore@email.com");
        gestore.setName("Laura");
        gestore.setSurname("Bianchi");
        gestore.setPassword("gestorepass");
        gestore.setNumber("3216549870");
        gestore.setRuolo(1); // esempio: 1 = gestore

        saveAndTrackGestore(gestore);

        AccountUser retrieved = dao.doRetrieveByKey("gestore@email.com");

        assertEquals("Laura", retrieved.getName());
        assertEquals("Bianchi", retrieved.getSurname());
        assertEquals("gestorepass", retrieved.getPassword());
        assertEquals("3216549870", retrieved.getNumber());
        assertEquals(1, retrieved.getRuolo());
    }

    
    // TC5_6_2_IT: doSaveGestore con email già presente
    @Test
    public void TC5_6_2_IT() throws Exception {
        AccountUser gestore1 = new AccountUser();
        gestore1.setEmail("dupgestore@email.com");
        gestore1.setName("Giovanni");
        gestore1.setSurname("Neri");
        gestore1.setPassword("pass1");
        gestore1.setNumber("1231231234");
        gestore1.setRuolo(1);

        saveAndTrackGestore(gestore1);

        AccountUser gestore2 = new AccountUser();
        gestore2.setEmail("dupgestore@email.com"); // stessa email
        gestore2.setName("Marco");
        gestore2.setSurname("Verdi");
        gestore2.setPassword("pass2");
        gestore2.setNumber("5675675678");
        gestore2.setRuolo(1);

        assertThrows(Exception.class, () -> dao.doSaveGestore(gestore2));
    }


  

   

  

   

   

  
}
