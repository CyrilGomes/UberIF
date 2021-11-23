package Model;

public class Request {
    protected String pickupId;
    protected String deliveryId;
    protected int pickupDuration;
    protected int deliveryDuration;

    public Request(String pickupId, String deliveryId, int pickupDuration, int deliveryDuration) {
        this.pickupId = pickupId;
        this.deliveryId = deliveryId;
        this.pickupDuration = pickupDuration;
        this.deliveryDuration = deliveryDuration;
    }

    public String getPickupId() {
        return pickupId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public int getPickupDuration() {
        return pickupDuration;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }
}
