import java.util.concurrent.atomic.AtomicInteger;

public class Cook {

    private AtomicInteger dishQuantity = new AtomicInteger(0);

    Restaurant restaurant;

    public Cook(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void cookStart() {
        String cookName = Thread.currentThread().getName();

        try {
            System.out.printf("%s пришёл на работу\n", cookName);

            while (!Thread.interrupted()) {

                Dish dish;

                if (dishQuantity.get() > restaurant.dishesMax) {
                    System.out.printf("На кухне закончилсь продукты! %s закончил работать!\n", cookName);
                    break;
                }

                synchronized (restaurant.dishesOrders) {
                    if (restaurant.dishesOrders.isEmpty()) {
                        restaurant.dishesOrders.wait();
                    }
                    dish = restaurant.dishesOrders.poll();
                }

                System.out.printf("%s получил от %s заказ-наряд для приготовления блюда для %s\n", cookName, dish.getWaiterName(), dish.getGuestName());

                synchronized (dish) {
                    dish.setDishName("Блюдо " + dishQuantity.incrementAndGet());
                    System.out.printf("%s начал готовить %s для %s по заказ-наряду от %s\n", cookName, dish.getDishName(), dish.getGuestName(), dish.getWaiterName());
                    Thread.sleep(7000);
                    System.out.printf("%s приготовил %s для %s по заказ-наряду от %s\n", cookName, dish.getDishName(), dish.getGuestName(), dish.getWaiterName());
                    dish.setCookName(cookName);
                    dish.notify();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
