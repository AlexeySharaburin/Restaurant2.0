import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant {

    static int dishesMax = 4;

    static  final AtomicInteger dishesNumber = new AtomicInteger(0);

    static final List<Order> waitingList = new ArrayList<>();
    static final List<Dish> dishesOrders = new ArrayList<>();

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
