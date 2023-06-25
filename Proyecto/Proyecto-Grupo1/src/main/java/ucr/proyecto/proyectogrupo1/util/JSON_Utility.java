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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

//Clase para obtener y guardar los datos en el archivo JSON, sin importar que tipo de TDA contenga los datos
public class JSON_Utility {

    private String pathJSON;//ruta del archivo
    private File itExistFile;
    private File file;
    private String ABOSLUTE_PATH;
    private File[] directoryList;

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


    public JSON_Utility() {
        createPathFather();
    }

    //Obtenemos la ruta actual donde se encuentran los archivos, y se los volvemos a pasar para evitar que se
    //creen en la raiz principal del proyecto
    private void getPath(){

        boolean pathExist = false;

        //Recorremos las carpetas obtenidas desde la carpeta padre ubicada
        //en la raiz del proyecto
        for (File allPaths: directoryList) {

            //Recorremos cada una de las carpetas
            File actualDirectory = new File(String.valueOf(allPaths));

            //Si la carpeta actual existe, o es una carpeta (en vez de archivos)
            //Obtenemos todo lo dentro de ella y verificamos si existe algo
            //si es asi entonces, ahi se ubican los archivos
            if (actualDirectory.exists() && actualDirectory.isDirectory()){
                File[] dataFiles = actualDirectory.listFiles(File::isFile);

                //Verificamos si lo obtenido de la carpeta esta vacio o lleno, si contiene algo
                //aqui se ubican los archivos
                if (dataFiles != null && dataFiles.length > 0){

                    //COMO AQUI SE UBICAN LOS ARCHIVOS, TOMAMOS SU RUTA PADRE Y SE LA DEFNIIMOS A LOS MISMOS ARCHIVOS
                    //ABOSLUTE_PATH = dataFiles[0].getAbsolutePath();
                    ABOSLUTE_PATH = dataFiles[0].getParent();
                    System.out.println("ruta abosluta del archivo actual: " + ABOSLUTE_PATH);
                    System.out.println("Ruta padre del archivo actual: " + dataFiles[0].getParent());
                    pathExist = true;
                    break;
                }
            }
        }
        //Si no existe ningun archivo, los creamos en cualquiera de las carpetas
        if (pathExist == false){
            ABOSLUTE_PATH = "Archivos\\Carpeta2";
        }
    }

    //Consigo la ruta absoluta de los archivos, donde el getPath() la distribuye a todos
    public void createPathFather(){
        File fileFather = new File("Archivos");

        //fileFather.listFiles();

        if (fileFather.exists() && fileFather.isDirectory()) {
            File[] subcarpetas = fileFather.listFiles(File::isDirectory);

            if (subcarpetas != null && subcarpetas.length > 0) {

                directoryList = subcarpetas;


               /* for (File subcarpeta : subcarpetas) {
                    System.out.println(subcarpeta.getName());
                }*/

                getPath();
            } else {
                System.out.println("La carpeta no contiene subcarpetas.");
            }
        } else {
            System.out.println("La carpeta no existe o no es una carpeta válida.");
        }
    }

    public void changePathConfig(String newPath) throws IOException {


        // Si la ruta actual de los archivos es la misma donde quiero moverlos,
        // no hacer nada
        if (newPath.equals(ABOSLUTE_PATH)) {
            return;
        }

        // Obtengo la ruta nueva y la ruta antigua
        File newPathLink = new File(newPath);
        File oldPathLink = new File(ABOSLUTE_PATH);

        // Verificar si la carpeta de origen existe y es un directorio
        if (!oldPathLink.exists() || !oldPathLink.isDirectory()) {
            System.out.println("La carpeta de origen no existe o no es un directorio válido");
            return;
        }

        // Verificar si la carpeta de destino existe y es un directorio
        if (!newPathLink.exists() || !newPathLink.isDirectory()) {
            System.out.println("La carpeta de destino no existe o no es un directorio válido");
            return;
        }

        // Obtener los archivos que hay en la ruta antigua
        File[] files = oldPathLink.listFiles();

        // Si no hay archivos, no hacer nada
        if (files == null || files.length == 0) {
            System.out.println("No hay archivos en la carpeta de origen");
            return;
        }

        // Vamos pasando los archivos uno por uno a la nueva carpeta
        for (File actualFile : files) {
            Path oldPathFile = actualFile.toPath();
            Path newPathFile = new File(newPathLink, actualFile.getName()).toPath();

            try {
                // Mover el archivo a la nueva carpeta
                Files.move(oldPathFile, newPathFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo " + actualFile.getName() + " movido exitosamente");
            } catch (IOException e) {
                System.out.println("No se pudo mover el archivo " + actualFile.getName());
            }
        }

        // DespuÃ©s de mover los archivos a la nueva ruta (carpeta), borramos los archivos de la antigua
        for (File actualFile : files) {
            actualFile.delete();
        }

        createPathFather();

    }


    //MANIPULACION DE DATOS DE LA CLASE SECURITY
    public CircularLinkedList getSecurityCircularLinkedList() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "securityData.txt";
        CircularLinkedList circularLinkedList = new CircularLinkedList();
        ArrayList<Security> arrayList = new ArrayList<>();
        //String que contendra el formatoJSON
        String output = "";
        file = new File(pathJSON);
        if (file.exists()) {

            //archivo existe
            System.out.println("Archivo existe");
            try {
                //leemos los datos del archivo JSON indicado
                output = new String(Files.readAllBytes(Paths.get(pathJSON)));

                if (output.equals("") || output == null) {
                    //si no hay nada en el jsonFile, lista vacia, para comenzar a llenar manualmente
                    return circularLinkedList;

                } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                    ObjectMapper om = new ObjectMapper();
                    //deserealizamos
                    arrayList = om.readValue(
                            output, new TypeReference<ArrayList<Security>>() {
                            });

                    for (Security list : arrayList) {
                        circularLinkedList.add(list);
                    }
                    return circularLinkedList;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            file = new File(pathJSON);
            file.createNewFile();
            return circularLinkedList;
        }

    }

    public void saveSecurityCircularLinkedList(CircularLinkedList circularLinkedList) throws ListException, IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "securityData.txt";
        ArrayList<Security> arrayList = new ArrayList<>();

        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!circularLinkedList.isEmpty()) {

            int count = 1;
            int size = circularLinkedList.size();

            while (count <= size) {
                //guardamos el valor del nodo
                arrayList.add((Security) circularLinkedList.getNode(count).data);
                count++;
            }
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //MANTENIMIENTO DE CUSTOMER
    public SinglyLinkedList getCustomerInSinglyLinkedList() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "customerData.txt";
        ArrayList<Customer> arrayList = new ArrayList<>();
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        //String que contendra el formatoJSON
        String output = "";
        file = new File(pathJSON);
        if (file.exists()) {
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return singlyLinkedList;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Customer>>() {
                        });

                for (Customer list : arrayList) {
                    singlyLinkedList.add(list);
                }
                return singlyLinkedList;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return singlyLinkedList;
        }
    }

    public void saveCustomerSinglyLinkedList(SinglyLinkedList singlyLinkedList) throws ListException, IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "customerData.txt";
        ArrayList<Customer> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!singlyLinkedList.isEmpty()) {
            int count = 1;
            int size = singlyLinkedList.size();

            while (count <= size) {
                //guardamos el valor del nodo
                arrayList.add((Customer) singlyLinkedList.getNode(count).data);
                count++;
            }

            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //CONTROL DE COSTOS (manejar productos con TDA Header LinkedQueue)
    public HeaderLinkedQueue getProductHeaderLinkedQueue() throws IOException, QueueException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        HeaderLinkedQueue headerLinkedQueue = new HeaderLinkedQueue();
        //String que contendra el formatoJSON
        file = new File(pathJSON);

        if (file.exists()) {
            String output = "";
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return headerLinkedQueue;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Product>>() {
                        });

                for (Product list : arrayList) {
                    headerLinkedQueue.enQueue(list);
                }
                return headerLinkedQueue;
            }

        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return headerLinkedQueue;
        }
    }

    public void saveProductHeaderLinkedQueue(HeaderLinkedQueue headerLinkedQueue) throws ListException, QueueException, IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();

        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
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
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //MANTENIMIENTO DE PRODUCTOS
    public AVL getProductAVL() throws IOException, QueueException {
        String pathJSON = ABOSLUTE_PATH + File.separator +  "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if (file.exists()) {

            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return avl;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Product>>() {
                        });

                for (Product list : arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        } else {
            System.out.println("El productData.txt no existe, se crea la carpeta");
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }



    public void saveProductAVL(AVL avl) throws ListException, QueueException, IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator +  "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!avl.isEmpty()) {
            inOrderProductoFirst(avl, arrayList);
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Metodo auxiliar para ayudar a recorrer los arboles

    public void inOrderProductoFirst(AVL avl, ArrayList<Product> arrayList) {
        inOrderProductSecond(avl.root, arrayList);
    }

    public void inOrderProductSecond(BTreeNode node, ArrayList<Product> arrayList) {

        if (node != null) {
            inOrderProductSecond(node.left, arrayList);
            arrayList.add((Product) node.data);
            inOrderProductSecond(node.right, arrayList);
        }
    }


    //MANTENIMIENTO DE PROVEEDORES
    public AVL getSupplierAVL() throws IOException, QueueException {
        String pathJSON = ABOSLUTE_PATH + File.separator +"supplierData.txt";
        ;
        ArrayList<Supplier> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);
        if (file.exists()) {

            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return avl;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Supplier>>() {
                        });

                for (Supplier list : arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }

    public void saveSupplierAVL(AVL avl) throws ListException, QueueException, IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator +"supplierData.txt";
        ArrayList<Supplier> arrayList = new ArrayList<>();

        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!avl.isEmpty()) {

            inOrderSupplierFirst(avl, arrayList);
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void inOrderSupplierFirst(AVL avl, ArrayList<Supplier> arrayList) {
        inOrderSupplierSecond(avl.root, arrayList);
    }

    public void inOrderSupplierSecond(BTreeNode node, ArrayList<Supplier> arrayList) {

        if (node != null) {
            inOrderSupplierSecond(node.left, arrayList);
            arrayList.add((Supplier) node.data);
            inOrderSupplierSecond(node.right, arrayList);
        }
    }


    //Manejo de OrderDetail (AVL)

    public AVL getOrderDetailAVL() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator +"orderDetailData.txt";
        ;
        ArrayList<OrderDetail> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if (file.exists()) {
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return avl;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<OrderDetail>>() {
                        });

                for (OrderDetail list : arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveOrderDetailAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = ABOSLUTE_PATH + File.separator +"orderDetailData.txt";
        ArrayList<OrderDetail> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        //Se vacia la AVL con el metodo get()
        for (int i = 0; i < avl.size(); i++) {
            arrayList.add((OrderDetail) avl.get(i));
        }
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //MANEJO DE ORDER (AVL)

    public AVL getOrderAVL() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator +"orderData.txt";
        ;
        ArrayList<Order> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if (file.exists()) {
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return avl;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Order>>() {
                        });

                for (Order list : arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveOrderAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = ABOSLUTE_PATH + File.separator + "orderData.txt";
        ArrayList<Order> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        //Se vacia la AVL con el metodo get()
        for (int i = 0; i < avl.size(); i++) {
            arrayList.add((Order) avl.get(i));
        }
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    //Manejo de clase Sale (AVL)

    public AVL getSaleAVL() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "saleData.txt";
        ;
        ArrayList<Sale> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if (file.exists()) {
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return avl;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Sale>>() {
                        });

                for (Sale list : arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveSaleAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = ABOSLUTE_PATH + File.separator + "saleData.txt";
        ArrayList<Sale> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!avl.isEmpty()) {
            //Se vacia la AVL con el metodo get()
            for (int i = 0; i < avl.size(); i++) {
                arrayList.add((Sale) avl.get(i));
            }
            //Les especificaciomos a este constructor JSON que debe guardar toda instancia
            //tipo fecha/hora con el formato indicado en la clase Sale
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //Manejo de clase SaleDetail (AVL)

    public AVL getSaleDetailAVL() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "saleDetailData.txt";
        ;
        ArrayList<SaleDetail> arrayList = new ArrayList<>();
        AVL avl = new AVL();
        file = new File(pathJSON);

        if (file.exists()) {
            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return avl;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<SaleDetail>>() {
                        });

                for (SaleDetail list : arrayList) {
                    avl.add(list);
                }
                return avl;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return avl;
        }
    }


    public void saveSaleDetailAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = ABOSLUTE_PATH + File.separator + "saleDetailData.txt";
        ArrayList<SaleDetail> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }
        if (!avl.isEmpty()) {
            //Se vacia la AVL con el metodo get()
            for (int i = 0; i < avl.size(); i++) {
                arrayList.add((SaleDetail) avl.get(i));
            }
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //CONTROL DE INVENTARIO (manejo de productos con Btree en vez de AVL)
    public BTree getInventoryBtree() throws IOException, QueueException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "productData.txt";
        ;
        ArrayList<Product> arrayList = new ArrayList<>();
        BTree bTree = new BTree();
        file = new File(pathJSON);

        if (file.exists()) {

            String output = "";
            //leemos los datos del archivo JSON indicado
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));

            if (output.equals("") || output == null) {
                //arrayList = new ArrayList<Customer>();
                return bTree;

            } else {//si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
                ObjectMapper om = new ObjectMapper();
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Product>>() {
                        });

                for (Product list : arrayList) {
                    bTree.add(list);
                }
                return bTree;
            }
        } else {
            file = new File(pathJSON);
            file.createNewFile();
            return bTree;
        }
    }

    public void saveInventoryBtree(BTree bTree) throws ListException, QueueException, IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "productData.txt";
        ArrayList<Product> arrayList = new ArrayList<>();
        File file = new File(pathJSON);

        if (file.exists()) {//se eliminar el archivo json
            file.delete();
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        } else {
            System.out.println("El archivo " + pathJSON + "no existe");
            file.createNewFile();//se crear de nuevo el archivo JSON para la nueva info
        }

        if (!bTree.isEmpty()) {
            inOrderinventoryFirst(bTree, arrayList);
            ObjectMapper om = new ObjectMapper();
            try {
                om.writeValue(Paths.get(pathJSON).toFile(), arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AVL getBitacora() throws IOException {
        String pathJSON = ABOSLUTE_PATH + File.separator + "bitacoraGeneral.txt";;
        ArrayList<Binnacle> arrayList = new ArrayList<>();
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
                om.registerModule(new JavaTimeModule());
                om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
                //llenamos el ArrayList con la info del JSON file
                //(se deserializa)
                arrayList = om.readValue(
                        output, new TypeReference<ArrayList<Binnacle>>(){});

                for (Binnacle list: arrayList) {
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


    public void saveBitacotaAVL(AVL avl) throws IOException, TreeException {

        String pathJSON = ABOSLUTE_PATH + File.separator + "bitacoraGeneral.txt";
        ArrayList<Binnacle> arrayList = new ArrayList<>();
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
            arrayList.add( (Binnacle) avl.get(i));
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



    //Metodo auxiliar para ayudar a recorrer los arboles

    public void inOrderinventoryFirst(BTree bTree, ArrayList<Product> arrayList) {
        inOrderinventorySecond(bTree.root, arrayList);
    }

    public void inOrderinventorySecond(BTreeNode node, ArrayList<Product> arrayList) {

        if (node != null) {
            inOrderinventorySecond(node.left, arrayList);
            arrayList.add((Product) node.data);
            inOrderinventorySecond(node.right, arrayList);
        }
    }


}
