package util;

import TDA.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utility {
    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static int random(){
        return 1+(int) Math.floor(Math.random()*99);
    }

    public static int random(int bound){
        //return 1+random.nextInt(bound);
        return 1+(int) Math.floor(Math.random()*bound);
    }

    public static int random(int lowBound, int highBound){
        int value=0;
        do{
            value = lowBound+(int) Math.floor(Math.random()*highBound);
        }while(!isBetween(value, lowBound, highBound));
        return value;
    }

    public static boolean isBetween(int value, int low, int high) {
        return low <= value && value <= high;
    }

    public static String format(double value){
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }

    public static String perFormat(double value){
        //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }

    public static String dateFormat(Date value){
        return new SimpleDateFormat("dd/MM/yyyy")
                .format(value);
    }

    /**
     * a < b return -1
     * a > b return 1
     * a == b return 0
     * @param a
     * @param b
     * @return
     **/
    public static int compare(Object a, Object b) {
        switch(instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1 < int2? -1 :
                        int1 > int2? 1 : 0; //0==equal
            case "String":
                String str1 = (String)a; String str2 = (String)b;
                return str1.compareToIgnoreCase(str2)<0? -1 :
                        str1.compareToIgnoreCase(str2)>0? 1 : 0;
            case "Character":
                Character ch1 = (Character)a; Character ch2 = (Character)b;
                return ch1.compareTo(ch2)<0? -1 :
                        ch1.compareTo(ch2)>0? 1 : 0;
            case "SinglyLinkedList":
                SinglyLinkedList singlyLinkedList1 = (SinglyLinkedList) a; SinglyLinkedList singlyLinkedList2 = (SinglyLinkedList) b;
                return singlyLinkedList1.toString().compareToIgnoreCase(singlyLinkedList2.toString())<0? -1 :
                        singlyLinkedList1.toString().compareToIgnoreCase(singlyLinkedList2.toString())>0? 1 : 0;

            case "CircularLinkedList":
                CircularLinkedList circularLinkedList1 = (CircularLinkedList) a; CircularLinkedList circularLinkedList2 = (CircularLinkedList) b;
                return circularLinkedList1.toString().compareToIgnoreCase(circularLinkedList2.toString())<0? -1 :
                        circularLinkedList1.toString().compareToIgnoreCase(circularLinkedList2.toString())>0? 1 : 0;
            case "ArrayStack":
                ArrayStack arrayStack1 = (ArrayStack) a; ArrayStack arrayStack2 = (ArrayStack) b;
                return arrayStack1.toString().compareToIgnoreCase(arrayStack2.toString())<0? -1 :
                        arrayStack1.toString().compareToIgnoreCase(arrayStack2.toString())>0? 1 : 0;
            case "LinkedQueue":
                LinkedQueue linkedQueue1 = (LinkedQueue) a; LinkedQueue linkedQueue2 = (LinkedQueue) b;
                return linkedQueue1.toString().compareToIgnoreCase(linkedQueue2.toString())<0? -1 :
                        linkedQueue1.toString().compareToIgnoreCase(linkedQueue2.toString())>0? 1 : 0;
            case "BTree":
                BTree bTree1 = (BTree) a; BTree bTree2 = (BTree) b;
                return bTree1.toString().compareToIgnoreCase(bTree2.toString())<0? -1 :
                        bTree1.toString().compareToIgnoreCase(bTree2.toString())>0? 1 : 0;
        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a){
        if(a instanceof Integer) return "Integer";
        if(a instanceof String) return "String";
        if(a instanceof Character) return "Character";
        if (a instanceof ArrayStack) return "ArrayStack";
        if (a instanceof LinkedQueue) return "LinkedQueue";
        if (a instanceof BTree) return "BTree";
        return "Unknown"; //desconocido
    }

    public static String instanceOf(Object a, Object b){
        if(a instanceof Integer&&b instanceof Integer) return "Integer";
        if(a instanceof String&&b instanceof String) return "String";
        if(a instanceof Character&&b instanceof Character) return "Character";
        if (a instanceof SinglyLinkedList && b instanceof SinglyLinkedList) return "SinglyLinkedList";
        if (a instanceof CircularLinkedList && b instanceof CircularLinkedList) return "CircularLinkedList";
        if (a instanceof ArrayStack && b instanceof ArrayStack) return "ArrayStack";
        if (a instanceof LinkedQueue && b instanceof LinkedQueue) return "LinkedQueue";
        if (a instanceof BTree && b instanceof BTree) return "BTree";
        return "Unknown"; //desconodo
    }

    public static String getPersonName() {
        String list[] = {"Donald", "Felipe", "Margos", "Dario",
                "Ana", "Rosa", "Guadalupe", "Christian",
                "Lorena", "Pedro", "Juan", "Federico",
                "Garita", "Adrian", "Marco", "Luz",
                "Daneil", "Mireya", "Fabina", "Ashley",
                "Honduras", "Pamela", "Carla", "Carlos",
                "Pablo", "Japon", "Valeria", "Teresa",
                "Jose", "Diego", "Helena", "Isabelle",
        };
        //return list[(int) (Math.random() * 31 - 1)];
        return list[random(31)];
    }

    public static String getLetter() {
        String list[] = {"a", "b", "c", "d",
                "e", "f", "g", "h",
                "i", "j", "k", "l",
                "m", "n", "o", "p",
                "q", "r", "s", "t",
                "u", "v", "w", "x",
                "y", "z"
        };
        //return list[(int) (Math.random() * 31 - 1)];
        return list[random(25)];
    }
}
