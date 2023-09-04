package HomeWork2;

public class LinkedList <T>{
    private Node head;
    public class Node{
        public Node next;
        public T value;

        @Override
        public String toString() {
            return "Node{" +
                    "next=" + next +
                    ", value=" + value +
                    '}';
        }
    }
    public void add(T value){
        Node node = new Node();
        node.value = value;
        if (head != null){
            node.next = head;
        }
        head = node;
    }
    public void reverse(){
        Node node = head;
        T last = null;
        while (node != null){
            last = node.value;
            node = node.next;
        }
        node = head;
        T tmp2 = null;
        while (head.value != last){
            if (node.next != null && node.next.value != tmp2) {
                T tmp = node.value;
                node.value = node.next.value;
                node.next.value = tmp;
                node = node.next;
            }
            else {
                tmp2 = node.value;
                node = head;
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node node = head;
        while (node != null){
            stringBuilder.append(node.value);
            node = node.next;
            if (node != null)
                stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
