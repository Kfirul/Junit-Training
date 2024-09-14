public class Person {
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;

    // Constructor
    public Person(String firstName, String lastName, int age) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Names must contain only alphabetic characters");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Optional middle name constructor
    public Person(String firstName, String middleName, String lastName, int age) {
        this(firstName, lastName, age);
        if (middleName != null && !middleName.matches("[a-zA-Z]*")) {
            throw new IllegalArgumentException("Middle name must contain only alphabetic characters");
        }
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (!firstName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("First name must contain only alphabetic characters");
        }
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        if (middleName != null && !middleName.matches("[a-zA-Z]*")) {
            throw new IllegalArgumentException("Middle name must contain only alphabetic characters");
        }
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (!lastName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Last name must contain only alphabetic characters");
        }
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    // Method to update full name
    public void setFullName(String firstName, String middleName, String lastName) {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
    }

    // Increase age by a certain number of years
    public void increaseAge(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("Cannot increase age by a negative number");
        }
        this.age += years;
    }

    // Decrease age by a certain number of years
    public void decreaseAge(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("Cannot decrease age by a negative number");
        }
        if (this.age - years < 0) {
            throw new IllegalArgumentException("Resulting age cannot be negative");
        }
        this.age -= years;
    }


    // Overriding toString() for better string representation
    @Override
    public String toString() {
        return String.format("%s %s %s, Age: %d", firstName, middleName != null ? middleName : "", lastName, age).trim();
    }

    public boolean isAdult() {
        return age >= 18;
    }

    public String getFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }
}
