package model.graphs;

import java.util.Objects;

/**
 * the Key class. Serve as key for maps in Plan.
 * Used to order the segments by their origin and their
 * destination intersections.
 * @see Plan
 */
public class Key {

    /**
     * the origin value.
     */
    private final String x;
    /**
     * the destination value.
     */
    private final String y;

    /**
     * The constructor of the key class.
     * @param x the id of the origin intersection.
     * @param y the id of the destination intersection.
     */
    public Key(final String x, final String y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The deep copy constructor of the key class.
     * @param keyCopy the key to copy
     */
    public Key(Key keyCopy){
        this.x = keyCopy.x;
        this.y = keyCopy.y;
    }

    /**
     * Override of the equals method.
     * @param o the object to compare tho the key
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Key)) {
            return false;
        }
        Key key = (Key) o;
        return x.equals(key.x)  && y.equals(key.y);
    }

    /**
     * @return the origin key
     */
    public String getX() {
        return x;
    }

    /**
     * @return the destination key
     */
    public String getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Key{"
                + "x='" + x + '\''
                + ", y='" + y + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
