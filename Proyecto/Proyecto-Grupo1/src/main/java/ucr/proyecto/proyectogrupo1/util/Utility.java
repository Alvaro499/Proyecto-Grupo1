package ucr.proyecto.proyectogrupo1.util;

import ucr.proyecto.proyectogrupo1.Segurity.Cryptographic;
import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Security;
import ucr.proyecto.proyectogrupo1.domain.Supplier;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Utility {
    public static CircularLinkedList getLoginCircularLinkedList() {
        return loginCircularLinkedList;
    }

    public static void setLoginCircularLinkedList(CircularLinkedList loginCircularLinkedList) {
        Utility.loginCircularLinkedList = loginCircularLinkedList;
    }

    public static AVL getProductAVL() {
        return productAVL;
    }

    public static void setProductAVL(AVL productAVL) {
        Utility.productAVL = productAVL;
    }

    public static AVL getSupplierAVL() {
        return supplierAVL;
    }

    public static void setSupplierAVL(AVL supplierAVL) {
        Utility.supplierAVL = supplierAVL;
    }

    private static CircularLinkedList loginCircularLinkedList; //table security
    private static AVL productAVL; //table product
    private static AVL supplierAVL; //table supplier


    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);

        //Tabla security
        ArrayList<Security> security = new ArrayList<>();
        loginCircularLinkedList = new CircularLinkedList();
        //admin 0 a 999 | consulta 1000 a 1999 | cliente 2000 a 2999
        security.add(new Security(Integer.parseInt(Cryptographic.descodificar("208 144 96").trim()), Cryptographic.descodificar("592 383 318 421 279 174").trim(), Cryptographic.descodificar("592 383 318 421 279 174").trim()));
        security.add(new Security(Integer.parseInt(Cryptographic.descodificar("289 193 144 208 144 96").trim()), Cryptographic.descodificar("619 398 331 673 448 333 406 277 161").trim(), Cryptographic.descodificar("619 398 331 673 448 333 406 277 161").trim()));
        security.add(new Security(Integer.parseInt(Cryptographic.descodificar("290 194 144 208 144 96").trim()), Cryptographic.descodificar("598 385 318 669 443 342 261 197 96").trim(), Cryptographic.descodificar("598 385 318 669 443 342 261 197 96").trim()));
        for (int i = 0; i < security.size(); i++) {
            loginCircularLinkedList.add(security.get(i));
        }

        //Tabla Supplier
        supplierAVL = new AVL();
        ArrayList<Supplier> supplier = new ArrayList<>();
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("209 145 96").trim()), "Editorial Alma", 78904345, "info@editorialalma.com", "Calle Principal, Ciudad"));
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("210 146 96").trim()), "Esfera de los libros", 83940594, "contacto@esferadeloslibros.com", "Avenida Central, Ciudad"));
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("211 147 96").trim()), "Alianza", 69834759, "info@alianzaeditorial.com", "Calle Secundaria, Ciudad"));
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("212 148 96").trim()), "Diana", 74903928, "contacto@dianaeditorial.com", "Calle Principal, Ciudad"));
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("213 149 96").trim()), "Flamboyant", 83497459, "info@flamboyanteditorial.com", "Avenida Central, Ciudad"));
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("214 150 96").trim()), "Gredos", 64849403, "contacto@gredoseditorial.com", "Calle Secundaria, Ciudad"));
        supplier.add(new Supplier(Integer.parseInt(Cryptographic.descodificar("215 151 96").trim()), "B de bolsillo", 89409049, "info@bdebolsilloeditorial.com", "Avenida Central, Ciudad"));
        for (int i = 0; i < supplier.size(); i++) {
            supplierAVL.add(supplier.get(i));
        }

        //Tabla Product
        productAVL = new AVL();
        ArrayList<Product> product = new ArrayList<>();
        product.add(new Product(
                Integer.parseInt(Cryptographic.descodificar("209 145 96").trim()),
                Integer.parseInt(Cryptographic.descodificar("209 145 96").trim()),
                Cryptographic.descodificar("647 414 349 513 341 230 421 266 234 480 324 239 360 251 186 339 218 153 418 275 210 499 331 252 453 282 272 598 397 298 535 334 302 607 378 346 629 416 318 590 394 295 921 568 463 272 190 132 298 198 150 483 302 292 623 417 303 512 343 227 483 306 274 638 425 310 613 381 349 636 417 320 456 283 273 654 434 329 309 219 122 635 408 339 912 560 463 380 286 178 610 404 303 393 237 197 332 230 172 600 402 297 921 568 463 511 330 286 660 445 329 680 447 350 440 299 185 958 625 558 622 418 303 437 290 179").trim(),
                Cryptographic.descodificar("629 406 329 626 413 312 628 415 310 275 211 96").trim(),
                Double.parseDouble(Cryptographic.descodificar("312 207 153 284 190 142 208 144 96").trim()),
                Integer.parseInt(Cryptographic.descodificar("262 175 119").trim()),
                Integer.parseInt(Cryptographic.descodificar("252 167 117").trim()),
                Cryptographic.descodificar("684 452 348 516 343 231 498 332 285 495 330 211 612 409 301 658 443 329 614 412 307 645 428 318 625 418 304 642 426 327 628 423 313 577 367 321 530 374 265 616 411 310 488 342 245 620 407 310 639 425 317 613 387 340 662 445 334 472 309 210 590 394 295 447 299 195 404 254 202 452 302 249 360 255 157 457 307 251 442 295 245 603 402 301 455 306 250 353 251 152 312 207 158 310 209 154 296 200 143 316 214 157 326 214 167 324 216 160 305 201 152 322 212 164 450 299 246 459 307 258 414 279 167").trim()));
        for (int i = 0; i < product.size(); i++) {
            productAVL.add(product.get(i));
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
