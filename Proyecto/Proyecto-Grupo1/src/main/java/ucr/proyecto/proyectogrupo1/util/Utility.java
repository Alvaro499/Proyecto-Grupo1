package ucr.proyecto.proyectogrupo1.util;

import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utility {
    private static CircularLinkedList loginCircularLinkedList; //table security
    private static AVL productAVL; //table product
    private static AVL supplierAVL; //table supplier
    private static SinglyLinkedList clientSinglyLinkedList; //table customer
    private static AVL order; //table order
    private static AVL orderDetail;// table orderDetail
    private static AVL sale;
    private static AVL saleDetail;
    private static Integer IDClient;
    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed
    private static JSON_Utility json;

    // static initializer
    static {
        json = new JSON_Utility();
        //instanciamos los arboles
        sale = new AVL();
        saleDetail = new AVL();
        clientSinglyLinkedList = new SinglyLinkedList();
        loginCircularLinkedList = new CircularLinkedList();
        supplierAVL = new AVL();
        productAVL = new AVL();
        order = new AVL();
        orderDetail = new AVL();
        //llenamos los arboles
        try {
            productAVL = json.getProductAVL();
            supplierAVL = json.getSupplierAVL();
            loginCircularLinkedList = json.getSecurityCircularLinkedList();
            clientSinglyLinkedList = json.getCustomerInSinglyLinkedList();
            sale = json.getSaleAVL();
            saleDetail = json.getSaleDetailAVL();
            order = json.getOrderAVL();
            orderDetail = json.getOrderDetailAVL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (QueueException e) {
            throw new RuntimeException(e);
        }


        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
        //tabla cliente
        //clientSinglyLinkedList.add(new Customer(123456789, "Jean", "70790629", "zjeancarlo42@gmail.com", "Cartago"));

        //Tabla security
        //admin 0 a 999 | consulta 1000 a 1999 | cliente 2000 a 2999
        /*loginCircularLinkedList.add(new Security(0, "Admin", "Admin"));
        loginCircularLinkedList.add(new Security(1000, "Consulta", "Consulta"));
        loginCircularLinkedList.add(new Security(123456789, "Cliente", "Cliente"));*/

        //Tabla Supplier
        /*supplierAVL.add(new Supplier(1, "Editorial Alma", 78904345, "info@editorialalma.com", "Calle Principal, Ciudad"));
        supplierAVL.add(new Supplier(2, "Esfera de los libros", 83940594, "contacto@esferadeloslibros.com", "Avenida Central, Ciudad"));
        supplierAVL.add(new Supplier(3, "Alianza", 69834759, "info@alianzaeditorial.com", "Calle Secundaria, Ciudad"));
        supplierAVL.add(new Supplier(4, "Diana", 74903928, "contacto@dianaeditorial.com", "Calle Principal, Ciudad"));
        supplierAVL.add(new Supplier(5, "Flamboyant", 83497459, "info@flamboyanteditorial.com", "Avenida Central, Ciudad"));
        supplierAVL.add(new Supplier(6, "Gredos", 64849403, "contacto@gredoseditorial.com", "Calle Secundaria, Ciudad"));
        supplierAVL.add(new Supplier(7, "B de bolsillo", 89409049, "info@bdebolsilloeditorial.com", "Avenida Central, Ciudad"));
*/
        //Tabla Product
        /*productAVL.add(new Product(
                "9788418008658",
                1,
                "Autor: LOUISA MAY ALCOTT\n" +
                        "Fecha de publicación: 2022\n" +
                        "Formato: Pasta suave\n" +
                        "Idioma: Español\n" +
                        "Tema(s): Ficción,Literatura,Clásicos",
                "Mujercitas",
                6900.00,
                0,
                15,
                "https://www.libreriainternacional.com/media/catalog/product/cache/4b453cb5481e21beed80ec4214570591/9/7/9788418008658_1.jpg"));

        productAVL.add(new Product(
                "9788490607725",
                2,
                "Autor: JAVIER SANTAOLALLA\nFecha de publicación: 2018\nFormato: Pasta suave\nIdioma: Español\nTema(s): No Ficción,Referencia,Ciencia",
                "El Bosón de Higgs No Te Va a Hacer la Cama",
                18000.00,
                Utility.random(100),
                Utility.random(10, 50),
                "https://www.libreriainternacional.com/media/catalog/product/cache/4b453cb5481e21beed80ec4214570591/9/7/9788490607725_4fp637xaigispfzz.jpg"));

        productAVL.add(new Product(
                "9788491047087",
                3,
                "Autor: EDGAR ALLAN POE\nFecha de publicación: 2017\nFormato: Pasta suave\nIdioma: Español\nTema(s): Ficción,Literatura,Poesía",
                "Cuervo y otros poemas",
                11900.00,
                Utility.random(100),
                Utility.random(10, 50),
                "https://www.libreriainternacional.com/media/catalog/product/cache/4b453cb5481e21beed80ec4214570591/9/7/9788491047087_1.jpg"));

        productAVL.add(new Product(
                "9786070728792",
                4,
                "Autor: GABRIEL GARCIA MARQUEZ\nFecha de publicación: 2015\nFormato: Pasta suave\nIdioma: Español\nTema(s): Ficción,Literatura,Latinoamericana",
                "Cien años de soledad",
                11900.00,
                Utility.random(100),
                Utility.random(10, 50),
                "https://www.libreriainternacional.com/media/catalog/product/cache/4b453cb5481e21beed80ec4214570591/9/7/9786070728792_1_1.jpg"));

        productAVL.add(new Product(
                "9786070729560",
                4,
                "Autor: GABRIEL GARCIA MARQUEZ\nFecha de publicación: 2015\nFormato: Pasta suave\nIdioma: Español\nTema(s): Ficción,Literatura,Latinoamericana",
                "Crónica de una muerte anunciada",
                8900.00,
                Utility.random(100),
                Utility.random(10, 50),
                "https://www.libreriainternacional.com/media/catalog/product/cache/4b453cb5481e21beed80ec4214570591/9/7/9786070729560_1.jpg"));

        productAVL.add(new Product(
                "9788418304316",
                5,
                "Autor: JACOB GRIMM\nFecha de publicación: 2021\nFormato: Pasta dura\nIdioma: Español\nTema(s): Infantil,3 a 5 años,Lectores tempranos\nCrónica de una muerte anunciada",
                "Caperucita roja",
                14800.00,
                Utility.random(100),
                Utility.random(10, 50),
                "https://www.libreriainternacional.com/media/catalog/product/cache/4b453cb5481e21beed80ec4214570591/9/7/9788418304316_1.jpg"));
*/
    }
    public static SinglyLinkedList getClientSinglyLinkedList() {
        return clientSinglyLinkedList;
    }

    public static void setClientSinglyLinkedList(SinglyLinkedList clientSinglyLinkedList) {
        Utility.clientSinglyLinkedList = clientSinglyLinkedList;
        try{
            json.saveCustomerSinglyLinkedList(Utility.getClientSinglyLinkedList());
        } catch (ListException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CircularLinkedList getLoginCircularLinkedList() {
        return loginCircularLinkedList;
    }

    public static void setLoginCircularLinkedList(CircularLinkedList loginCircularLinkedList) {
        Utility.loginCircularLinkedList = loginCircularLinkedList;
        try {
            json.saveSecurityCircularLinkedList(Utility.getLoginCircularLinkedList());
        } catch (ListException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AVL getProductAVL() {
        return productAVL;
    }

    public static void setProductAVL(AVL productAVL) {
        Utility.productAVL = productAVL;
        try {
            json.saveProductAVL(Utility.productAVL);
        } catch (ListException e) {
            throw new RuntimeException(e);
        } catch (QueueException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AVL getSupplierAVL() {
        return supplierAVL;
    }

    public static void setSupplierAVL(AVL supplierAVL) throws TreeException {
        Utility.supplierAVL = supplierAVL;
        try {
            json.saveSupplierAVL(Utility.supplierAVL);
        } catch (ListException e) {
            throw new RuntimeException(e);
        } catch (QueueException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AVL getOrder() {
        return order;
    }

    public static void setOrder(AVL order) {
        Utility.order = order;
        try {
            json.saveOrderAVL(Utility.order);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getIDClient() {
        return IDClient;
    }

    public static void setIDClient(Integer IDClient) {
        Utility.IDClient = IDClient;
    }

    public static AVL getOrderDetail() {
        return orderDetail;
    }

    public static void setOrderDetail(AVL orderDetail) {
        Utility.orderDetail = orderDetail;
        try {
            json.saveOrderDetailAVL(Utility.orderDetail);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    public static AVL getSale() {
        return sale;
    }

    public static void setSale(AVL sale) {
        Utility.sale = sale;
        try {
            json.saveSaleAVL(Utility.sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    public static AVL getSaleDetail() {
        return saleDetail;
    }

    public static void setSaleDetail(AVL saleDetail) {
        Utility.saleDetail = saleDetail;
        try {
            json.saveSaleDetailAVL(Utility.saleDetail);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    public static int random() {
        return 1 + (int) Math.floor(Math.random() * 99);
    }

    public static int random(int bound) {
        //return 1+random.nextInt(bound);
        return 1 + (int) Math.floor(Math.random() * bound);
    }

    public static int random(int lowBound, int highBound) {
        int value = 0;
        do {
            value = lowBound + (int) Math.floor(Math.random() * highBound);
        } while (!isBetween(value, lowBound, highBound));
        return value;
    }

    public static boolean isBetween(int value, int low, int high) {
        return low <= value && value <= high;
    }

    public static String format(double value) {
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }

    public static String perFormat(double value) {
        //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }

    public static String dateFormat(Date value) {
        return new SimpleDateFormat("dd/MM/yyyy")
                .format(value);
    }

    /**
     * a < b return -1
     * a > b return 1
     * a == b return 0
     *
     * @param a
     * @param b
     * @return
     **/
    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "Integer":
                Integer int1 = (Integer) a;
                Integer int2 = (Integer) b;
                return int1 < int2 ? -1 :
                        int1 > int2 ? 1 : 0; //0==equal
            case "String":
                String str1 = (String) a;
                String str2 = (String) b;
                return str1.compareToIgnoreCase(str2) < 0 ? -1 :
                        str1.compareToIgnoreCase(str2) > 0 ? 1 : 0;
            case "Character":
                Character ch1 = (Character) a;
                Character ch2 = (Character) b;
                return ch1.compareTo(ch2) < 0 ? -1 :
                        ch1.compareTo(ch2) > 0 ? 1 : 0;
            case "SinglyLinkedList":
                SinglyLinkedList singlyLinkedList1 = (SinglyLinkedList) a;
                SinglyLinkedList singlyLinkedList2 = (SinglyLinkedList) b;
                return singlyLinkedList1.toString().compareToIgnoreCase(singlyLinkedList2.toString()) < 0 ? -1 :
                        singlyLinkedList1.toString().compareToIgnoreCase(singlyLinkedList2.toString()) > 0 ? 1 : 0;

            case "CircularLinkedList":
                CircularLinkedList circularLinkedList1 = (CircularLinkedList) a;
                CircularLinkedList circularLinkedList2 = (CircularLinkedList) b;
                return circularLinkedList1.toString().compareToIgnoreCase(circularLinkedList2.toString()) < 0 ? -1 :
                        circularLinkedList1.toString().compareToIgnoreCase(circularLinkedList2.toString()) > 0 ? 1 : 0;
            case "ArrayStack":
                ArrayStack arrayStack1 = (ArrayStack) a;
                ArrayStack arrayStack2 = (ArrayStack) b;
                return arrayStack1.toString().compareToIgnoreCase(arrayStack2.toString()) < 0 ? -1 :
                        arrayStack1.toString().compareToIgnoreCase(arrayStack2.toString()) > 0 ? 1 : 0;
            case "LinkedQueue":
                LinkedQueue linkedQueue1 = (LinkedQueue) a;
                LinkedQueue linkedQueue2 = (LinkedQueue) b;
                return linkedQueue1.toString().compareToIgnoreCase(linkedQueue2.toString()) < 0 ? -1 :
                        linkedQueue1.toString().compareToIgnoreCase(linkedQueue2.toString()) > 0 ? 1 : 0;
            case "BTree":
                BTree bTree1 = (BTree) a;
                BTree bTree2 = (BTree) b;
                return bTree1.toString().compareToIgnoreCase(bTree2.toString()) < 0 ? -1 :
                        bTree1.toString().compareToIgnoreCase(bTree2.toString()) > 0 ? 1 : 0;
            case "Supplier":
                Supplier supplier1 = (Supplier) a;
                Supplier supplier2 = (Supplier) b;
                return supplier1.toString().compareToIgnoreCase(supplier2.toString()) < 0 ? -1 :
                        supplier1.toString().compareToIgnoreCase(supplier2.toString()) > 0 ? 1 : 0;
            case "Customer":
                Customer customer1 = (Customer) a;
                Customer customer2 = (Customer) b;
                return customer1.toString().compareToIgnoreCase(customer2.toString()) < 0 ? -1 :
                        customer1.toString().compareToIgnoreCase(customer2.toString()) > 0 ? 1 : 0;
            case "Security":
                Security security1 = (Security) a;
                Security security2 = (Security) b;
                return security1.toString().compareToIgnoreCase(security2.toString()) < 0 ? -1 :
                        security1.toString().compareToIgnoreCase(security2.toString()) > 0 ? 1 : 0;
            case "Order":
                Order order1 = (Order) a;
                Order order2 = (Order) b;
                return order1.toString().compareToIgnoreCase(order2.toString()) < 0 ? -1 :
                        order1.toString().compareToIgnoreCase(order2.toString()) > 0 ? 1 : 0;
            case "OrderDetail":
                OrderDetail orderDetail1 = (OrderDetail) a;
                OrderDetail orderDetail2 = (OrderDetail) b;
                return orderDetail1.toString().compareToIgnoreCase(orderDetail2.toString()) < 0 ? -1 :
                        orderDetail1.toString().compareToIgnoreCase(orderDetail2.toString()) > 0 ? 1 : 0;
            case "Product":
                Product product1 = (Product) a;
                Product product2 = (Product) b;
                return product1.toString().compareToIgnoreCase(product2.toString()) < 0 ? -1 :
                        product1.toString().compareToIgnoreCase(product2.toString()) > 0 ? 1 : 0;
            case "Sale":
                Sale sale1 = (Sale) a;
                Sale sale2 = (Sale) b;
                return sale1.toString().compareToIgnoreCase(sale2.toString()) < 0 ? -1 :
                        sale1.toString().compareToIgnoreCase(sale2.toString()) > 0 ? 1 : 0;
            case "SaleDetail":
                SaleDetail saleDetail1 = (SaleDetail) a;
                SaleDetail saleDetail2 = (SaleDetail) b;
                return saleDetail1.toString().compareToIgnoreCase(saleDetail2.toString()) < 0 ? -1 :
                        saleDetail1.toString().compareToIgnoreCase(saleDetail2.toString()) > 0 ? 1 : 0;
        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a) {
        if (a instanceof Integer) return "Integer";
        if (a instanceof String) return "String";
        if (a instanceof Character) return "Character";
        if (a instanceof ArrayStack) return "ArrayStack";
        if (a instanceof LinkedQueue) return "LinkedQueue";
        if (a instanceof BTree) return "BTree";
        return "Unknown"; //desconocido
    }

    public static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) return "Integer";
        if (a instanceof String && b instanceof String) return "String";
        if (a instanceof Character && b instanceof Character) return "Character";
        if (a instanceof SinglyLinkedList && b instanceof SinglyLinkedList) return "SinglyLinkedList";
        if (a instanceof CircularLinkedList && b instanceof CircularLinkedList) return "CircularLinkedList";
        if (a instanceof ArrayStack && b instanceof ArrayStack) return "ArrayStack";
        if (a instanceof LinkedQueue && b instanceof LinkedQueue) return "LinkedQueue";
        if (a instanceof BTree && b instanceof BTree) return "BTree";
        if (a instanceof Supplier && b instanceof Supplier) return "Supplier";
        if (a instanceof Customer && b instanceof Customer) return "Customer";
        if (a instanceof Security && b instanceof Security) return "Security";
        if (a instanceof Order && b instanceof Order) return "Order";
        if (a instanceof OrderDetail && b instanceof OrderDetail) return "OrderDetail";
        if (a instanceof Product && b instanceof Product) return "Product";
        if (a instanceof Sale && b instanceof Sale) return "Sale";
        if (a instanceof SaleDetail && b instanceof SaleDetail) return "SaleDetail";

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
