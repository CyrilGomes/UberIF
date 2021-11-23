package model.graphs;

import java.util.Objects;

public class Key {

    private final String x;
    private final String y;

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
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
