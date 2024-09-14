import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    Person dani;
    Person mika;
    Person sagi;

    @BeforeEach
    void setUp() {
        // This will run before each test
        dani = new Person("Dani", "Cohen", 18);
        mika = new Person("Mika", "Levi", 24);
        sagi = new Person("Sagi", "Shimon", "Rubin", 26);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create Person object correctly")
        @Order(1)
        void person_constructor_should_work() {
            assertAll(
                    () -> assertNotNull(dani),
                    () -> assertEquals("Dani", dani.getFirstName()),
                    () -> assertEquals("Cohen", dani.getLastName()),
                    () -> assertEquals(18, dani.getAge())
            );
        }

        @Test
        @DisplayName("Constructor with middle name should create Person object correctly")
        @Order(2)
        void person_constructor_with_middle_name_should_work() {
            assertAll(
                    () -> assertNotNull(sagi),
                    () -> assertEquals("Sagi", sagi.getFirstName()),
                    () -> assertEquals("Shimon", sagi.getMiddleName()),
                    () -> assertEquals("Rubin", sagi.getLastName()),
                    () -> assertEquals(26, sagi.getAge())
            );
        }

        @ParameterizedTest
        @MethodSource("provideInvalidNames")
        @DisplayName("Invalid names should throw exceptions")
        void names_with_invalid_characters_should_throw_exception(String firstName, String lastName, String expectedMessage) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Person(firstName, lastName, 18));
            assertEquals(expectedMessage, exception.getMessage());
        }

        private static Stream<Arguments> provideInvalidNames() {
            return Stream.of(
                    Arguments.of(null, "Cohen", "First name cannot be empty"),
                    Arguments.of("Dani", null, "Last name cannot be empty"),
                    Arguments.of(null, null, "First name cannot be empty"),
                    Arguments.of("Dani!", "Cohen", "Names must contain only alphabetic characters"),
                    Arguments.of("Dani2", "Cohen", "Names must contain only alphabetic characters"),
                    Arguments.of("Dani", "Cohen2", "Names must contain only alphabetic characters"),
                    Arguments.of("Dani", "Cohen!", "Names must contain only alphabetic characters"),
                    Arguments.of("Dani", "\uD83D\uDE00", "Names must contain only alphabetic characters") // Emoji in last name
            );
        }

        @Test
        @DisplayName("Constructor should throw exception for invalid age")
        @Order(4)
        void person_constructor_should_throw_exception_for_invalid_age() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Person("Dani", "Cohen", -1));
            assertEquals("Age cannot be negative", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {

        @Test
        @DisplayName("Setters should correctly set values")
        @Order(5)
        void settersTest() {
            // when
            dani.setFirstName("Lior");
            mika.setFirstName("Sharon");
            dani.setLastName("Klein");
            mika.setLastName("Rubin");
            dani.setAge(20);
            mika.setAge(5);

            // then
            assertAll("Testing Setters",
                    () -> assertEquals("Lior", dani.getFirstName()),
                    () -> assertEquals("Sharon", mika.getFirstName()),
                    () -> assertEquals("Klein", dani.getLastName()),
                    () -> assertEquals("Rubin", mika.getLastName()),
                    () -> assertEquals(20, dani.getAge()),
                    () -> assertEquals(5, mika.getAge())
            );
        }

        @Test
        @DisplayName("Setting null first name should throw exception")
        @Order(6)
        void set_first_name_null_should_throw_exception() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dani.setFirstName(null));
            assertEquals("First name cannot be empty", exception.getMessage());
        }

        @Test
        @DisplayName("Setting null last name should throw exception")
        @Order(7)
        void set_last_name_null_should_throw_exception() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dani.setLastName(null));
            assertEquals("Last name cannot be empty", exception.getMessage());
        }

        @Test
        @DisplayName("Setting invalid age should throw exception")
        @Order(8)
        void set_age_invalid_should_throw_exception() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dani.setAge(-1));
            assertEquals("Age cannot be negative", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {

        @Test
        @DisplayName("Getters should return correct values")
        @Order(9)
        void gettersTest() {
            assertAll("Testing Getters",
                    () -> assertEquals("Dani", dani.getFirstName()),
                    () -> assertEquals("Mika", mika.getFirstName()),
                    () -> assertEquals("Cohen", dani.getLastName()),
                    () -> assertEquals("Levi", mika.getLastName()),
                    () -> assertEquals("Dani Cohen", dani.getFullName()),
                    () -> assertEquals("Mika Levi", mika.getFullName()),
                    () -> assertEquals(18, dani.getAge()),
                    () -> assertEquals(24, mika.getAge())
            );
        }

        @Test
        @DisplayName("isAdult should return correct results")
        @Order(10)
        void isAdultTest() {
            Person lior = new Person("Lior", "Klein", 25);
            Person child = new Person("Mika", "Levi", 11);

            assertAll("Testing isAdult",
                    () -> assertTrue(dani.isAdult()),
                    () -> assertTrue(lior.isAdult()),
                    () -> assertFalse(child.isAdult())
            );
        }
    }

    @Nested
    @DisplayName("Age Manipulation Tests")
    class AgeManipulationTests {

        @Test
        @DisplayName("Increase age should work correctly")
        @Order(11)
        void increase_age_should_work() {
            dani.increaseAge(5);
            assertEquals(23, dani.getAge());
        }

        @Test
        @DisplayName("Decrease age should work correctly")
        @Order(12)
        void decrease_age_should_work() {
            dani.decreaseAge(5);
            assertEquals(13, dani.getAge());
        }

        @Test
        @DisplayName("Decrease age should throw exception for negative result")
        @Order(13)
        void decrease_age_should_throw_exception_for_negative_result() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dani.decreaseAge(20));
            assertEquals("Resulting age cannot be negative", exception.getMessage());
        }

        @Test
        @DisplayName("Increase age by negative number should throw exception")
        @Order(14)
        void increase_age_by_negative_number_should_throw_exception() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dani.increaseAge(-5));
            assertEquals("Cannot increase age by a negative number", exception.getMessage());
        }
    }
}
