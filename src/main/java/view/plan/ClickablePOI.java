package view.plan;

import model.Intersection;
/**
 * Interface of a clickable point of interest.
 */
public interface ClickablePOI {
    /** Adds info on the clickablePOI.
     * @param origin Intersection representing the origin point
     * @param radiusOrigin Radius of the clickable box
     * @param destination Intersection representing the destination point
     * @param radiusDestination Radius of the clickable box
     * **/
    void updateTrack(Intersection origin, int radiusOrigin,
                            Intersection destination, int radiusDestination);
}
