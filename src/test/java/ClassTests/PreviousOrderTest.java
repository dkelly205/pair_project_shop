package ClassTests;

import models.PreviousOrder;
import models.Shop;
import models.User;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class PreviousOrderTest {

    private PreviousOrder previousOrder;
    private User user;
    private GregorianCalendar orderDate;
    private GregorianCalendar deliveryDate;
    private Shop shop;

    @Before
    public void setUp() throws Exception {
        shop = new Shop("PPS Groceries");
        user = new User("Ed", "edostler", 25, shop);
        orderDate = new GregorianCalendar(2020, 5, 10);
        deliveryDate = new GregorianCalendar(2020, 5, 28);
        previousOrder = new PreviousOrder(0, user, orderDate, deliveryDate, shop);
    }

    @Test
    public void testOrderDateday() {
        assertEquals(10, previousOrder.getOrderDate().get(GregorianCalendar.DAY_OF_MONTH));
    }

    @Test
    public void testOrderDatemonth() {
        assertEquals(5, previousOrder.getOrderDate().get(GregorianCalendar.MONTH));
    }

    @Test
    public void testOrderDateyear() {
        assertEquals(2020, previousOrder.getOrderDate().get(GregorianCalendar.YEAR));
    }

    @Test
    public void testDeliveryDateday() {
        assertEquals(28, previousOrder.getDeliveryDate().get(GregorianCalendar.DAY_OF_MONTH));
    }

    @Test
    public void testDeliveryDatemonth() {
        assertEquals(5, previousOrder.getDeliveryDate().get(GregorianCalendar.MONTH));
    }

    @Test
    public void testDeliveryDateyear() {
        assertEquals(2020, previousOrder.getDeliveryDate().get(GregorianCalendar.YEAR));
    }

}
