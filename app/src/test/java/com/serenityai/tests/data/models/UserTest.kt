package com.serenityai.tests.data.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.*

class UserTest {

    @Nested
    @DisplayName("User Creation")
    inner class UserCreation {

        @Test
        @DisplayName("should create user with default values")
        fun shouldCreateUserWithDefaultValues() {
            val user = User()
            
            assertEquals("", user.userId)
            assertEquals("", user.email)
            assertEquals("", user.username)
            assertFalse(user.consentGiven)
            assertNotNull(user.dateCreated)
            assertNotNull(user.lastActiveAt)
        }

        @Test
        @DisplayName("should create user with custom values")
        fun shouldCreateUserWithCustomValues() {
            val userId = "user123"
            val email = "test@example.com"
            val username = "testuser"
            val dateCreated = Date()
            val lastActiveAt = Date()
            val consentGiven = true
            
            val user = User(
                userId = userId,
                email = email,
                username = username,
                dateCreated = dateCreated,
                lastActiveAt = lastActiveAt,
                consentGiven = consentGiven
            )
            
            assertEquals(userId, user.userId)
            assertEquals(email, user.email)
            assertEquals(username, user.username)
            assertEquals(dateCreated, user.dateCreated)
            assertEquals(lastActiveAt, user.lastActiveAt)
            assertTrue(user.consentGiven)
        }
    }

    @Nested
    @DisplayName("User Equality")
    inner class UserEquality {

        @Test
        @DisplayName("should be equal when all properties match")
        fun shouldBeEqualWhenAllPropertiesMatch() {
            val date1 = Date()
            val date2 = Date()
            
            val user1 = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                dateCreated = date1,
                lastActiveAt = date2,
                consentGiven = true
            )
            
            val user2 = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                dateCreated = date1,
                lastActiveAt = date2,
                consentGiven = true
            )
            
            assertEquals(user1, user2)
            assertEquals(user1.hashCode(), user2.hashCode())
        }

        @Test
        @DisplayName("should not be equal when properties differ")
        fun shouldNotBeEqualWhenPropertiesDiffer() {
            val user1 = User(userId = "user123", email = "test1@example.com")
            val user2 = User(userId = "user123", email = "test2@example.com")
            
            assertNotEquals(user1, user2)
        }
    }

    @Nested
    @DisplayName("User Copy")
    inner class UserCopy {

        @Test
        @DisplayName("should create copy with modified properties")
        fun shouldCreateCopyWithModifiedProperties() {
            val originalUser = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                consentGiven = false
            )
            
            val modifiedUser = originalUser.copy(
                email = "newemail@example.com",
                consentGiven = true
            )
            
            assertEquals(originalUser.userId, modifiedUser.userId)
            assertEquals(originalUser.username, modifiedUser.username)
            assertEquals("newemail@example.com", modifiedUser.email)
            assertTrue(modifiedUser.consentGiven)
        }

        @Test
        @DisplayName("should create copy with all properties modified")
        fun shouldCreateCopyWithAllPropertiesModified() {
            val originalUser = User()
            val newDate = Date()
            
            val modifiedUser = originalUser.copy(
                userId = "newuser123",
                email = "new@example.com",
                username = "newuser",
                dateCreated = newDate,
                lastActiveAt = newDate,
                consentGiven = true
            )
            
            assertEquals("newuser123", modifiedUser.userId)
            assertEquals("new@example.com", modifiedUser.email)
            assertEquals("newuser", modifiedUser.username)
            assertEquals(newDate, modifiedUser.dateCreated)
            assertEquals(newDate, modifiedUser.lastActiveAt)
            assertTrue(modifiedUser.consentGiven)
        }
    }

    @Nested
    @DisplayName("User Validation")
    inner class UserValidation {

        @Test
        @DisplayName("should identify valid user")
        fun shouldIdentifyValidUser() {
            val user = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                consentGiven = true
            )
            
            assertTrue(user.userId.isNotEmpty())
            assertTrue(user.email.isNotEmpty())
            assertTrue(user.username.isNotEmpty())
            assertTrue(user.consentGiven)
        }

        @Test
        @DisplayName("should identify invalid user with empty fields")
        fun shouldIdentifyInvalidUserWithEmptyFields() {
            val user = User()
            
            assertTrue(user.userId.isEmpty())
            assertTrue(user.email.isEmpty())
            assertTrue(user.username.isEmpty())
            assertFalse(user.consentGiven)
        }

        @Test
        @DisplayName("should identify user without consent")
        fun shouldIdentifyUserWithoutConsent() {
            val user = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                consentGiven = false
            )
            
            assertFalse(user.consentGiven)
        }
    }

    @Nested
    @DisplayName("User String Representation")
    inner class UserStringRepresentation {

        @Test
        @DisplayName("toString should contain all properties")
        fun toStringShouldContainAllProperties() {
            val user = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                consentGiven = true
            )
            
            val userString = user.toString()
            
            assertTrue(userString.contains("user123"))
            assertTrue(userString.contains("test@example.com"))
            assertTrue(userString.contains("testuser"))
            assertTrue(userString.contains("true"))
        }
    }
}
