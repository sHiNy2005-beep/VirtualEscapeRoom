package com.model.tests;

import com.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        user = new User("testuser", "test@example.com", "password123");
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Constructor should initialize user with correct values")
    public void testConstructor() {
        assertNotNull(user);
        assertEquals("testuser", user.getUserName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getUserId());
    }

    @Test
    @DisplayName("User ID should be unique for different users")
    public void testUniqueUserId() {
        User user2 = new User("anotheruser", "another@example.com", "pass456");
        assertNotEquals(user.getUserId(), user2.getUserId());
    }

    @Test
    @DisplayName("Should validate correct password")
    public void testValidatePasswordCorrect() {
        assertTrue(user.validatePassword("password123"));
    }

    @Test
    @DisplayName("Should reject incorrect password")
    public void testValidatePasswordIncorrect() {
        assertFalse(user.validatePassword("wrongpassword"));
    }

    @Test
    @DisplayName("Should handle null password validation")
    public void testValidatePasswordNull() {
        assertFalse(user.validatePassword(null));
    }

    @Test
    @DisplayName("Should handle empty password validation")
    public void testValidatePasswordEmpty() {
        assertFalse(user.validatePassword(""));
    }

    @Test
    @DisplayName("Password validation should be case-sensitive")
    public void testValidatePasswordCaseSensitive() {
        assertFalse(user.validatePassword("PASSWORD123"));
    }

    @Test
    @DisplayName("Should serialize user to JSON")
    public void testSerializeToJson() throws Exception {
        String json = mapper.writeValueAsString(user);
        assertNotNull(json);
        assertTrue(json.contains("testuser"));
        assertTrue(json.contains("test@example.com"));
    }

    @Test
    @DisplayName("Should deserialize user from JSON")
    public void testDeserializeFromJson() throws Exception {
        String json = "{\"userId\":\"123\",\"userName\":\"john\",\"email\":\"john@example.com\",\"password\":\"pass\"}";
        User deserializedUser = mapper.readValue(json, User.class);
        
        assertNotNull(deserializedUser);
        assertEquals("john", deserializedUser.getUserName());
        assertEquals("john@example.com", deserializedUser.getEmail());
        assertEquals("pass", deserializedUser.getPassword());
    }

    @Test
    @DisplayName("Serialization and deserialization should preserve data")
    public void testSerializationRoundTrip() throws Exception {
        String json = mapper.writeValueAsString(user);
        User deserializedUser = mapper.readValue(json, User.class);
        
        assertEquals(user.getUserId(), deserializedUser.getUserId());
        assertEquals(user.getUserName(), deserializedUser.getUserName());
        assertEquals(user.getEmail(), deserializedUser.getEmail());
        assertEquals(user.getPassword(), deserializedUser.getPassword());
    }

    @Test
    @DisplayName("Should handle special characters in username")
    public void testSpecialCharactersInUsername() {
        User specialUser = new User("user_123-test", "test@example.com", "pass");
        assertEquals("user_123-test", specialUser.getUserName());
    }

    @Test
    @DisplayName("Should handle special characters in email")
    public void testSpecialCharactersInEmail() {
        User specialUser = new User("test", "test.user+tag@example.co.uk", "pass");
        assertEquals("test.user+tag@example.co.uk", specialUser.getEmail());
    }

    @Test
    @DisplayName("Should handle whitespace in password validation")
    public void testPasswordWithWhitespace() {
        User userWithSpace = new User("test", "test@example.com", "pass word");
        assertTrue(userWithSpace.validatePassword("pass word"));
        assertFalse(userWithSpace.validatePassword("password"));
    }

    @Test
    @DisplayName("Two users with same data should have different IDs")
    public void testDifferentIdsForSameData() {
        User user1 = new User("john", "john@example.com", "pass");
        User user2 = new User("john", "john@example.com", "pass");
        
        assertNotEquals(user1.getUserId(), user2.getUserId());
    }

    @Test
    @DisplayName("Should handle empty username")
    public void testEmptyUsername() {
        User emptyUser = new User("", "test@example.com", "pass");
        assertEquals("", emptyUser.getUserName());
    }

    @Test
    @DisplayName("Should handle empty email")
    public void testEmptyEmail() {
        User emptyUser = new User("test", "", "pass");
        assertEquals("", emptyUser.getEmail());
    }

    @Test
    @DisplayName("Getter methods should return correct values")
    public void testGetters() {
        assertEquals("testuser", user.getUserName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getUserId());
    }
}