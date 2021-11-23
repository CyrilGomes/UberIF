package model;
/**
 * Request, a request from a client.
 */
public class Request {
    private String pickupId;
    private String deliveryId;
    private int pickupDuration;
    private int deliveryDuration;

    /**
     * The constructor of a request object.
     * @param pickupId the id of the pickup address
     * @param deliveryId the id of the delivery address
     * @param pickupDuration the time it takes to pickup in sec
     * @param deliveryDuration the time it takes to deliver in sec
     */
    public Request(String pickupId, String deliveryId, int pickupDuration, int deliveryDuration) {
        this.pickupId = pickupId;
        this.deliveryId = deliveryId;
        this.pickupDuration = pickupDuration;
        this.deliveryDuration = deliveryDuration;
    }

    /**
     * Getter of pickupId.
     * @return the id of the pickup address.
     */
    public String getPickupId() {
        return pickupId;
    }

    /**
     * Getter of deliveryId.
     * @return the id of the delivery address.
     */
    public String getDeliveryId() {
        return deliveryId;
    }

    /**
     * Getter of pickupDuration.
     * @return get the time it takes for the pickup.
     */
    public int getPickupDuration() {
        return pickupDuration;
    }

    /**
     * Getter of deliveryDuration.
     * @return get the time it takes for the delivery.
     */
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
