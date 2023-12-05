



public class Main {
    public static void main(String[] args){
        MyTreeMap<Integer, String> myMap = new MyTreeMap<>();
        myMap.put (1, "one");
        myMap.put (2, "two");
        myMap.put (3, "three");
        myMap.put (4, "four");
        myMap.put (-5, "-five");

        System.out.println(myMap.size());
        System.out.println(myMap.get(1));
        System.out.println(myMap.isEmpty());

        System.out.println(myMap.containsKey(2));

        System.out.println(myMap.containsVal("kklklk"));
        System.out.println(myMap.containsVal("another string"));

        System.out.println(myMap.keys());
        System.out.println(myMap.values());

        myMap.remove(1);
        myMap.remove(-5);
        myMap.remove(3);
        myMap.remove(4);


        System.out.println(myMap.keys());
        System.out.println(myMap.values());



    }
}