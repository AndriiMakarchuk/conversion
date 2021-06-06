package daoTests;

import model.dao.ProfileDAO;
import model.entity.user.Profile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileDAOTest {

    private Profile profile;

    private ProfileDAO profileDAO;


    @Before
    public void setup(){
        profileDAO = mock(ProfileDAO.class);
    }

    @Test
    public void getProfileByIdFound(){
        Profile testData = new Profile();
        testData.setDeveloperName("Administrator");
        testData.setProfileName("Administrator");

        when(profileDAO.getProfile(3)).thenReturn(testData);

        profile = profileDAO.getProfile(3);
        assertEquals("Administrator Administrator", profile.getProfileName() + " " + profile.getDeveloperName());
    }

    @Test
    public void getProfileByIdNotFound(){

        when(profileDAO.getProfile(100)).thenReturn(null);

        profile = profileDAO.getProfile(100);
        assertEquals(null, profile);
    }

    @Test
    public void getProfileByDeveloperNameFound(){
        Profile testData = new Profile();
        testData.setId(4);
        testData.setDeveloperName("Administrator");
        testData.setProfileName("Administrator");

        when(profileDAO.getProfile("Administrator")).thenReturn(testData);

        Profile profile = profileDAO.getProfile("Administrator");
        assertEquals("Administrator", profile.getProfileName());
        assertEquals("Administrator", profile.getDeveloperName());
        assertEquals(4, profile.getId());
    }

    @Test
    public void getProfileByDeveloperNameNotFound(){
        when(profileDAO.getProfile("Invalid_Profile")).thenReturn(null);

        profile = profileDAO.getProfile("Invalid_Profile");
        assertEquals(null, profile);
    }
}
