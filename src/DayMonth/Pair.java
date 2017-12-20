package DayMonth;

import java.util.Objects;

public class Pair {

    int day;
    int month;


    Pair(int day, int month) {
        this.day = day;
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return day == pair.day &&
                month == pair.month;
    }

    @Override
    public int hashCode() {

        return Objects.hash(day, month);
    }

    @Override
    public String toString() {
        return day + ", " + month;
    }


}
