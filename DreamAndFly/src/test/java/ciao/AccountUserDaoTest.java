package ciao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.AccountUser;
import storage.AccountUserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

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
        mockDataSource         = mock(DataSource.class);
        mockConnection         = mock(Connection.class);
        mockPreparedStatement  = mock(PreparedStatement.class);
        mockResultSet          = mock(ResultSet.class);

        // ogni chiamata a getConnection() torna la stessa Connection mock
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        // ogni prepareStatement(...) torna lo stesso PreparedStatement mock
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        // per executeQuery di default torniamo il ResultSet mock
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new AccountUserDao(mockDataSource);
    }

    
    @Test
    void testTC1_1_2_emailPresentePasswordValida() throws Exception {
        // preparo il ResultSet con un record
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("email")).thenReturn("test@example.com");
        when(mockResultSet.getString("passw")).thenReturn("pass123");
        when(mockResultSet.getString("nome")).thenReturn("Mario");
        when(mockResultSet.getString("cognome")).thenReturn("Rossi");
        when(mockResultSet.getString("telefono")).thenReturn("3331234567");
        when(mockResultSet.getInt("ruolo")).thenReturn(1);

        AccountUser user = dao.doRetrieveByKey("test@example.com");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("pass123",         user.getPassword());
        assertEquals("Mario",           user.getName());
        assertEquals("Rossi",           user.getSurname());
        assertEquals("3331234567",      user.getNumber());
        assertEquals(1,                 user.getRuolo());

        verify(mockConnection).prepareStatement("select * from user_account where email = ? ");
        verify(mockPreparedStatement).setString(1, "test@example.com");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }
    
 // TC1_1_1: nessun campo compilato → chiamano doRetrieveByKey("") → la query non trova nulla
    @Test
    void testTC1_1_1_noFieldsFilled() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        AccountUser user = dao.doRetrieveByKey("");

        // non esiste un record, quindi tutti i campi restano null/0
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getNumber());
        assertEquals(0, user.getRuolo());

        verify(mockConnection).prepareStatement("select * from user_account where email = ? ");
        verify(mockPreparedStatement).setString(1, "");
        verify(mockPreparedStatement).executeQuery();
    }

    // TC1_1_3: email presente ma password non corrisponde → DAO restituisce l’utente con la password salvata
    @Test
    void testTC1_1_3_passwordDoesNotMatch() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("email")).thenReturn("foo@bar.it");
        when(mockResultSet.getString("passw")).thenReturn("correctPwd");
        when(mockResultSet.getString("nome")).thenReturn("Luigi");
        when(mockResultSet.getString("cognome")).thenReturn("Bianchi");
        when(mockResultSet.getString("telefono")).thenReturn("3456789012");
        when(mockResultSet.getInt("ruolo")).thenReturn(0);

        AccountUser user = dao.doRetrieveByKey("foo@bar.it");

        // DAO non confronta la password: restituisce quella del DB
        assertEquals("foo@bar.it", user.getEmail());
        assertEquals("correctPwd", user.getPassword());
        // e verifico che non coincida con quella “sbagliata”
        assertNotEquals("wrongPwd", user.getPassword());

        verify(mockPreparedStatement).setString(1, "foo@bar.it");
        verify(mockPreparedStatement).executeQuery();
    }

    // TC1_1_4: email non presente nel DB → mockResultSet.next()==false
    @Test
    void testTC1_1_4_emailNotPresent() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        AccountUser user = dao.doRetrieveByKey("absent@domain.com");

        // nessun record trovato
        assertNull(user.getEmail());
        assertNull(user.getPassword());

        verify(mockPreparedStatement).setString(1, "absent@domain.com");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    void testTC1_1_2_emailNonPresentePasswordValida() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        AccountUser user = dao.doRetrieveByKey("notfound@example.com");

        // tutti i campi restano null/0
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getNumber());
        assertEquals(0, user.getRuolo());

        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    void testDoSave() throws Exception {
        AccountUser u = new AccountUser();
        u.setEmail("a@e.it");
        u.setPassword("p");
        u.setName("N");
        u.setSurname("S");
        u.setNumber("123");

        dao.doSave(u);

        verify(mockConnection).prepareStatement(
            "insert into user_account(email,passw,nome,cognome,telefono) values(?,?,?,?,?)"
        );
        verify(mockPreparedStatement).setString(1, "a@e.it");
        verify(mockPreparedStatement).setString(2, "p");
        verify(mockPreparedStatement).setString(3, "N");
        verify(mockPreparedStatement).setString(4, "S");
        verify(mockPreparedStatement).setString(5, "123");
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void testDoSaveGestore() throws Exception {
        AccountUser u = new AccountUser();
        u.setEmail("g@e.it");
        u.setPassword("pw");
        u.setName("G");
        u.setSurname("T");
        u.setNumber("456");
        u.setRuolo(1);

        dao.doSaveGestore(u);

        verify(mockConnection).prepareStatement(
            "insert into user_account(email,passw,nome,cognome,telefono,ruolo) values(?,?,?,?,?,?)"
        );
        verify(mockPreparedStatement).setString(1, "g@e.it");
        verify(mockPreparedStatement).setString(2, "pw");
        verify(mockPreparedStatement).setString(3, "G");
        verify(mockPreparedStatement).setString(4, "T");
        verify(mockPreparedStatement).setString(5, "456");
        verify(mockPreparedStatement).setInt(6, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoUpdateNumber() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdateNumber("email@example.com", "123456");

        verify(mockConnection).prepareStatement("update user_account set telefono = ? where email = ? ");
        verify(mockPreparedStatement).setString(1, "123456");
        verify(mockPreparedStatement).setString(2, "email@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoUpdatePassword() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doUpdatePassword("email@example.com", "newpass");

        verify(mockConnection).prepareStatement("update user_account set passw = ? where email = ? ");
        verify(mockPreparedStatement).setString(1, "newpass");
        verify(mockPreparedStatement).setString(2, "email@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDoRetriveAll() throws Exception {
        // simulo due righe nel resultset
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("email")).thenReturn("a@b.com", "c@d.com");
        when(mockResultSet.getString("nome")).thenReturn("Alice", "Bob");
        when(mockResultSet.getString("cognome")).thenReturn("Bianchi", "Verdi");
        when(mockResultSet.getString("telefono")).thenReturn("000111222", "333444555");

        Collection<AccountUser> users = dao.doRetriveAll();
        assertEquals(2, users.size());
        // verifica almeno uno
        assertTrue(users.stream().anyMatch(u -> "a@b.com".equals(u.getEmail())));
    }

   
    @Test
    void testDoDelete() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.doDelete("test@example.com");

        verify(mockConnection).prepareStatement("delete from user_account where email=?;");
        verify(mockPreparedStatement).setString(1, "test@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }
}
