package view.plan;

class Tuple2<T1, T2> {
    /** 1st element of the tuple. **/
    public T1 firstElem;
    /** 2nd element of the tuple. **/
    public T2 secondElem;

    Tuple2(final T1 first, final T2 second) {
        this.firstElem = first;
        this.secondElem = second;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple2 e = (Tuple2) o;
        return firstElem.equals(e.firstElem) && secondElem.equals(e.secondElem);
    }

    @Override
    public int hashCode() {
        return firstElem.hashCode() * 100000 + secondElem.hashCode();
    }
}
