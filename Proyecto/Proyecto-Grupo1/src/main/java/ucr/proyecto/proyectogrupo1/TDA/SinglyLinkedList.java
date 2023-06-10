package ucr.proyecto.proyectogrupo1.TDA;

import ucr.proyecto.proyectogrupo1.util.Utility;

public class SinglyLinkedList implements List {
    private Node first; //apunta al inicio de la lista

    //Los nodos por defecto traen al prev y next como nulos. El nulo que se asigna
    //es para el data, para indicar que no tiene valor por el momento
    public SinglyLinkedList() {
        this.first = null;
    }

    @Override
    public int size() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        //necesitamos guardar una referencia del actual nodo (en este caso del primero)
        //para ir 1 por 1 en los nodos. O sino tendriamos que revisar cada uno de la forma:
        //first.next.next.next.next... y es imposible
        Node aux = first;
        int count = 0;
        while (aux != null) {
            count++;
            aux = aux.next; //muevo aux al sgte nodo
        }
        //en caso de que aux sea nulo a a la primera significa que first aun no tiene asignado un
        //nodo, por lo que no hay datos, size = 0
        return count;
    }

    //Hacemos que el nodo principal se desconecte de todos los demas
    @Override
    public void clear() {
        first = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        while (aux != null) {
            if (Utility.compare(aux.data, element) == 0) return true;
            //if (util.Utility.compare(element, aux.data) == 0) return true;
            aux = aux.next; //muevo aux al sgte nodo
        }
        return false; //si llego aqui, el elemento no existe
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode;
        } else {
            Node aux = first; //apunta al primer nodo de la lista
            //necesito moverme por la lista hasta el final, es decir encontrar al ultimo nodo de la lista
            //y para saber si estoy en el ultimo nodo, debo verificar su next y ver si es nulo, es decir si no
            //conecta con nadie mas
            while (aux.next != null) {
                aux = aux.next;
            }
            //se sale del while cuando aux.next == null
            aux.next = newNode;
        }

    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode;
        } else {
            //recordar que los valores de los nodos son referencias
                newNode.next = first;
                first = newNode;
        }
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode;
        }else {

            //Cuando first.data es mayor que element
            if (Utility.compare(first.data, element) > 0) {
                newNode.next = first;
                first = newNode;
            } else {
                Node aux = first;
                //Comprobar si el siguiente del nodo actual (aux) no es nulo y es menor que el nuevo,
                //si es asi, los intercambiamos. Es como decir, no es nulo o sea hay un datos, ahora verficr si
                //ese dato es menor o mayor al actual, si es mayor reemplazamos
                while (aux.next != null && (Utility.compare(aux.next.data, element) == -1)) {
                    aux = aux.next;
                }

                //Se intercambian los nodos, pero tambien sus next
                newNode.next = aux.next;
                aux.next = newNode;
            }
        }
    }

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        //Caso 1. El elemento a suprimir es el primero de la lista
        if (Utility.compare(first.data, element) == 0) {
            first = first.next;
        } else {
            //Caso 2. El elemento puede estar en cualquier parte
            Node prev = first;
            Node aux = first.next;
            //Aqui el objetivo es ver si el elemento actual es el que queremos borrar, si es el, entonces
            //devemos verificar tambien que haya algo en su next
            while (aux != null && !(Utility.compare(aux.data, element) == 0)) {
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o cuando encuentra el elemento
            if (aux != null && Utility.compare(aux.data, element) == 0) {
                //desenlaza el nodo con el elemento a eliminar
                prev.next = aux.next;
            }

            //Si entra a los dos nulos es porque no encontro el elemento a borrar
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Object aux = first.data;
        first = first.next;
        return aux;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        //Caso 1 la lista tiene solo un elemento
        if(aux.next==null) aux=first.next;

        //Original
//        while (aux.next.next!=null){
//            aux=aux.next;
//        }
        while (aux.next!=null){
            aux=aux.next;
        }
        aux.next = null;

        return aux;
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty()) {
            throw new ListException("Doubly Linked List is empty");
        }
                for(int i=1;i<=size();i++){
                    for(int j=i+1;j<size();j++){
                        if(Utility.compare(getNode(j).data, getNode(i).data)<0){
                            Object aux=getNode(i).data;
                            getNode(i).data=getNode(j).data;
                            getNode(j).data=aux;
                        }
                    }
                }
            }


    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        int index = 1;
        while (aux != null) {
            if (Utility.compare(aux.data, element) == 0) return index;
            index++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return -1; //significa que el elemento no existe
    }
    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        while (aux.next!=null){ //se sale cuando aux.next sea nulo
            aux = aux.next;
        }
        Object dato = aux.data;
        return dato;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        if (Utility.compare(element, first.data)==0)
            return null;

        Node aux = first;
        Node current = first.next;

        while (current != null && current.data != element) {
            aux = current;
            current = current.next;
        }

        return current == null ? null : aux.data;
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        if (first.next ==null) return null; //Caso en el que haya solo un elemento en la lista
        if (first.next.next == null) return first.next.data; //Caso en el haya solo dos elementos en la lista


        Node aux = first;
        while (aux.next!= null && aux.data!=element){
            aux= aux.next;
        }

        return aux.next==null?null:  aux.next.data;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        int i = 1;
        while (aux != null) {
            if (Utility.compare(i, index) == 0) return aux;
            i++;
            aux = aux.next; //muevo aux al sgte nodo
        }
        return null; //si llega aqui, no encontro el nodo
    }

    @Override
    public String toString() {
        String result = "Singly Linked List Content\n";
        Node aux = first;
        while (aux != null) {
            result += aux.data + " ";
            aux = aux.next; //muevo aux al sgte nodo
        }
        return result;
    }
}
