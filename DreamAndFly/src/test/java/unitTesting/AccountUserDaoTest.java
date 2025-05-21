package unitTesting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.AccountUser;
import storage.AccountUserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountUserDaoTest {

    private DataSource mockDataSource;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private AccountUserDao dao;

    @BeforeEach
    void setUp() throws Exception {
        mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new AccountUserDao(mockDataSource);
    }

    // TC5_1.1 - Email non presente
    @Test
    void TC5_1_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        AccountUser user = dao.doRetrieveByKey("nonexistent@example.com");

        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getNumber());
        assertEquals(0, user.getRuolo());

        verify(mockPreparedStatement).setString(1, "nonexistent@example.com");
        verify(mockPreparedStatement).executeQuery();
        
        
    }

    // TC5_1.2 - Email presente
    @Test
    void TC5_1_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("email")).thenReturn("test@example.com");
        when(mockResultSet.getString("passw")).thenReturn("pw");
        when(mockResultSet.getString("nome")).thenReturn("Mario");
        when(mockResultSet.getString("cognome")).thenReturn("Rossi");
        when(mockResultSet.getString("telefono")).thenReturn("1234567890");
        when(mockResultSet.getInt("ruolo")).thenReturn(1);

        AccountUser user = dao.doRetrieveByKey("test@example.com");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("pw", user.getPassword());
        assertEquals("Mario", user.getName());
        assertEquals("Rossi", user.getSurname());
        assertEquals("1234567890", user.getNumber());
        assertEquals(1, user.getRuolo());

        verify(mockPreparedStatement).setString(1, "test@example.com");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(1)).next();

     
    }

    // TC5_2.1 - doUpdateNumber con email non presente
    @Test
    void TC5_2_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        dao.doUpdateNumber("wrong@example.com", "1112223333");

        verify(mockPreparedStatement).setString(1, "1112223333");
        verify(mockPreparedStatement).setString(2, "wrong@example.com");
        verify(mockPreparedStatement).executeUpdate();
        
     
    }

    // TC5_2.2 - doUpdateNumber con email presente
    @Test
    void TC5_2_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdateNumber("test@example.com", "3334445555");

        verify(mockPreparedStatement).setString(1, "3334445555");
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_3.1 - doUpdatePassword con email non presente
    @Test
    void TC5_3_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        dao.doUpdatePassword("ghost@example.com", "newpw");

        verify(mockPreparedStatement).setString(1, "newpw");
        verify(mockPreparedStatement).setString(2, "ghost@example.com");
        verify(mockPreparedStatement).executeUpdate();
        
       
    }

    // TC5_3.2 - doUpdatePassword con email presente
    @Test
    void TC5_3_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdatePassword("test@example.com", "securepass");

        verify(mockPreparedStatement).setString(1, "securepass");
        verify(mockPreparedStatement).setString(2, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_4.1 - doSave con utente non presente
    @Test
    void TC5_4_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        AccountUser u = new AccountUser();
        u.setEmail("a@b.it");
        u.setPassword("p");
        u.setName("Nome");
        u.setSurname("Cognome");
        u.setNumber("123");

        dao.doSave(u);

        verify(mockPreparedStatement).setString(1, "a@b.it");
        verify(mockPreparedStatement).setString(2, "p");
        verify(mockPreparedStatement).setString(3, "Nome");
        verify(mockPreparedStatement).setString(4, "Cognome");
        verify(mockPreparedStatement).setString(5, "123");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_4.2 - doSave con utente già presente (simuliamo che executeUpdate = 0)
    @Test
    void TC5_4_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        AccountUser u = new AccountUser();
        u.setEmail("existing@b.it");
        u.setPassword("p");
        u.setName("Nome");
        u.setSurname("Cognome");
        u.setNumber("123");

        dao.doSave(u);

        verify(mockPreparedStatement).executeUpdate();
        
       
    }

    // TC5_5.1 - doDelete con email non presente
    @Test
    void TC5_5_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        dao.doDelete("nobody@example.com");

        verify(mockPreparedStatement).setString(1, "nobody@example.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_5.2 - doDelete con email presente
    @Test
    void TC5_5_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doDelete("delete@example.com");

        verify(mockPreparedStatement).setString(1, "delete@example.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_6.1 - doSaveGestore utente non presente
    @Test
    void TC5_6_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        AccountUser u = new AccountUser();
        u.setEmail("g@e.it");
        u.setPassword("pw");
        u.setName("Gest");
        u.setSurname("Ore");
        u.setNumber("999");
        u.setRuolo(1);

        dao.doSaveGestore(u);

        verify(mockPreparedStatement).setString(1, "g@e.it");
        verify(mockPreparedStatement).setString(2, "pw");
        verify(mockPreparedStatement).setString(3, "Gest");
        verify(mockPreparedStatement).setString(4, "Ore");
        verify(mockPreparedStatement).setString(5, "999");
        verify(mockPreparedStatement).setInt(6, 1);
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_6.2 - doSaveGestore utente già presente (simulate executeUpdate = 0)
    @Test
    void TC5_6_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        AccountUser u = new AccountUser();
        u.setEmail("g@old.it");
        u.setPassword("pw");
        u.setName("Gest");
        u.setSurname("Ore");
        u.setNumber("888");
        u.setRuolo(1);

        dao.doSaveGestore(u);

        verify(mockPreparedStatement).executeUpdate();
        
        
    }
    
    
    
    @AfterEach
    void tearDown() throws Exception {
        // Verifica la chiusura delle risorse comuni
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }
    
    

    

}
