package com.model.library;

import org.junit.Test;

import com.model.User;
import com.model.UserList;

import java.util.List;
import static org.junit.Assert.*;


public class UserListClassTest {

    @Test
    public void singletonInstance_isSameAcrossCalls() {
        UserList a = UserList.getInstance();
        UserList b = UserList.getInstance();
        assertSame("getInstance should return the same singleton", a, b);
    }

    @Test
    public void getUsers_loadsUsers_and_returnsUnmodifiableList() {
        UserList ul = UserList.getInstance();
        List<User> users = ul.getUsers();
        assertNotNull("getUsers should not return null", users);
        assertFalse("There should be at least one user loaded from json/User.json", users.isEmpty());

        boolean hasLeni = users.stream().anyMatch(u -> u.getUserName() != null && u.getUserName().startsWith("leni"));
        assertTrue("Expected a user whose username starts with 'leni'", hasLeni);

        try {
            users.add(new User("temp","t@x.com","pw"));
            fail("Returned users list should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
