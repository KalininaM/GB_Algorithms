package HomeWork2;

public class Program {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(6);
        list.add(4);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(0);
        System.out.println("Первоначальный связный список: ");
        System.out.println(list);
        System.out.println("Разворот: ");
        list.reverse();
        System.out.println(list);
    }
}
