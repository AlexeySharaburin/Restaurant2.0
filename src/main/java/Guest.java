import java.util.concurrent.atomic.AtomicInteger;

public class Guest {

    private AtomicInteger dishQuantity = new AtomicInteger(0);

    Restaurant restaurant;

    public Guest(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public void welcomeGuest() {
        String guestName = Thread.currentThread().getName();

        try {
            System.out.printf("%s пришёл в ресторан\n", guestName);
            Thread.sleep(2000);



            if (dishQuantity.get() > restaurant.dishesMax) {
                Thread.currentThread().interrupt();
            }

            Order order = new Order(guestName);


            synchronized (restaurant.waitingList) {
                System.out.printf("%s готов сделать заказ\n", guestName);
                restaurant.waitingList.add(order);
                restaurant.waitingList.notify();
            }

            synchronized (order) {
                if (!order.readyToOrder()) {

                    order.wait();
                }

                System.out.printf("%s сделал заказ у %s\n", guestName, order.getWaiterName());

                order.notify();

                order.wait();

                System.out.printf("%s принёс %s для %s с надписью 'Дорогому '%s' от %s'\n", order.getWaiterName(), order.getDishName(), guestName, order.getGuestName(), order.getCookName());

                System.out.printf("%s начал ужинать\n", guestName);
                Thread.sleep(5000);
                dishQuantity.getAndIncrement();
                System.out.printf("%s съел всё своё %s, оставил на чай %s, поблагодарил %s и пошёл домой\n", guestName, order.getDishName(), order.getWaiterName(), order.getCookName());

                order.notify();
            }

        } catch (Exception e) {
            System.out.printf("Так как на кухне закончились продукты, %s пошёл в соседний ресторан 'Коллекции для параллельной работы'\n", guestName);
        }

    }
}
