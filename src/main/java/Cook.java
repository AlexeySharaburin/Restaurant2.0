import java.util.concurrent.atomic.AtomicInteger;

public class Cook {

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

                if (Restaurant.dishesNumber.get() == Restaurant.dishesMax) {
                    System.out.printf("На кухне закончилсь продукты! %s закончил работать!\n", cookName);
                    break;
                }

                synchronized (Restaurant.dishesOrders) {
                    if (Restaurant.dishesOrders.isEmpty()) {
                        Restaurant.dishesOrders.wait();
                    }
                    dish = Restaurant.dishesOrders.remove(0);
                }


                synchronized (dish) {
                    System.out.printf("%s получил от %s задание на приготовления блюда для %s\n", cookName, dish.getWaiterName(), dish.getGuestName());
                    dish.setDishName("Блюдо " + Restaurant.dishesNumber.incrementAndGet());
                    System.out.printf("%s начал готовить %s для %s по заданию от %s\n", cookName, dish.getDishName(), dish.getGuestName(), dish.getWaiterName());
                    Thread.sleep(7000);
                    System.out.printf("%s приготовил %s для %s по заданию от %s\n", cookName, dish.getDishName(), dish.getGuestName(), dish.getWaiterName());
                    dish.setCookName(cookName);
                    dish.notify();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
