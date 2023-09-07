package HomeWork4;

import java.util.Iterator;
import java.util.ArrayList;

public class HashMap<K, V> implements Iterable<HashMap.Entity> {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;

    private Bucket[] buckets;
    private int size;


    @Override
    public Iterator<HashMap.Entity> iterator() {
        return new HashMapIterator();
    }

    class HashMapIterator implements Iterator<HashMap.Entity> {

        /**
         *TODO: Необходимо доработать структуру класса HashMap, реализованную на семинаре.
         * У нас появились методы добавления, удаления и поиска элемента по ключу.
         * Добавить возможность перебора всех элементов нашей структуры данных,
         * необходимо добавить несколько элементов,
         * а затем перебрать все элементы таблицы используя цикл foreach.
         * Подумайте, возможно вам стоит обратиться к интерфейсу Iterable.
         * @return
         */
        int index;
        ArrayList<Entity> entity = new ArrayList<>();
        int entityIndex;
        public HashMapIterator(){
            this.index = 0;
            this.entityIndex = -1;
        }

        @Override
        public boolean hasNext() {
            while (buckets[index] == null && index < buckets.length-1 ||
                    buckets[index] != null && buckets[index].head == null){
                index+=1;
            }
            return index < buckets.length-1 || entityIndex < entity.size()-1;
        }

        @Override
        public Entity next() {

            if (buckets[index] != null || index != buckets.length-1) {
                Bucket.Node node = buckets[index].head;
                while (node != null) {
                    entity.add(node.value);
                    node = node.next;
                }
                index += 1;
            }
            entityIndex+=1;
            return entity.get(entityIndex);
        }
    }


    /**
     * TODO: Вывести все элементы хеш-таблицы на экран через toString()
     * @return
     */
    @Override
    public String toString() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                Bucket.Node node = buckets[i].head;
                while (node != null){
                    System.out.println("Ячейка №" + i + " - " + node.value);
                    node = node.next;
                }
            }
        }
        return "";
    }



    /**
     * Элемент хеш-таблицы
     */
    class Entity{

        /**
         * Ключ
         */
        K key;

        /**
         * Значение элемента
         */
        V value;
        @Override
        public String toString() {
            return "Содержимое = " +
                    "телефон (ключ): " + key +
                    ", имя (значение): " + value;
        }
    }

    /**
     * Бакет, связный список
     */
    class Bucket{

        /**
         * Указатель на первый элемент связного списка
         */
        Node head;

        @Override
        public String toString() {
            return head.toString();
        }
        /**
         * Узел бакета (связного списка)
         */
        class Node{

            /**
             * Указатель на следующий элемент связного списка
             */
            Node next;

            /**
             * Значение узла, указывающее на элемент хеш-таблицы
             */
            Entity value;

            @Override
            public String toString() {
                return value.toString();
            }
        }

        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null){
                    currentNode = currentNode.next;
                }
                else {
                    currentNode.next = node;
                    return null;
                }
            }
        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = head.value.value;
                head = head.next;
                return buf;
            }
            else {
                Node node = head;
                while (node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key))
                    return node.value.value;
                node = node.next;
            }
            return null;
        }

    }

    private int calculateBucketIndex(K key){
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private void recalculate(){
        size = 0;
        Bucket[] old = buckets;
        buckets = new HashMap.Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++){
            Bucket bucket = old[i];
            if (bucket != null){
                Bucket.Node node = bucket.head;
                while (node != null){
                    put(node.value.key, node.value.value);
                    node = node.next;
                }
            }
        }
    }

    public V put(K key, V value){
        if (size >= buckets.length * LOAD_FACTOR ){
            recalculate();
        }
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = bucket.add(entity);
        if (buf == null){
            size++;
        }
        return buf;
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        V buf = bucket.remove(key);
        if (buf != null){
            size--;
        }
        return buf;
    }

    public HashMap(){
        buckets = new HashMap.Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int initCount){
        buckets = new HashMap.Bucket[initCount];
    }


}
