package model;

public class Request {
    private String pickupId;
    private String deliveryId;
    private int pickupDuration;
    private int deliveryDuration;

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

    public String toString(){
        return "[id of the pickup: "+this.pickupId+
                " pickup duration: "+this.pickupDuration+"\n"+
                "id of the delivery: "+this.deliveryId+
                " delivery duration: "+this.deliveryDuration+"]";
    }
}
