package daoTests;

import model.DBManager;
import model.dao.ProfileDAO;
import model.dao.UserDAO;
import model.entity.user.User;
import model.entity.user.UserDetails;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {

    @BeforeClass
    public static void beforeTest() {
        DBManager.isTest = true;
    }

    @Test
    public void getUserTest() throws SQLException {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");

        ProfileDAO profileDAO = new ProfileDAO();
        user.setProfile(profileDAO.getProfile("Standard_User"));
        profileDAO.close();
        UserDetails details = new UserDetails();
        details.setFirstName("firstName");
        details.setLastName("lastName");
        details.setEmail("email");
        details.setPhone("phone");
        user.setDetails(details);
        UserDAO userDAO = new UserDAO();
        userDAO.insertUser(user);

        User userTest = userDAO.getUser("login");
        assertEquals(userTest.getDetails().getFirstName(), details.getFirstName());
        assertEquals(userTest.getLogin(), user.getLogin());
        assertEquals(userTest.getDetails().getEmail(), details.getEmail());
        assertEquals(userTest.getProfile().getId(), user.getProfile().getId());
        userDAO.close();
    }
}
