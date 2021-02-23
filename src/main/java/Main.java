import java.util.ArrayList;
import java.util.List;

public class Main {

    static int client = 5;
    static int officiants = 3;
    static int chefs = 2;

    public static void main(String[] args) throws InterruptedException {

        Restaurant restaurant = new Restaurant();

        List<Thread> threadsListWaiters = new ArrayList<>();
        List<Thread> threadsListGuests = new ArrayList<>();
        List<Thread> threadsListCooks = new ArrayList<>();

        ThreadGroup waiters = new ThreadGroup("Waiters");
        ThreadGroup guests = new ThreadGroup("Guests");
        ThreadGroup cooks = new ThreadGroup("Cooks");

        System.out.println("Ресторан 'Синхронизация вручную' открыт!");
        System.out.printf("Количество блюд в меню на сегодня - %d\n", Restaurant.dishesMax);

        for (int i = 1; i < (chefs + 1); i++) {
            Thread thread = new Thread(cooks, restaurant::comeNewCook, "Повар " + i);
            thread.start();
            threadsListCooks.add(thread);
            Thread.sleep(3000);
        }

        for (int i = 1; i < (officiants + 1); i++) {
            Thread thread = new Thread(waiters, restaurant::comeNewWaiter, "Официант " + i);
            thread.start();
            threadsListWaiters.add(thread);
            Thread.sleep(2000);
        }

        for (int i = 1; i < (client + 1); i++) {
            Thread thread = new Thread(guests, restaurant::comeNewGuest, "Гость " + i);
            thread.start();
            threadsListGuests.add(thread);
            Thread.sleep(7000);
        }

        for (Thread thread : threadsListGuests) {
            thread.join();
        }

        for (Thread thread : threadsListWaiters) {
            thread.interrupt();
            System.out.printf("%s закончил мыть столики и пошёл домой\n", thread.getName());
        }

        for (Thread thread : threadsListCooks) {
            thread.interrupt();
            System.out.printf("%s домыл свою плиту и пошёл домой\n", thread.getName());
        }
    }
}



