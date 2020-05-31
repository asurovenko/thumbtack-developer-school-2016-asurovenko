package net.thumbtack.asurovenko;

import com.sun.org.apache.xpath.internal.functions.Function2Args;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.*;

public class Tasks {
    // begin task 1
    private <T, R> R transform(T value, Function<T, R> f) {
        return f.apply(value);
    }

    public List<String> split(String s) {
        return transform(s, str -> Arrays.asList(str.split(" ")));
    }

    public int count(List<?> list) {
        return transform(list, List::size);
    }
    // end task 1

    // begin task 4
    public Integer splitAndCountA(String s) {
        Function<String, List<String>> split = str -> Arrays.asList(str.split(" "));
        Function<List<?>, Integer> count = List::size;
        Function<String, Integer> splitAndCount = split.andThen(count);
        return splitAndCount.apply(s);
    }

    public Integer splitAndCountB(String s) {
        Function<String, List<String>> split = str -> Arrays.asList(str.split(" "));
        Function<List<?>, Integer> count = List::size;
        Function<String, Integer> splitAndCount = count.compose(split);
        return splitAndCount.apply(s);
    }
    // end task 4

    // begin task 6
    public Double max(double d1, double d2) {
        ToDoubleBiFunction<Double, Double> max = Math::max;
        return max.applyAsDouble(d1, d2);
    }
    // end task 6

    // begin task 7
    public Date getCurrentDate() {
        Supplier<Date> supplier = Date::new;
        return supplier.get();
    }
    // end task 7

    // begin task 9
    public boolean areEqual(Integer i1, Integer i2) {
        BiFunction<Integer, Integer, Boolean> function = Integer::equals;
        return function.apply(i1, i2);
    }
    // end task 9

}
