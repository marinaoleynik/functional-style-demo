package it.discovery.marina;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by Марина on 16.02.2017.
 */
public class FunctionStyleDemo {
    public static void main(String[] args) {
        System.out.println(FunctionStyleDemo.countOfDigits("123"));
        System.out.println(FunctionStyleDemo.countOfDigits("abc123"));
        System.out.println(FunctionStyleDemo.countOfDigits("4abc123"));
        System.out.println(FunctionStyleDemo.countOfDigits(null));
        System.out.println(FunctionStyleDemo.countOfDigits(""));
        System.out.println(FunctionStyleDemo.countOfDigits("abc"));

        getStream(null);
        System.out.println(getStream(null).toString());

  }

    static long countOfDigits(String incomingString) {
        return getStream(incomingString).map((value) -> value.filter(Character::isDigit))
                                        .map(IntStream::count)
                                        .orElse(0L);
    }

    static Optional<IntStream> getStream(String incomingString) {
        return Optional.ofNullable(incomingString).map(String::chars);
    }

}
