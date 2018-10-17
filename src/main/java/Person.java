/**
 * This is the person class that all actors of the system will inherit from.
 * It provides basic functionality related to personal data.
 */
public class Person {

    String firstName, lastName;

    String streetAddress, city, state;

    int SSN, zip;

    Person(String firstName, String lastName, int SSN, int zip, String streetAddress, String city, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.SSN = SSN;
        this.zip = zip;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
    }

    // print the full name of a person leading with last name
    String fullName() {
        return String.format("%s, %s", lastName, firstName);
    }


    // print all details about a user
    String fullDetails() {
        return String.format("First: %s \n Last: %s \n SSN: %d \n Street: %s \n Zip: %d \n" +
                " City: %s \n State: %s \n", firstName, lastName, SSN, streetAddress, zip, city, state
        );
    }


    // print the full address of a person
    String fullAddress() {
        return String.format("%s, %s, %s", streetAddress, city, state);
    }

}
