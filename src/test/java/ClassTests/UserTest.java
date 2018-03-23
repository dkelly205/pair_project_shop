package ClassTests;

<<<<<<< HEAD
import models.Shop;
=======
import models.PreviousOrder;
>>>>>>> ff432449f9a87ab8fb73e697547dac77633424a0
import models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User user;
<<<<<<< HEAD
    Shop shop;

    @Before
    public void setUp() throws Exception {
        shop = new Shop("PPS Groceries");
        user = new User("James Bond", "Bond007", 20, shop);
=======
    private PreviousOrder previousOrder;
    private GregorianCalendar orderDate;
    private GregorianCalendar deliveryDate;

    @Before
    public void setUp() throws Exception {
        user = new User("James Bond", "Bond007", 20);
        orderDate = new GregorianCalendar(2020, 5, 10);
        deliveryDate = new GregorianCalendar(2020, 5, 28);
        previousOrder = new PreviousOrder(user, orderDate, deliveryDate);
>>>>>>> ff432449f9a87ab8fb73e697547dac77633424a0
    }

    @Test
    public void testName() {
        assertEquals("James Bond", user.getName());
    }

    @Test
    public void testUsername() {
        assertEquals("Bond007", user.getUsername());
    }

    @Test
    public void testDistance() {
        assertEquals(20, user.getDistance());
    }

    @Test
    public void testLifetimeSpendStartsAt0(){
        assertEquals(0, user.getLifetimeSpend(), 0.01);
    }

    @Test
    public void canCheckPreviousOrdersSize() {
        assertEquals(0, user.checkPreviousOrdersSize());
    }

    @Test
    public void canAddPreviousOrderToPreviousOrders() {
        user.addPreviousOrderToPreviousOrders(previousOrder);
        assertEquals(1, user.checkPreviousOrdersSize());
    }

}
