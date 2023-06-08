package TDA;

public class CircularLinkedList implements List {
    private Node first; //apunta al inicio de la lista
    private Node last; //apunta al final de la lista

    public CircularLinkedList() {
        this.first = last = null;
    }

    @Override
    public int size() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        int count = 0;
        while (aux != last) {
            count++;
            aux = aux.next; //muevo aux al sgte nodo
        }
        //Cuando aux sea el ultimo, sale del ciclo, pero no aumenta ese ultimo contador
        if (aux == last) {
            count++;
        }
        return count;
    }

    @Override
    public void clear() {
        first = last = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        while (aux != last) {
            if (util.Utility.compare(aux.data, element) == 0) return true;
            aux = aux.next; //muevo aux al sgte nodo
        }
        //se sale cuando aux==last
        return util.Utility.compare(aux.data, element) == 0;
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode; //last siempre queda en el ult nodo
            //hago el enlace circular
            last.next = first;
        }

    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
            //hago el enlace circular
            last.next = first;
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
            first = last = newNode;
        } else {
            //Cuando first.data es mayor que element
            if (util.Utility.compare(first.data, element) > 0) {
                newNode.next = first;
                first = newNode;
            } else {
                Node prev = first; //rastro o marca
                Node aux = first.next;
                //se hace asi, ya que comparamos el first con anterioridad
                boolean added = false;
                while (aux != last && !added) {
                    if (util.Utility.compare(aux.data, element) > 0) {
                        prev.next = newNode;
                        newNode.next = aux;
                        added = true;
                    }
                    prev = aux;
                    aux = aux.next;
                }
                //aqui enlazamos cuando aux=last
                if ((util.Utility.compare(aux.data, element) == 0) && !added) {
                    prev.next = newNode;
                    newNode.next = aux;
                } else{ //enlaza al final
                    if (!added) {
                        aux.next = newNode;
                        //muevo last al ult nodo
                        last = newNode;
                    }
                }
            }
        }
        //hago el enlace circular
        last.next = first;
    }

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        //Caso 1. El elemento a suprimir es el primero de la lista
        if (util.Utility.compare(first.data, element) == 0) {
            first = first.next;
        } else {
            //Caso 2. El elemento puede estar en cualquier parte
            Node prev = first;
            Node aux = first.next;
            while (aux != last && !(util.Utility.compare(aux.data, element) == 0)) {
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando aux=last o cuando encuentra el elemento
            if (util.Utility.compare(aux.data, element) == 0) {
                //desenlaza el nodo con el elemento a eliminar
                prev.next = aux.next;
                //nos aseguraos que last quede apuntando al ult nodo
                if (aux == last) { //estamos en el ult nodo
                    last = prev;
                }
            }
        }
        //mantengo el enlace circular
        last.next = first;
        //ultima validacion
        //que pasa si solo queda un nodo y es el q queremos eliminar
        if (first == last && util.Utility.compare(first.data, element) == 0) {
            clear();
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Object element = first.data;
        first = first.next;
        //hago el enlace circular
        last.next = first;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        Node prev = first;
        while (aux != last) {
            prev = aux;
            aux = aux.next;
        }
        //aux esta en el ultimo nodo, es el q queremos eliminar
        Object element = aux.data;
        prev.next = first; //re-enlazamos el ult nodo
        return element;
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty())
            throw new ListException("Circular Linked List is empty");
        for (int i = 1; i <= size(); i++) {
            for (int j = i + 1; j < size(); j++) {
                if (util.Utility.compare(getNode(j).data, getNode(i).data) < 0) {
                    Object aux = getNode(i).data;
                    getNode(i).data = getNode(j).data;
                    getNode(j).data = aux;
                }//if
            }//for j
        }//for i
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        int index = 1;
        while (aux != last) {
            if (util.Utility.compare(aux.data, element) == 0) return index;
            index++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        //se sale cuando estamos en el ult nodo
        if (util.Utility.compare(aux.data, element) == 0) return index;
        return -1; //significa que el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        return this.first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        if (util.Utility.compare(first.data, element) == 0) {
            return "It's the first, it has no prev";
        }
        Node aux = first;
        while (aux.next != last) {
            if (util.Utility.compare(aux.next.data, element) == 0) {
                return aux.data;
            }
            aux = aux.next; //muevo aux al sgte nodo
        }
        //se sale cuando aux == last
        if (util.Utility.compare(aux.data, element) == 0) {
            return aux.data;
        }
        return "Does not exist in Single Linked List";
    }

    //coigo original
/*    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        while (aux != null) {
            if (util.Utility.compare(aux.data, element) == 0) {
                if (aux.next != null) return aux.next.data;
                else return "Has no next";
            }
            aux = aux.next; //muevo aux al sgte nodo
        }
        return "Does not exist in Circular Linked List";
    }*/

    //codigo nuevo (prueba)
    @Override
    public Object getNext(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        while(aux!=last){
            if(util.Utility.compare(aux.data, element)==0){
                if (aux.next != null) return aux.next.data;
            }
            aux = aux.next; //muevo aux al sgte nodo
        }
        //se sale cuando aux==last
        if(util.Utility.compare(aux.data, element)==0){
            return aux.next.data; //el elemento anterior
        }
        return "Does not exist in Circular Linked List";
    }


   /* @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        int i = 1;
        while (aux != last) {
            if (util.Utility.compare(i, index) == 0) return aux;
            i++;
            aux = aux.next; //muevo aux al sgte nodo
        }
        //se sale cuando aux==last
        if (util.Utility.compare(i, index) == 0) {
            return aux;
        }
        return null; //si llega aqui, no encontro el nodo
    }
*/
    //metodo original
    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        int i = 1;
        while(aux!=null){
            if(util.Utility.compare(i, index)==0) return aux;
            i++;
            aux = aux.next; //muevo aux al sgte nodo
        }
        return null; //si llega aqui, no encontro el nodo
    }


    //metodo nuevo de prueba
    @Override
    public String toString() {
        String result = "Circular Linked List Content\n";
        Node aux = first;
        while (aux != last) {
            result += aux.data + " \n";
            aux = aux.next; //muevo aux al sgte nodo
        }
        //se sale cuando aux==last
        //agregamos la info del ultimo nodo
        return result + " " + aux.data;
    }


    //metodo origina:
//    @Override
//    public String toString(){
//        String result="\n";
//        Node aux = first;
//        int count=0;
//        try {
//            while (++count <= size()) {
//                result += aux.data + " ";
//                aux = aux.next; //muevo aux al sgte nodo
//            }
//        }catch (ListException ex){
//
//        }
//        return result;
//    }


}