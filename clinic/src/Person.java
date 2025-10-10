import java.time.LocalDate;

// Abstract base class 
public abstract class Person {
    // Private fields
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    // Constructor
    public Person(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isValidPhone(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number. Must be 10 digits.");
        }
    }

    // Phone validation method
    public boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Abstract method to be overridden by subclasses
    public abstract String getRole();

    // toString
   
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}