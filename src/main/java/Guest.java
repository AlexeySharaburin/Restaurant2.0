import java.util.concurrent.atomic.AtomicInteger;

public class Guest {

    Restaurant restaurant;

    public Guest(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public void welcomeGuest() {
        String guestName = Thread.currentThread().getName();

        try {
            System.out.printf("%s пришёл в ресторан\n", guestName);
            Thread.sleep(2000);


            if (Restaurant.dishesNumber.get() == Restaurant.dishesMax) {
                Thread.currentThread().interrupt();
            }

            Order order = new Order(guestName);


            synchronized (Restaurant.waitingList) {
                System.out.printf("%s готов сделать заказ\n", guestName);
                Restaurant.waitingList.add(order);
                Restaurant.waitingList.notify();
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
                System.out.printf("%s съел всё своё %s, оставил на чай %s, поблагодарил %s и пошёл домой\n", guestName, order.getDishName(), order.getWaiterName(), order.getCookName());

                order.notify();
            }

        } catch (Exception e) {
            System.out.printf("Так как на кухне закончились продукты, %s пошёл в соседний ресторан 'Коллекции для параллельной работы'\n", guestName);
        }

    }
}
