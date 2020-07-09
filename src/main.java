import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {
    public static List<Contact> list = new ArrayList<Contact>();
    private static String rozdel = "===================================\n";

    public static void main(String[] args) throws IOException {
        System.out.println("Hey, How can i help you?");
        menu();
    }

    //create menu where can you select option
    public static void menu() throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println(rozdel+"a) print all contacts\nb) add new contact\nc) find in contacts\nd) delete from contacts");
        String select = s.nextLine().toLowerCase();
        System.out.println(rozdel);
        if (select.equals("a"))
            getContact_db();
        else if (select.equals("b")){
            String name = s.nextLine();
            int number = s.nextInt();
            numberIsBad(number);
            addContact_db(name,number);
        }else if (select.equals("c"))
            search_db();
        else if (select.equals("d"))
            delete_db();
    }

    //control valid number
    private static void numberIsBad(int number) {
        if(number <= 99 || number >= 999/*000000*/) {
            Scanner s = new Scanner(System.in);
            System.out.println("This number is bad please enter normal number");
            number = s.nextInt();
            numberIsBad(number);
        }
    }

    //write new contact to new line in number_db.csv file
    public static void addContact_db(String name, int number) throws IOException {
        FileWriter db = new FileWriter("number_db.csv", true);
        db.append(name+","+number+"\n");
        db.flush();
        db.close();
    }

    //can search number or string
    public static void search_db() throws IOException {
        Scanner s = new Scanner(System.in);
        //first read db and add then to list
        getList_sync();
        //get input world or int
        String inputWorld = s.nextLine().toLowerCase();
        int inputNumber;
        try{
            inputNumber = Integer.parseInt(inputWorld);
            for(Contact x: list){
                if (x.getNumber() == inputNumber){
                    System.out.println("K cislu "+x.getNumber()+" je spojeno jmeno "+x.getName());
                    break;
                }else{
                    System.out.println("cislo "+inputNumber+" neni spojeno zadne jmeno");
                }
            }
        }catch (Exception e){
            for (Contact x: list){
                if (x.getName().contains(inputWorld)){
                    System.out.println("Myslite "+x.getName()+" s cislem "+x.getNumber());
                    break;
                }else{
                    System.out.println("Bouzel neni zadna schoda v db");
                }
            }
        }
    }

    //write list and remove from list selected item and rewrite db file
    public static void delete_db() throws IOException {
        getList_sync();
        int counter = 0;
        for (Contact x : list) {
            counter++;
            System.out.println(counter+". jmeno " + x.getName() + " number " + x.getNumber());
        }
        Scanner s = new Scanner(System.in);
        int select = s.nextInt()-1;
        while (select > counter-1 || select < 0){
            if (select > counter - 1)
                System.out.println("Cislo je moc velke zkuste jine");
            else if (select < 0)
                System.out.println("Cislo je moc male zkuste jine");
            select = s.nextInt();
        }
        System.out.println("Byl odstranen kontakt na "+list.get(select).getName()+" s cislem "+list.get(select).getNumber());
        list.remove(select);
        FileWriter writer = new FileWriter("number_db.csv",false);
        for (Contact x:list){
            writer.append(x.getName()+","+x.getNumber()+"\n");
        }
        writer.flush();
        writer.close();
        getContact_db();
    }

    //set db file to list
    public static void getList_sync() throws IOException {
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader("number_db.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            list.add(new Contact(data[0].toLowerCase(),Integer.parseInt(data[1])));
        }
        csvReader.close();
    }

    //read number_db.csv
    public static void getContact_db() throws IOException {
        getList_sync();
        for (Contact c: list)
            System.out.println("jmeno je: "+c.getName()+" a cislo je "+c.getNumber());
    }
}
