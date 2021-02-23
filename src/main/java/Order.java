public class Order {

    private String waiterName;
    private final String guestName;
    private String cookName;
    private String dishName;

    public Order(String guestName) {
        this.guestName = guestName;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getGuestName() {
        return guestName;
    }

    public boolean readyToOrder() {
        return getWaiterName() != null;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }


}
