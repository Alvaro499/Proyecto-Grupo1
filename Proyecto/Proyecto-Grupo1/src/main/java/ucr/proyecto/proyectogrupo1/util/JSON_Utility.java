package ucr.proyecto.proyectogrupo1.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

//Clase para obtener y guardar los datos en el archivo JSON, sin importar que tipo de TDA contenga los datos
public class JSON_Utility {

    private String pathJSON;//ruta del archivo
    private File itExistFile;
    private File file;
    private String fileNameAndExtension;

    /*
    customers.json --> singlyLinkedList
    productos.json --> AVL
    proveedores.json --> AVL
    security.json --> circularLinkedList
    inventario.json --> Btree
    gestion_pedidos.josn --> AVL
    prevision_demanda.josn --> BST
    control_costos.josn --> HeaderLinkedQueue
    * */

    //Le indicdo a traves del constructor, de cual archivo JSON quiero obtener datos
    //como los de arriba


    public JSON_Utility() {
    }



    //MANIPULACION DE DATOS DE LA CLASE SECURITY
    public CircularLinkedList getSecurityCircularLinkedList() throws IOException {
        String pathJSON = "securityData.txt";
        CircularLinkedList circularLinkedList = new CircularLinkedList();
        ArrayList<Security> arrayList = new ArrayList<>();
        //String que contendra el formatoJSON
        String output = "";
        file = new File(pathJSON);
        if(file.exists()){

            //archivo existe
            System.out.println("Archivo existe");
            try {
                //leemos los datos del archivo JSON indicado
                output = new String(Files.readAllBytes(Paths.get(pathJSON)));

                if (output.equals("") || output == null){
                    //si no hay nada en el jsonFile, lista vacia, para comenzar a llenar manualmente
                    return circularLinkedList;

                }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                    ObjectMapper om = new ObjectMapper();
                    //deserealizamos
                    arrayList = om.readValue(
                            output, new TypeReference<ArrayList<Security>>(){});

                    for (Security list: arrayList) {
                        circularLinkedList.add(list);
                    }
                    return circularLinkedList;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Archivo no existe, se crea");
            file = new File(pathJSON);
            file.createNewFile();
            return circularLinkedList;
        }

    }

    public void saveSecurityCircularLinkedList(CircularLinkedList circularLinkedList) throws ListException, IOException {
        String pathJSON = "securityData.txt";
        ArrayList<Security> arrayList = new ArrayList<>();

        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!circularLinkedList.isEmpty()) {

            int count = 1;
            int size = circularLinkedList.size();

            while (count <= size) {
                //guardamos el valor del nodo
                arrayList.add( (Security) circularLinkedList.getNode(count).data);
                count++;
            }
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    //MANTENIMIENTO DE CUSTOMER
    public SinglyLinkedList getCustomerInSinglyLinkedList() throws IOException {
        String pathJSON = "customerData.txt";
        ArrayList<Customer> arrayList = new ArrayList<>();
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        //String que contendra el formatoJSON
        String output = "";
        file = new File(pathJSON);
        if(file.exists()){
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return singlyLinkedList;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Customer>>(){});

                for (Customer list: arrayList) {
                    singlyLinkedList.add(list);
                }
                return singlyLinkedList;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return singlyLinkedList;
        }
    }

    public void saveCustomerSinglyLinkedList(SinglyLinkedList singlyLinkedList) throws ListException, IOException {
        String pathJSON = "customerData.txt";
        ArrayList<Customer> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!singlyLinkedList.isEmpty()) {
            int count = 1;
            int size = singlyLinkedList.size();

            while (count <= size) {
                //guardamos el valor del nodo
                arrayList.add( (Customer) singlyLinkedList.getNode(count).data);
                count++;
            }

            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //CONTROL DE COSTOS (manejar productos con TDA Header LinkedQueue)
    public HeaderLinkedQueue getProductHeaderLinkedQueue() throws IOException, QueueException {
        String pathJSON = "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        HeaderLinkedQueue headerLinkedQueue = new HeaderLinkedQueue();
        //String que contendra el formatoJSON
        file = new File(pathJSON);

        if(file.exists()){
            String output = "";
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return headerLinkedQueue;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Product>>(){});

                for (Product list: arrayList) {
                    headerLinkedQueue.enQueue(list);
                }
                return headerLinkedQueue;
            }

        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return headerLinkedQueue;
        }
    }

    public void saveProductHeaderLinkedQueue(HeaderLinkedQueue headerLinkedQueue) throws ListException, QueueException, IOException {
        String pathJSON = "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();

        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!headerLinkedQueue.isEmpty()) {

            HeaderLinkedQueue aux = new HeaderLinkedQueue();
            Product queueToArray = null;
            while (!headerLinkedQueue.isEmpty()) {
                queueToArray = (Product) headerLinkedQueue.deQueue();
                arrayList.add(queueToArray);
                aux.enQueue(queueToArray);

            }
            //Volvemos a llenar la lista original
            while (!aux.isEmpty()) {
                headerLinkedQueue.enQueue(aux.deQueue());
            }

            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //MANTENIMIENTO DE PRODUCTOS
    public AVL getProductAVL() throws IOException, QueueException {
        String pathJSON = "productData.txt";;
        ArrayList<Product> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if(file.exists()){

            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return avl;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Product>>(){});

                for (Product list: arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }

    public void saveProductAVL(AVL avl) throws ListException, QueueException, IOException {
        String pathJSON = "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!avl.isEmpty()) {
            inOrderProductoFirst(avl,arrayList);
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Metodo auxiliar para ayudar a recorrer los arboles

    public void inOrderProductoFirst(AVL avl, ArrayList<Product> arrayList){
        inOrderProductSecond(avl.root, arrayList);
    }
    public void inOrderProductSecond(BTreeNode node, ArrayList<Product> arrayList){

        if (node != null){
            inOrderProductSecond(node.left,arrayList);
            arrayList.add((Product) node.data);
            inOrderProductSecond(node.right,arrayList);
        }
    }


    //MANTENIMIENTO DE PROVEEDORES
    public AVL getSupplierAVL() throws IOException, QueueException {
        String pathJSON = "supplierData.txt";;
        ArrayList<Supplier> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);
        if(file.exists()){

            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return avl;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Supplier>>(){});

                for (Supplier list: arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return  avl;
        }
    }

    public void saveSupplierAVL(AVL avl) throws ListException, QueueException, IOException {
        String pathJSON = "supplierData.txt";
        ArrayList<Supplier> arrayList = new ArrayList<>();

        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!avl.isEmpty()) {

            inOrderSupplierFirst(avl,arrayList);
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void inOrderSupplierFirst(AVL avl, ArrayList<Supplier> arrayList){
        inOrderSupplierSecond(avl.root, arrayList);
    }
    public void inOrderSupplierSecond(BTreeNode node, ArrayList<Supplier> arrayList){

        if (node != null){
            inOrderSupplierSecond(node.left,arrayList);
            arrayList.add((Supplier) node.data);
            inOrderSupplierSecond(node.right,arrayList);
        }
    }


    //Manejo de OrderDetail (AVL)

    public AVL getOrderDetailAVL() throws IOException {
        String pathJSON = "orderDetailData.txt";;
        ArrayList<OrderDetail> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if(file.exists()){
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return avl;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<OrderDetail>>(){});

                for (OrderDetail list: arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveOrderDetailAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = "orderDetailData.txt";
        ArrayList<OrderDetail> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        //Se vacia la AVL con el metodo get()
        for (int i = 0; i < avl.size() ; i++) {
            arrayList.add( (OrderDetail) avl.get(i));
        }
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //MANEJO DE ORDER (AVL)

    public AVL getOrderAVL() throws IOException {
        String pathJSON = "orderData.txt";;
        ArrayList<Order> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if(file.exists()){
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return avl;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Order>>(){});

                for (Order list: arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveOrderAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = "orderData.txt";
        ArrayList<Order> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        //Se vacia la AVL con el metodo get()
        for (int i = 0; i < avl.size() ; i++) {
            arrayList.add( (Order) avl.get(i));
        }
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        try {
            om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    //Manejo de clase Sale (AVL)

    public AVL getSaleAVL() throws IOException {
        String pathJSON = "saleData.txt";;
        ArrayList<Sale> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if(file.exists()){
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return avl;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Sale>>(){});

                for (Sale list: arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveSaleAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = "saleData.txt";
        ArrayList<Sale> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        //Se vacia la AVL con el metodo get()
        for (int i = 0; i < avl.size() ; i++) {
            arrayList.add( (Sale) avl.get(i));
        }
        //Les especificaciomos a este constructor JSON que debe guardar toda instancia
        //tipo fecha/hora con el formato indicado en la clase Sale
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        try {
            om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    //Manejo de clase SaleDetail (AVL)

    public AVL getSaleDetailAVL() throws IOException {
        String pathJSON = "saleDetailData.txt";;
        ArrayList<SaleDetail> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if(file.exists()){
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return avl;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<SaleDetail>>(){});

                for (SaleDetail list: arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveSaleDetailAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = "saleDetailData.txt";
        ArrayList<SaleDetail> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        //Se vacia la AVL con el metodo get()
        for (int i = 0; i < avl.size() ; i++) {
            arrayList.add( (SaleDetail) avl.get(i));
        }
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    //CONTROL DE INVENTARIO (manejo de productos con Btree en vez de AVL)
    public BTree getInventoryBtree() throws IOException, QueueException {
        String pathJSON = "productData.txt";;
        ArrayList<Product> arrayList = new ArrayList<>();
        BTree bTree = new BTree();
        file = new File(pathJSON);

        if(file.exists()){

            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null){
                //arrayList = new ArrayList<Customer>();
                return bTree;

            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Product>>(){});

                for (Product list: arrayList) {
                    bTree.add(list);
                }
                return bTree;
            }
        }else{
            file = new File(pathJSON);
            file.createNewFile();
            return bTree;
        }
    }

    public void saveInventoryBtree(BTree bTree) throws ListException, QueueException, IOException {
        String pathJSON = "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()){//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }else{
            System.out.println("El archivo " +  pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!bTree.isEmpty()) {
            inOrderinventoryFirst(bTree,arrayList);
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Metodo auxiliar para ayudar a recorrer los arboles

    public void inOrderinventoryFirst(BTree bTree, ArrayList<Product> arrayList){
        inOrderinventorySecond(bTree.root, arrayList);
    }
    public void inOrderinventorySecond(BTreeNode node, ArrayList<Product> arrayList){

        if (node != null){
            inOrderinventorySecond(node.left,arrayList);
            arrayList.add((Product) node.data);
            inOrderinventorySecond(node.right,arrayList);
        }
    }




    //GESTION DE PEDIDOS (uso de las clases Order-OrderDetail con la TDA AVL)

//    public AVL getOrderDetailAVL() throws IOException, QueueException {
//        String pathJSON = "orderDetailData.txt";;
//        ArrayList<OrderDetail> arrayList = new ArrayList<>();
//        AVL avl = new AVL();
//        file = new File(pathJSON);
//        if(file.exists()){
//
//            String output = "";
//            //leemos los datos del archivo JSON indicado
//            output = new String(Files.readAllBytes(Paths.get(pathJSON)));
//
//            if (output.equals("") || output == null){
//                //arrayList = new ArrayList<Customer>();
//                return avl;
//
//            }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
//                ObjectMapper om = new ObjectMapper();
//                om.registerModule(new JavaTimeModule());
//                //llenamos el ArrayList con la info del JSON file
//                //(se deserializa)
//                arrayList = om.readValue(
//                        output, new TypeReference<ArrayList<Supplier>>(){});
//
//                for (Supplier list: arrayList) {
//                    avl.add(list);
//                }
//                return avl;
//            }
//        }else{
//            file.createNewFile();
//            return  avl;
//        }
//    }
//
//    public void saveSupplierAVL(AVL avl) throws ListException, QueueException, IOException {
//        String pathJSON = "productData.txt";
//        ArrayList<Supplier> arrayList = new ArrayList<>();
//
//        File file = new File(pathJSON);
//
//        if (file.exists()){//se eliminar el archivo json
//            file.delete();
//            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
//        }else{
//            System.out.println("El archivo " +  pathJSON + "no existe");
//            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
//        }
//
//        if (!avl.isEmpty()) {
//
//            inOrderSupplierFirst(avl,arrayList);
//            ObjectMapper om = new ObjectMapper();
//            try {
//                om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
































//
//    public void inOrderSupplierFirst(AVL avl, ArrayList<Supplier> arrayList){
//        inOrderSupplierSecond(avl.root, arrayList);
//    }
//    public void inOrderSupplierSecond(BTreeNode node, ArrayList<Supplier> arrayList){
//
//        if (node != null){
//            inOrderSupplierSecond(node.left,arrayList);
//            arrayList.add((Supplier) node.data);
//            inOrderSupplierSecond(node.right,arrayList);
//        }
//    }



//    public JSON_Utilityy(String fileNameAndExtension) {
//        //Obtener el nombre del archivo
//        this.fileNameAndExtension = fileNameAndExtension;
//        itExistFile = new File(this.fileNameAndExtension);
//
//        //Obtener la ruta absoluta
//        pathJSON = itExistFile.getAbsolutePath();
//        //verificamos que exista el archivo
//        file = new File(pathJSON);
//
//        if (!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                System.out.println("Error, no se ha podido crear el archivo. Por favor intentelo mas tarde");
//                throw new RuntimeException(e);
//            }
//        }
//        pathJSON = file.getAbsolutePath();
//    }
//
//    public String getFileNameAndExtension() {
//        return fileNameAndExtension;
//    }
//
//    //Se lee el JSON.file y se regresa como un String su informacion
//    public String readObjectsJSON(){
//        String output = "";
//
//        try {
//            //lee todos los bytes presentes en el archivos JSON y los convierte a
//            //String
//            output = new String(Files.readAllBytes(Paths.get(pathJSON)));
//            System.out.println(output);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return output;
//    }
//
//    //Transformar los Objetos en formato JSON a elementos de un ArrayList
//    public ArrayList<Object> listOfObjects() throws IOException {
//
//        ArrayList<Object> inspectors = null;
//        //si no hay nada en el JSON.file se envia vacia la lista para comenzar a trabajar
//        String jsonFile = readObjectsJSON();
//        if (jsonFile.equals("") || jsonFile == null){
//            inspectors = new ArrayList<Object>();
//            return inspectors;
//            //si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
//        }else{
//            ObjectMapper om = new ObjectMapper();
//            //llenamos el ArrayList con la info del JSON file
//            //(se deserializa)
//            inspectors = om.readValue(
//                    jsonFile, new TypeReference<ArrayList<Object>>(){});
//            return inspectors;
//        }
//    }
//
//    //Obtener el dato del JSON como cualquier TDA
//    public SinglyLinkedList getDataInSinglyLinkedList() throws IOException {
//        ArrayList<Customer> arrayList = null;
//        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
//        String jsonFile = readObjectsJSON();
//
//        if (jsonFile.equals("") || jsonFile == null){
//            //arrayList = new ArrayList<Customer>();
//            return singlyLinkedList;
//
//        }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
//            ObjectMapper om = new ObjectMapper();
//            //llenamos el ArrayList con la info del JSON file
//            //(se deserializa)
//            arrayList = om.readValue(
//                    jsonFile, new TypeReference<ArrayList<Customer>>(){});
//
//            for (Customer list: arrayList) {
//                singlyLinkedList.add(list);
//            }
//            return singlyLinkedList;
//        }
//    }
//
//
//    public CircularLinkedList getDataInCircularLinkedList() throws IOException {
//        ArrayList<Security> arrayList = null;
//        CircularLinkedList circularLinkedList = new CircularLinkedList();
//        String jsonFile = readObjectsJSON();
//
//        if (jsonFile.equals("") || jsonFile == null){
//            //arrayList = new ArrayList<Security>();
//            return circularLinkedList;
//
//        }else{//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
//            ObjectMapper om = new ObjectMapper();
//            //llenamos el ArrayList con la info del JSON file
//            //(se deserializa)
//            arrayList = om.readValue(
//                    jsonFile, new TypeReference<ArrayList<Customer>>(){});
//
//            for (Security list: arrayList) {
//                circularLinkedList.add(list);
//            }
//            return circularLinkedList;
//        }
//    }
//
////    public CircularLinkedList getDataInCircularLinkedList() throws IOException {
////        CircularLinkedList circularLinkedList = new CircularLinkedList();
////        for (Object list: listOfObjects()) {
////            circularLinkedList.add(list);
////        }
////        return circularLinkedList;
////    }
//
//    public BTree getDataInBTree() throws IOException {
//        BTree bTree = new BTree();
//        for (Object list: listOfObjects()) {
//            bTree.add(list);
//        }
//        return bTree;
//    }
//
//    public AVL getDataInBTreeAVL() throws IOException {
//        AVL bTreeAVL = new AVL();
//        for (Object list: listOfObjects()) {
//            bTreeAVL.add(list);
//        }
//        return bTreeAVL;
//    }
//
//    public BST getDataInBTreeBST() throws IOException {
//        BST bTreeBST = new BST();
//        for (Object list: listOfObjects()) {
//            bTreeBST.add(list);
//        }
//        return bTreeBST;
//    }
//
//    //ORIGINAL
////    public ArrayList<Object> getAllObjectsJSON() throws JsonProcessingException {
////
////        return listOfObjects();
////    }
//
//    //GUARDAR LOS DATOS DE NUEVO EN LOS ARCHIVOS
//
//
//    //Reemplazar los datos actuales del archivo, por los actualizados provenientes de las TDA
//    public boolean replaceAndUpdate(Object tda) throws ListException, TreeException, IOException {
//
//        //Dependiendo del tipo de tda que sea, guardamos los nuevos datos manejados desde las listas
//        //globales, en el archivo JSON
//        boolean saved;
//        if (tda instanceof SinglyLinkedList){
//            updateDataAsSinglyLinkedList((SinglyLinkedList) tda);
//
//        } else if (tda instanceof CircularLinkedList) {
//
//            updateDataAsCircularLinkedList((CircularLinkedList) tda);
//
//        } else if (tda instanceof AVL) {
//
//            updateDataAsAVL((AVL) tda);
//
//        } else if (tda instanceof BST) {
//            updateDataAsBST((BST) tda);
//
//        } else if (tda instanceof BTree) {
//
//            updateDataAsBTree((BTree) tda);
//
//        }else saved = true;
//
//    }
//
//    public void updateDataAsCircularLinkedList(CircularLinkedList list) throws ListException, IOException {
//
//        ArrayList<Security> arrayList = null;
//        boolean upadted = false;
//        if (list.isEmpty()) {
//            arrayList = new ArrayList<Security>();
//        } else {
//            int count = 1;
//            int size = list.size();
//            while (count <= size) {
//                //guardamos el valor del nodo
//                arrayList.add((Security) list.getNode(count).data);
//                count++;
//            }
//            upadted = true;
//        }
//        ObjectMapper om = new ObjectMapper();
//        om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
//    }
//
//    public void updateDataAsSinglyLinkedList(SinglyLinkedList list) throws ListException, IOException {
//
//        ArrayList<Customer> arrayList = null;
//        boolean upadted = false;
//        if (list.isEmpty()) {
//            arrayList = new ArrayList<>();//se guarda la lista vacia
//        } else {
//            int count = 1;
//            int size = list.size();
//            while (count <= size) {
//                //guardamos el valor del nodo
//                arrayList.add((Customer) list.getNode(count).data);
//                count++;
//            }
//            upadted = true;
//        }
//        ObjectMapper om = new ObjectMapper();
//        om.writeValue(Paths.get(pathJSON).toFile(),arrayList);
//    }
//
////    public boolean updateDataAsBTree(BTree tree) throws TreeException {
////
////        boolean updated = false;
////        if (tree.isEmpty()) {
////            updated = true;
////        }else {
////            InOrder(tree,arrayList);
////            updated = true;
////        }
////        return updated;
////    }
//
//    public void updateDataAsAVL(AVL tree) throws IOException {
//        ArrayList<Product> arrayListProduct = null;
//        ArrayList<Supplier> arrayListSupplier = null;
//
//        if (getFileNameAndExtension().equals("products.json")){
//
//            if (tree.isEmpty()) {
//                arrayListProduct = new ArrayList<>();
//            }else {
//
//                InOrder(tree,arrayListProduct);
//            }
//            ObjectMapper om = new ObjectMapper();
//            om.writeValue(Paths.get(pathJSON).toFile(),arrayListProduct);
//
//        }else{
//
//            if (tree.isEmpty()) {
//                arrayListSupplier = new ArrayList<>();
//            }else {
//
//                InOrder(tree,arrayListSupplier);
//            }
//            ObjectMapper om = new ObjectMapper();
//            om.writeValue(Paths.get(pathJSON).toFile(),arrayListSupplier);
//
//        }
//    }
//
//    public void updateDataAsBST(BST tree){
//        boolean updated = false;
//        return updated;
//    }
//
//    public void InOrder(BTree tree, ArrayList<Supplier> arrayList) throws TreeException {
//        inOrder(tree.getRoot(),arrayList);
//    }
//
//    public void InOrder(BTree tree, ArrayList<Product> arrayList) throws TreeException {
//        inOrder(tree.getRoot(),arrayList);
//    }
//
//
//
//
//    //metodo interno
//    //preOrder: left-node-right
//    private Object inOrder(BTreeNode node,ArrayList<Object> arrayList){
//        Object resultNode = null;
//        if(node!=null){
//            arrayList.add(inOrder(node.left,arrayList));
//            arrayList.add(node.data);
//            arrayList.add(inOrder(node.right,arrayList));
//        }
//        return resultNode;
//    }
}
