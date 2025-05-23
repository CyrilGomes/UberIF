package model.graphs;

import java.util.Objects;

/**
 * the Key class. Serve as key for maps in Plan.
 * Used to order the segments by their origin and their destination intersections.
 * @see Plan
 */
public class Key {

    private final String x;
    private final String y;

    /**
     * The constructor of the key class.
     * @param x the id of the origin intersection.
     * @param y the id of the destination intersection.
     */
    public Key(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x.equals(key.x)  && y.equals(key.y) ;
    }

    @Override
    public String toString() {
        return "Key{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
