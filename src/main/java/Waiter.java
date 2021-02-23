import java.util.concurrent.atomic.AtomicInteger;

public class Waiter {

    private AtomicInteger dishQuantity = new AtomicInteger(0);

    Restaurant restaurant;

    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void comeWaiter() {

        String waiterName = Thread.currentThread().getName();
        try {
            System.out.printf("%s пришёл на работу\n", waiterName);

            while (!Thread.interrupted()) {

                Order order;

                if (dishQuantity.get() == restaurant.dishesMax) {
                    System.out.printf("Так как на кухне закончилсь продукты, %s пошёл наводить порядок\n", waiterName);
                    Thread.currentThread().interrupt();
                }

                synchronized (restaurant.waitingList) {
                    System.out.printf("%s ждёт клиента\n", waiterName);
                    if (restaurant.waitingList.isEmpty()) {

                        restaurant.waitingList.wait();
                    }
                    order = restaurant.waitingList.poll();
                }

                synchronized (order) {
                    System.out.printf("%s готов принять заказ\n", waiterName);
                    order.setWaiterName(waiterName);

                    order.notify();

                    order.wait();

                    Dish dish = new Dish();

                    synchronized (dish) {
                        dish.setWaiterName(waiterName);
                        dish.setGuestName(order.getGuestName());


                        synchronized (restaurant.dishesOrders) {
                            restaurant.dishesOrders.add(dish);

                            restaurant.dishesOrders.notify();
                        }

                        if (!dish.readyToBring()) {
                            System.out.printf("%s ждёт, пока готовится блюдо для %s\n", waiterName, dish.getGuestName());

                            dish.wait();
                        }

                        order.setCookName(dish.getCookName());
                        order.setDishName(dish.getDishName());

                        dishQuantity.getAndIncrement();

                    }

                    System.out.printf("%s получил от %s %s\n", waiterName, order.getCookName(), order.getDishName());
                    System.out.printf("%s несет %s для %s\n", waiterName, order.getDishName(), order.getGuestName());

                    synchronized (restaurant.dishesOrders) {
                        restaurant.dishesOrders.poll();

                    }

                    order.notify();

                    order.wait();

                }
            }
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
    }
}
