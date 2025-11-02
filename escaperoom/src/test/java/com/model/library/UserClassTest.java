package com.model.library;

import org.junit.Test;

import com.model.DataLoader;
import com.model.User;

import java.util.List;
import static org.junit.Assert.*;


public class UserClassTest {

    @Test
    public void loadUsers_viaDataLoader_and_verifyLeni() throws Exception {
        List<User> users = DataLoader.getUsers();
        assertNotNull("DataLoader.getUsers() should return a list", users);

        boolean found = false;
        for (User u : users) {
            String uname = u.getUserName();
            if (uname != null && uname.startsWith("leni")) {
                found = true;
                assertTrue("email should contain 'leni'", u.getEmail().contains("leni"));
                assertNotNull("sessions list should be non-null", u.getSessions());
            }
        }

        assertTrue("should find a user whose username starts with 'leni'", found);
    }
}
