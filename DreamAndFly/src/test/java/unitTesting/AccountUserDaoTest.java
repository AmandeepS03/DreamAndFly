package unitTesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.AccountUser;
import storage.AccountUserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    // TC5_1.1 - doRetrieveByKey: Email non presente
    @Test
    void TC5_1_1() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        AccountUser user = dao.doRetrieveByKey("notfound@email.com");

        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getNumber());
        assertEquals(0, user.getRuolo());

        verify(mockPreparedStatement).setString(1, "notfound@email.com");
        verify(mockPreparedStatement).executeQuery();
        
        
    }

    // TC5_1.2 - doRetrieveByKey: Email presente
    @Test
    void TC5_1_2() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("email")).thenReturn("test@email.com");
        when(mockResultSet.getString("passw")).thenReturn("password123");
        when(mockResultSet.getString("nome")).thenReturn("Mario");
        when(mockResultSet.getString("cognome")).thenReturn("Rossi");
        when(mockResultSet.getString("telefono")).thenReturn("1234567890");
        when(mockResultSet.getInt("ruolo")).thenReturn(1);

        AccountUser user = dao.doRetrieveByKey("test@email.com");

        assertEquals("test@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("Mario", user.getName());
        assertEquals("Rossi", user.getSurname());
        assertEquals("1234567890", user.getNumber());
        assertEquals(1, user.getRuolo());

        verify(mockPreparedStatement).setString(1, "test@email.com");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(1)).next();

     
    }

    // TC5_2.1 - doUpdateNumber con email non presente
    @Test
    void TC5_2_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        dao.doUpdateNumber("nonexistent@email.com", "0000000000");

        verify(mockPreparedStatement).setString(1, "0000000000");
        verify(mockPreparedStatement).setString(2, "nonexistent@email.com");
        verify(mockPreparedStatement).executeUpdate();
        
     
    }

    // TC5_2.2 - doUpdateNumber con email presente
    @Test
    void TC5_2_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdateNumber("update@email.com", "9999999999");

        verify(mockPreparedStatement).setString(1, "9999999999");
        verify(mockPreparedStatement).setString(2, "update@email.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_3.1 - doUpdatePassword con email non presente
    @Test
    void TC5_3_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        dao.doUpdatePassword("ghost@email.com", "secure123");

        verify(mockPreparedStatement).setString(1, "secure123");
        verify(mockPreparedStatement).setString(2, "ghost@email.com");
        verify(mockPreparedStatement).executeUpdate();
        
       
    }

    // TC5_3.2 - doUpdatePassword con email presente nel DB
    @Test
    void TC5_3_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdatePassword("pwd@email.com", "newpass");

        verify(mockPreparedStatement).setString(1, "newpass");
        verify(mockPreparedStatement).setString(2, "pwd@email.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_4.1 - doSave con utente non presente
    @Test
    void TC5_4_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        AccountUser u = new AccountUser();
        u.setEmail("test@email.com");
        u.setPassword("password123");
        u.setName("Mario");
        u.setSurname("Rossi");
        u.setNumber("1234567890");

        dao.doSave(u);

        verify(mockPreparedStatement).setString(1, "test@email.com");
        verify(mockPreparedStatement).setString(2, "password123");
        verify(mockPreparedStatement).setString(3, "Mario");
        verify(mockPreparedStatement).setString(4, "Rossi");
        verify(mockPreparedStatement).setString(5, "1234567890");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_4.2 - doSave con utente già presente (lancia eccezione)   
    @Test
    void TC5_4_2() throws Exception {
        // Simula la violazione del vincolo di unicità sull'email
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Duplicate entry"));

        AccountUser u = new AccountUser();

        u.setEmail("dup@email.com");
        u.setPassword("123");
        u.setName("Dup");
        u.setSurname("User");
        u.setNumber("111");


        // Verifica che venga lanciata una SQLException
        assertThrows(SQLException.class, () -> dao.doSave(u));

        verify(mockPreparedStatement).executeUpdate();
    }


    // TC5_5.1 - doDelete con email non presente
    @Test
    void TC5_5_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        dao.doDelete("fake@email.com");

        verify(mockPreparedStatement).setString(1, "fake@email.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_5.2 - doDelete con email presente
    @Test
    void TC5_5_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doDelete("delete@email.com");

        verify(mockPreparedStatement).setString(1, "delete@email.com");
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_6.1 - doSaveGestore utente non presente
    @Test
    void TC5_6_1() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        AccountUser u = new AccountUser();
        u.setEmail("gestore@email.com");
        u.setPassword("gestorepass");
        u.setName("Laura");
        u.setSurname("Bianchi");
        u.setNumber("3216549870");
        u.setRuolo(1);

        dao.doSaveGestore(u);

        verify(mockPreparedStatement).setString(1, "gestore@email.com");
        verify(mockPreparedStatement).setString(2, "gestorepass");
        verify(mockPreparedStatement).setString(3, "Laura");
        verify(mockPreparedStatement).setString(4, "Bianchi");
        verify(mockPreparedStatement).setString(5, "3216549870");
        verify(mockPreparedStatement).setInt(6, 1);
        verify(mockPreparedStatement).executeUpdate();
        
        
    }

    // TC5_6.2 - doSaveGestore con email già presente (simulate executeUpdate = 0)
    @Test
    void TC5_6_2() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        AccountUser u = new AccountUser();
        u.setEmail("dupgestore@email.com");
        u.setPassword("pass1");
        u.setName("Giovanni");
        u.setSurname("Neri");
        u.setNumber("1231231234");
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
