package ucr.proyecto.proyectogrupo1.util;

import ucr.proyecto.proyectogrupo1.TDA.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public JSON_Utility(String fileNameAndExtension) {
        //Obtener el nombre del archivo
        itExistFile = new File(fileNameAndExtension);
        //Obtener la ruta absoluta
        pathJSON = itExistFile.getAbsolutePath();
        //verificamos que exista el archivo
        file = new File(pathJSON);

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error, no se ha podido crear el archivo. Por favor intentelo mas tarde");
                throw new RuntimeException(e);
            }
        }
        pathJSON = file.getAbsolutePath();
    }

    //Se lee el JSON.file y se regresa como un String su informacion
    public String readObjectsJSON(){
        String output = "";

        try {
            //lee todos los bytes presentes en el archivos JSON y los convierte a
            //String
            output = new String(Files.readAllBytes(Paths.get(pathJSON)));
            System.out.println(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    //Transformar los Objetos en formato JSON a elementos de un ArrayList
    public ArrayList<Object> listOfObjects() throws JsonProcessingException {

        ArrayList<Object> inspectors = null;
        //si no hay nada en el JSON.file se envia vacia la lista para comenzar a trabajar
        String jsonFile = readObjectsJSON();
        if (jsonFile.equals("") || jsonFile == null){
            inspectors = new ArrayList<Object>();
            return inspectors;
            //si hay al menos un objeto en el JSON, entonces lo pasamos al ArrayList
        }else{
            ObjectMapper om = new ObjectMapper();
            //llenamos el ArrayList con la info del JSON file
            //(se deserializa)
            inspectors = om.readValue(
                    jsonFile, new TypeReference<ArrayList<Object>>(){});
            return inspectors;
        }
    }

    //Obtener el dato del JSON como cualquier TDA
    public SinglyLinkedList getDataInSinglyLinkedList() throws JsonProcessingException {
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        for (Object list: listOfObjects()) {
            singlyLinkedList.add(list);
        }
        return singlyLinkedList;
    }

    public CircularLinkedList getDataInCircularLinkedList() throws JsonProcessingException {
        CircularLinkedList circularLinkedList = new CircularLinkedList();
        for (Object list: listOfObjects()) {
            circularLinkedList.add(list);
        }
        return circularLinkedList;
    }

    public BTree getDataInBTree() throws JsonProcessingException {
        BTree bTree = new BTree();
        for (Object list: listOfObjects()) {
            bTree.add(list);
        }
        return bTree;
    }

    public AVL getDataInBTreeAVL() throws JsonProcessingException {
        AVL bTreeAVL = new AVL();
        for (Object list: listOfObjects()) {
            bTreeAVL.add(list);
        }
        return bTreeAVL;
    }

    public BST getDataInBTreeBST() throws JsonProcessingException {
        BST bTreeBST = new BST();
        for (Object list: listOfObjects()) {
            bTreeBST.add(list);
        }
        return bTreeBST;
    }

    //ORIGINAL
//    public ArrayList<Object> getAllObjectsJSON() throws JsonProcessingException {
//
//        return listOfObjects();
//    }

    //GUARDAR LOS DATOS DE NUEVO EN LOS ARCHIVOS


    //Reemplazar los datos actuales del archivo, por los actualizados provenientes de las TDA
    public boolean replaceAndUpdate(Object tda) throws ListException, TreeException {

        //Dependiendo del tipo de tda que sea, guardamos los nuevos datos manejados desde las listas
        //globales, en el archivo JSON
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (tda instanceof SinglyLinkedList){
            return updateDataAsSinglyLinkedList((SinglyLinkedList) tda,arrayList);
        } else if (tda instanceof CircularLinkedList) {
            return updateDataAsCircularLinkedList((CircularLinkedList) tda,arrayList);
        } else if (tda instanceof AVL) {
            return updateDataAsAVL((AVL) tda,arrayList);
        } else if (tda instanceof BST) {
            return updateDataAsBST((BST) tda,arrayList);
        } else if (tda instanceof BTree) {
            return updateDataAsBTree((BTree) tda,arrayList);}


        ObjectMapper objectMapper = new ObjectMapper();


        try {
            objectMapper.writeValue(Paths.get(pathJSON).toFile(),objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean updateDataAsCircularLinkedList(CircularLinkedList list,ArrayList<Object> arrayList) throws ListException {

        boolean upadted = false;
        if (list.isEmpty()) {
            upadted = true;
        } else {
            int count = 0;
            int size = list.size();
            while (count <= size) {
                //guardamos el valor del nodo
                arrayList.add(list.getNode(count));
                count++;
            }
            upadted = true;
//            Node aux = list.getFirst();
//            while (aux.next != null) {
//                //guardamos el valor del nodo
//                arrayList.add(aux.data);
//                //pasamos al siguiente nodo
//                aux = aux.next;
//            }
        }
        return upadted;
    }

    public boolean updateDataAsSinglyLinkedList(SinglyLinkedList list,ArrayList<Object> arrayList) throws ListException {

        boolean upadted = false;
        if (list.isEmpty()) {
            upadted = true;
        } else {
            int count = 0;
            int size = list.size();
            while (count <= size) {
                //guardamos el valor del nodo
                arrayList.add(list.getNode(count));
                count++;
            }
            upadted = true;
        }
        return upadted;
    }

    public boolean updateDataAsBTree(BTree tree,ArrayList<Object> arrayList) throws TreeException {

        boolean updated = false;
        if (tree.isEmpty()) {
            updated = true;
        }else {
            arrayList = InOrder(tree,arrayList);
        }
    }

    public boolean updateDataAsAVL(AVL tree,ArrayList<Object> arrayList){
        return;
    }

    public boolean updateDataAsBST(BST tree,ArrayList<Object> arrayList){
        return;
    }

    public void InOrder(BTree tree,ArrayList<Object> arrayList) throws TreeException {
        inOrder(tree.getRoot(),arrayList);
    }

    //metodo interno
    //preOrder: left-node-right
    private ArrayList<Object> inOrder(BTreeNode node,ArrayList<Object> arrayList){
        if(node!=null){
            inOrder(node.left,arrayList);
            arrayList.add(node.data);
            inOrder(node.right,arrayList);
        }
        return arrayList;
    }
}
