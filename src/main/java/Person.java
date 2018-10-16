/**
 * This is the person class that all actors of the system will inherit from.
 * It provides basic functionality related to personal data.
 *
 */
public class Person {

    String firstName;
    String lastName;
    String zip;
    String streetAddress;
    String city;
    String state;
    String SSN;

    public Person (String firstName, String lastName, String SSN, String zip, String streetAddress, String city, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.zip = zip;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
    }


    String fullName() {
        return String.format("%s, %s",lastName, firstName);
    }


    String fullAddress() {
        return String.format("%s, %s, %s", streetAddress, city, state);
    }



}
