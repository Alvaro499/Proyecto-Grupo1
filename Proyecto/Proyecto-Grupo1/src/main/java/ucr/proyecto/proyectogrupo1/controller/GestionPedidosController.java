package ucr.proyecto.proyectogrupo1.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import ucr.proyecto.proyectogrupo1.PDF.PDF;
import ucr.proyecto.proyectogrupo1.TDA.*;
import ucr.proyecto.proyectogrupo1.domain.Order;
import ucr.proyecto.proyectogrupo1.domain.OrderDetail;
import ucr.proyecto.proyectogrupo1.domain.Product;
import ucr.proyecto.proyectogrupo1.domain.Supplier;
import ucr.proyecto.proyectogrupo1.util.FXUtility;
import ucr.proyecto.proyectogrupo1.util.Utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestionPedidosController {

    @FXML
    private ComboBox<Product> choiceBoxProduct;
    @FXML
    private TextField productQuantity;

    @FXML
    private TableColumn<List<String>, String> orderColumn;

    @FXML
    private TableColumn<List<String>, String> orderDateColumn;

    @FXML
    private TableColumn<List<String>, String> orderDetailProduct;

    @FXML
    private TableColumn<List<String>, String> orderDetailQuantityColumn;

    @FXML
    private TableColumn<List<String>, String> orderDetailUnitPrice;

    @FXML
    private TableColumn<List<String>, String> orderStatusColumn;

    @FXML
    private TableColumn<List<String>, String> orderSupplierColumn;

    @FXML
    private TableColumn<List<String>, String> orderTotalCost;
    @FXML
    private TableView<List<String>> tableView;

    //Llenar Tablas
    private ObservableList<Order> getOrderInfo; //ObservableList Local
    private ObservableList<OrderDetail> getOrderDetailInfo; //ObservableList Local
    private Alert alert;
    private AVL PRODUCT_AVL;
    private AVL SUPPLIER_AVL;
    private AVL ORDER_AVL;
    private AVL ORDER_DETAIL_AVL;
    private ArrayList<String> historicReport;
    private ArrayList<String> todayReport;
    private boolean isAutomaticStock = false;

    @FXML
    public void initialize() throws TreeException, QueueException, IOException {

        this.alert = FXUtility.alert("", "");

        //Llamamos la informacion de los archivos desde Utility
        PRODUCT_AVL = Utility.getProductAVL();
        SUPPLIER_AVL = Utility.getSupplierAVL();
        ORDER_AVL = Utility.getOrder();
        ORDER_DETAIL_AVL = Utility.getOrderDetail();

        //Creamos la listas para las tablas, que son llenadas con las TDA
        getOrderInfo = FXCollections.observableArrayList();
        getOrderDetailInfo = FXCollections.observableArrayList();

        //Carga todo los reportes desde el AVL Order
        historicReport = new ArrayList<String>();
        todayReport = new ArrayList<String>();

        chargeProduct();//cargamos el choiceBox con los productos

        //No llenamos la ObservableList, si no que se creara cuando el admin
        //solicite al menos un producto. Cuando se confirma el pedido, se agrega a la
        //TDA con todas las demas ordenes
    }
    public void setColumns() throws TreeException {

        orderColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(0)));

        orderDateColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(1)));

        orderStatusColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(2)));

        orderSupplierColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(3)));

        orderDetailProduct.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(4)));

        orderDetailQuantityColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(5)));

        orderDetailUnitPrice.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(6)));
        orderTotalCost.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().get(7)));

        //if (!getOrderInfo.isEmpty()) {
        tableView.setItems(getData());
        //}
        tableView.refresh();
    }

    private ObservableList<List<String>> getData() throws TreeException {
        //Cargamos el ObservableList para la tabla con los pedidos que se realizan actualmente, no
        //con los que se hayam realizado, esos se muestran en el reporte de PDF

        ObservableList<List<String>> data = FXCollections.observableArrayList();
        for (int i = 0; i < getOrderInfo.size(); i++) {
            List<String> arrayList = new ArrayList<>();
            Order o = (Order) getOrderInfo.get(i);

            arrayList.add(String.valueOf(o.getId()));//ID de la orden
            arrayList.add(String.valueOf(o.getOrderDate()));//fecha de la orden
            arrayList.add(o.getOrderStatus());//estatus de la orden
            arrayList.add(o.getSupplierName());//nombre del proveedor

            //Sacamos la info del orderDetail
            OrderDetail actualDetail = null;
            for (int j = 0; j < getOrderDetailInfo.size() ; j++) {
                actualDetail = (OrderDetail) getOrderDetailInfo.get(j);
                //Es el codigo del detalle de orden el mismo que el de la
                //orden actual? Entonces encontramos la orden y detalle equivalente
                if (actualDetail.getOrderID().equals(o.getId())){
                    break;
                }
            }

            String productNamefromAVL = "";
            String unitPriceAVL = "";
            //Producto (lo contiene orderDetail o se extrae desde AVL Product)
            for (int j = 0; j < PRODUCT_AVL.size(); j++) {//Agarra ID de la tabla supplier y lo compara con IDsupplier de la tabla Product, para saber el nombre del proveedor del libro
                Product product = (Product) PRODUCT_AVL.get(j);
                if (actualDetail.getProductID().equals(product.getID())){
                    //Agregamos el nombre del producto
                    productNamefromAVL = product.getName();
                    unitPriceAVL = String.valueOf(product.getPrice());;
                    break;
                }
            }
            arrayList.add(productNamefromAVL);//nombre del producto
            arrayList.add(actualDetail.getQuantity());//cantidad de producto
            arrayList.add("₡" + unitPriceAVL);//precio del producto
            arrayList.add("₡" + String.valueOf(o.getTotalCost()));//total del pedido

            //Guardamos los datos de las ordenes para agregarlas en el TableBiew
            data.add(arrayList);

            //Guardamos los datos para el reporte actual:
            todayReport.add(String.valueOf(o.getId()));//id de la orden
            todayReport.add(o.getSupplierName());//proveedor del producto
            todayReport.add("Confirmada");//estatus de la orden
            todayReport.add(o.getRemarks());//resumen de las caractersitcas
        }
        return data;
    }

    @FXML
    void btnAddAutomatic(ActionEvent event) throws TreeException {

        getOrderInfo.clear();
        getOrderDetailInfo.clear();
        AVL auxAVL = new AVL();
        //Seleccionamos una cantidad de productos aleatoriamente
        int numProduct = Utility.random(0,PRODUCT_AVL.size()+1);
        //numero que ira iterando y buscando un producto aleatoriamente
        int randomProduct = 0;
        //contador para wl while
        int i = 0;
        //producto aleatorio
        Product actualProduct = null;

        //mientras el contador sea menor a la cantidad de productos a solicitar
        while(i < numProduct){
            //se toma un producto aleatorio
            randomProduct = Utility.random(1,PRODUCT_AVL.size()-1);
            actualProduct = (Product) PRODUCT_AVL.get(randomProduct);

            //si el producto ya fue tomado aleatoriamente, entonces repetimos
            if (auxAVL.isEmpty() ){
                //el producto no ha sido seleccionado previamente, entonces creamos el pedido
                //y su detalle, por ultimo se agrega a las listas Observable para mostrarlo en columnas
                //y al confirmar los pedidos, se guardan las ordenes en los AVL
                auxAVL.add(actualProduct);
                addRandomProduct(actualProduct);
                i++;
            }else if ( !auxAVL.contains(actualProduct) ){
                auxAVL.add(actualProduct);
                addRandomProduct(actualProduct);
                i++;
            }
            //si ya fue seleccionado, seguimos buscando otro producto aleatorio diferente al de los demas
        }
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Pedidos automaticos generados");
        alert.showAndWait();
        setColumns();
        //System.out.println("Pedidos automaticos generados");
    }

    private void addRandomProduct(Product actualProduct) throws TreeException {

        //Obtengo el producto con su informacion

        //Generamos el numero de Orden
        int idOrder = takeID();

        //Generamos la hora del pedido
        //AAAA-MM-DDTHH:mm:ss
        LocalDateTime localDateTime = LocalDateTime.now();

        //Obtenemos su proveedor segun su ID
        Supplier supplierOrder = takeSupplier(actualProduct.getSupplierID());

        //Construyo la orden
        //El costo total se mantiene en cero por mientras
        //Los detalles tambien
        Order order = new Order(idOrder, localDateTime.withNano(0)+"","En proceso",supplierOrder.getName(), (double) 0,"");

        //Construyo el detalle de la orden (Order Detail)
        //Nota: como el producto y el pedido es automatico, entonces se solicita un 25% del stock actual
        int randomStock = 25 * actualProduct.getCurrentStock() / 100;
        OrderDetail orderDetail = new OrderDetail(order.getId(),actualProduct.getID(),String.valueOf(randomStock) ,actualProduct.getPrice());

        //Agregamos el costo total de este producto
        order.setTotalCost(orderDetail.getUniPrice() *  Integer.valueOf(randomStock));

        //Agregamos las anotaciones del pedido que son las caracteristicas del mismo (producto, cantidad, costo total, fecha)
        String remarks = "\nAnotaciones de pedido:\n" +
                "Producto: " + actualProduct.getName() + " \n" +
                "\nFecha de pedido: " + localDateTime.toString() + "\n" +
                "\nCantidad: " + randomStock + "\n" +
                "\nCosto total: ₡" + order.getTotalCost();

        order.setRemarks(remarks);

        //Guardamos el pedido y su informacion en las listas Observables
        getOrderInfo.add(order);
        getOrderDetailInfo.add(orderDetail);
    }

    @FXML
    void btnAddProduct(ActionEvent event) throws TreeException {

        //Si todos los campos estan llenos y el de cantidad no contiene texto
        if (isValid()){
            //Obtengo el producto con su informacion
            Product product = (Product) choiceBoxProduct.getValue();

            //Generamos el numero de Orden
            int idOrder = takeID();

            //Generamos la hora del pedido
            //AAAA-MM-DDTHH:mm:ss
            LocalDateTime localDateTime = LocalDateTime.now();

            //Obtenemos su proveedor segun su ID
            Supplier supplierOrder = takeSupplier(product.getSupplierID());

            //Construyo la orden
            //El costo total se mantiene en cero por mientras
            //Los detalles tambien
            Order order = new Order(idOrder, localDateTime.withNano(0)+"","En proceso",supplierOrder.getName(), (double) 0,"");

            //Construyo el detalle de la orden (Order Detail)
            OrderDetail orderDetail = new OrderDetail(order.getId(),product.getID(),productQuantity.getText(),product.getPrice());

            //Agregamos el costo total de este producto
            order.setTotalCost(orderDetail.getUniPrice() * Integer.valueOf(productQuantity.getText()) );

            //Agregamos las anotaciones del pedido que son las caracteristicas del mismo (producto, cantidad, costo total, fecha)
            String remarks = "\nAnotaciones de pedido:\n" +
                    "Producto: " + product.getName() + " \n" +
                    "\nFecha de pedido: " + localDateTime.withNano(0) + "\n" +
                    "\nCantidad: " + productQuantity.getText() + "\n" +
                    "\nCosto total: ₡" + order.getTotalCost();

            order.setRemarks(remarks);

            //Guardamos el pedido y su informacion en las listas Observables
            getOrderInfo.add(order);
            getOrderDetailInfo.add(orderDetail);

            for (OrderDetail or: getOrderDetailInfo) {
                System.out.println(or.toString());
            }
            setColumns();

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Pedido generado");
            alert.showAndWait();
            //System.out.println("Pedido generado");
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("No se pudo generar el pedido, complete la información");
            alert.showAndWait();
            //System.out.println("No se pudo generar el pedido, complete la informacoin");
        }
        choiceBoxProduct.setValue(null);
        productQuantity.setText("");
    }

    @FXML
    void btnConfirmOrders(ActionEvent event) throws TreeException, QueueException, IOException, ListException {

        //Verificamos que haya al menos un pedido por parte del administrador
        if (!getOrderInfo.isEmpty() && !getOrderDetailInfo.isEmpty()){

            //Guardamos las nuevas ordenes y sus detalles en las TDA de cada una
            for (Order newOrder: getOrderInfo) {
                ORDER_AVL.add(newOrder);
            }
            for (OrderDetail newOrderDetail: getOrderDetailInfo) {
                ORDER_DETAIL_AVL.add(newOrderDetail);
            }

            //Modificamos el stock de los productos en la TDA de productos
            for (int i = 0; i < getOrderDetailInfo.size() ; i++) {

                //Comparamos el id del producto del detalle de la orden para encontrar al Producto con el mismo id
                OrderDetail actualOrderDetail = getOrderDetailInfo.get(i);
                for (int j = 0; j < PRODUCT_AVL.size() ; j++) {
                    //Si el id producto del detalle de la orden coincide con alguno de los productos de la AVL
                    //entonces le restamos la cantidad comprada indicada en el detalle de la oden a su stock actual
                    if (actualOrderDetail.getProductID().equals( ((Product) PRODUCT_AVL.get(j)).getID() )){
                        ((Product) PRODUCT_AVL.get(j)).setCurrentStock( ((Product) PRODUCT_AVL.get(j)).getCurrentStock() + Integer.valueOf(actualOrderDetail.getQuantity()) );
                    }
                }
            }
            //Guardamos los Orders y OrdersDetail en la TDA Global de Utility que invoca a su vez a JSON_Utility
            Utility.setOrder(ORDER_AVL);
            Utility.setOrderDetail(ORDER_DETAIL_AVL);


            //Creamos un PDF con los pedidos que se acaban de hacer
            PDF.crearPDF("Últimos pedidos realizados","Reporte Actual",4,todayReport);
            //Setteamos los campos
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Se han confirmado todos los pedidos actuales");
            alert.showAndWait();
            btnDeleteAll(new ActionEvent());
            //System.out.println("Se han realizado los pedidos");
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("No hay pedidos por confirmar");
            alert.showAndWait();
            //System.out.println("No se han generados pedidos");
        }
    }

    @FXML
    void btnDeleteAll(ActionEvent event) throws TreeException, QueueException, IOException {
        choiceBoxProduct.setValue(null);
        productQuantity.setText("");
        getOrderDetailInfo.clear();
        getOrderInfo.clear();
        setColumns();
        tableView.refresh();
        initialize();
    }



    @FXML
    void btnHistoricReport(ActionEvent event) throws TreeException {
        //Muestra todos los pedidos que sea han realizado hasta la fecha,
        if (!ORDER_AVL.isEmpty()){

            for (int i = 0; i < ORDER_AVL.size() ; i++) {
                //Guardamos los datos para el reporte actual:
                historicReport.add(String.valueOf(((Order) ORDER_AVL.get(i)).getId()));//id de la orden
                historicReport.add(((Order) ORDER_AVL.get(i)).getSupplierName());//proveedor del producto
                //o.setStatus("Confirmada");
                historicReport.add("Confirmado");//estatus de la orden
                historicReport.add(((Order) ORDER_AVL.get(i)).getRemarks());//resumen de las caractersitcas
            }
            //Crea un pdf con todos los pedidos que se han hecho
            PDF.crearPDF("Pedidos de los administradores","Reporte Histórico",4,historicReport);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Reporte histórico generado");
            alert.showAndWait();
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("No se han realizado pedidos hasta la actualidad");
            alert.showAndWait();
            //System.out.println("No se han realizado pedidos por el momento");
        }
    }





    public void chargeProduct() {
        //Como los choiceBox solo pueden ser cargados manualmente dato por dato, o por
        //ArrayList, llenamos uno de estos ultimos usando un recorrid de AVL para extraer sus datos

        //se crea la lista para extraer
        ArrayList<Product> productsFromAvl = new ArrayList<>();
        //se recorre el AVL y capturamos sus datos con el ArrayList
        inorderChoiceBox(PRODUCT_AVL, productsFromAvl);
        //Llenamos el Observable indispensable para llenar el choiceBox
        ObservableList<Product> productsAVL = FXCollections.observableArrayList(productsFromAvl);
        //choiceBoxProduct.setItems(productsAVL);


        https://es.stackoverflow.com/questions/111168/como-mostrar-un-valor-de-un-combobox-en-javafx-pasando-un-valor
        choiceBoxProduct.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);

                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(product.toStringChoiceBox());
                }
            }
        });

        choiceBoxProduct.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                if (product == null) {
                    return null;
                } else {
                    return product.toStringChoiceBox();
                }
            }

            @Override
            public Product fromString(String s) {
                return null;
            }
        });
        choiceBoxProduct.getItems().addAll(productsAVL);
    }


    private void inorderChoiceBox(AVL avl, ArrayList<Product> arrayList){
        inOrderChoiceBoxAux(avl.root,arrayList);
    }

    private void inOrderChoiceBoxAux(BTreeNode node, ArrayList<Product> arrayList){
        if (node != null){
            inOrderChoiceBoxAux(node.left,arrayList);
            arrayList.add((Product) node.data);
            inOrderChoiceBoxAux(node.right,arrayList);
        }
    }

    public boolean isValid() {
        if (choiceBoxProduct.getValue() == null || !containsText(productQuantity.getText()) || productQuantity.getText().isEmpty() || Integer.valueOf(productQuantity.getText()) == 0){
            return false;
        }
        return true;
    }

    public boolean containsText(String textFieldContent){
        //return textFieldContent.matches("\\d+");
        return textFieldContent.matches("\\d*");
        //return textFieldContent.matches("[1-9]+");
    }

    public Supplier takeSupplier(int idSupplier) throws TreeException {

        //Recorremos el arbol en busqueda de algun ID parecido al  que se obtubo
        //del choiceBox
        for (int j = 0; j < SUPPLIER_AVL.size(); j++) {//Agarra ID de la tabla supplier y lo compara con IDsupplier de la tabla Product, para saber el nombre del proveedor del libro
            Supplier s = (Supplier) SUPPLIER_AVL.get(j);
            //Si el ID pasado es igual a uno de los de la TDA, lo devolvemos
            if (s.getID() == idSupplier){
                return s;
            }
        }
        return null;
    }

    private int takeID() throws TreeException {
        //Recorremos todo el arbol buscando la orden con el numero mayor y al final le sumamos
        //1 para la nueva orden

        //Si la lista actual de pedidos esta vacia, entonces generamos el id a partir del ID mas grande de la AVL
        //si la lista no esta vacia, generamos el nuevo ID del pedido a partir del ID mas alto de la lista
        if (!getOrderDetailInfo.isEmpty()){

            int numOrder = 1;
            Order actualOrder = null;
            for (int i = 0; i < getOrderInfo.size() ; i++) {
                actualOrder = getOrderInfo.get(i);

                if (numOrder <= actualOrder.getId() ){
                    numOrder = actualOrder.getId();
                }
            }
            //Aumentamos en 1 la orden con mayor numero
            return ++numOrder;

        }else if(!ORDER_AVL.isEmpty()) {

            int numOrder = 1;
            Order actualOrder = null;
            for (int i = 0; i < ORDER_AVL.size() ; i++) {
                actualOrder = (Order) ORDER_AVL.get(i);

                if (numOrder <= actualOrder.getId() ){
                    numOrder = actualOrder.getId();
                }
            }
            //Aumentamos en 1 la orden con mayor numero
            return ++numOrder;

        }return 1;

    }
}
