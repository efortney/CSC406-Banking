import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    Person testPerson = new Person("Joe","Schmoe","123123123","64454","123 Rochester Lane","Saint Joseph", "MO");

    @Test
    public void fullAddressTest() throws Exception {
        assertEquals("123 Rochester Lane, Saint Joseph, MO", testPerson.fullAddress());
    }

    @Test
    public void fullNameTest() throws Exception {
        assertEquals("Schmoe, Joe",testPerson.fullName());
    }

}