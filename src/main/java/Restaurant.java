import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Restaurant {

    static int dishesMax = 3;

    static final Queue<Order> waitingList = new LinkedList<>();

    static final Queue<Dish> dishesOrders = new LinkedList<>();

    public void comeNewCook() {
        Cook cook = new Cook(this);
        cook.cookStart();
    }

    public void comeNewWaiter() {
        Waiter waiter = new Waiter(this);
        waiter.comeWaiter();
    }

    public void comeNewGuest() {
        Guest guest = new Guest(this);
        guest.welcomeGuest();
    }
}
