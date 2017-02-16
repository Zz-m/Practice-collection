package hello;


import org.joda.time.LocalTime;

/**
 * t
 * Created by dhx on 2017/2/16.
 */
public class HelloWorld {
    public static void main(String[] args) {
        LocalTime currentTime = new LocalTime();
        System.out.println("The current local time is: " + currentTime);
        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());
    }
}
