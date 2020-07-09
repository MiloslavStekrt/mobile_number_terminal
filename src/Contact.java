import java.util.Scanner;

public class Contact {
    private String name;
    private int number;
    private static int counter = 0;

    public Contact(String name, int number){
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
