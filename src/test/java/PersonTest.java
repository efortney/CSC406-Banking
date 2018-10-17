import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    private Person testPerson = new Person("Joe","Schmoe",123123123,64454,"123 Rochester Lane","Saint Joseph", "MO");

    @Test
    public void fullAddressTest() throws Exception {
        assertEquals("123 Rochester Lane, Saint Joseph, MO", testPerson.fullAddress());
    }

    @Test
    public void fullDetialsTest() throws Exception {
        assertEquals(String.format("First: %s \n Last: %s \n SSN: %d \n Street: %s \n Zip: %d \n" +
                " City: %s \n State: %s \n", testPerson.firstName, testPerson.lastName, testPerson.SSN,
                testPerson.streetAddress, testPerson.zip, testPerson.city, testPerson.state
        ), testPerson.fullDetails());
    }

    @Test
    public void fullNameTest() throws Exception {
        assertEquals("Schmoe, Joe",testPerson.fullName());
    }

}