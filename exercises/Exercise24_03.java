import java.util.Collection;
import java.util.Scanner;

/**
 * Created by casey on 2016-03-06.
 */
public class Exercise24_03 {
    public Exercise24_03() {
        TwoWayLinkedList<Double> list = new TwoWayLinkedList<>();
        System.out.print("Enter 7 integers: ");
        Scanner input = new Scanner(System.in);
        double[] v = new double[5];
        for (int i = 0; i < 5; i++)
            v[i] = input.nextDouble();

        list.add(v[1]);
        list.add(v[2]);
        list.add(v[3]);
        list.add(v[4]);
        list.add(0, v[0]);
        list.add(2, 10.55);
        list.remove(3);

        System.out.println(list);

        // java.util.ListIterator<Double> iterator1 = list.listIterator();
        // java.util.ListIterator<Double> iterator2 = list.listIterator(list.size() - 1);

        TwoWayLinkedList.LinkedListIterator iterator1 = (TwoWayLinkedList.LinkedListIterator) list.iterator();
        while (iterator1.hasNext())
            System.out.print(iterator1.next() + " ");

        TwoWayLinkedList.LinkedListIterator iterator2 = (TwoWayLinkedList.LinkedListIterator) list.iterator(list.size() - 1);
        System.out.println();
        while (iterator2.hasPrevious())
            System.out.print(iterator2.previous() + " ");
    }

    public static void main(String[] args) {
        new Exercise24_03();
    }

    public interface MyList<E> extends java.util.Collection<E> {
        /**
         * Add a new element at the specified index in this list
         */
        public void add(int index, E e);

        /**
         * Return the element from this list at the specified index
         */
        public E get(int index);

        /**
         * Return the index of the first matching element in this list.
         * Return -1 if no match.
         */
        public int indexOf(Object e);

        /**
         * Return the index of the last matching element in this list
         * Return -1 if no match.
         */
        public int lastIndexOf(E e);

        /**
         * Remove the element at the specified position in this list
         * Shift any subsequent elements to the left.
         * Return the element that was removed from the list.
         */
        public E remove(int index);

        /**
         * Replace the element at the specified position in this list
         * with the specified element and returns the new set.
         */
        public E set(int index, E e);

        @Override
        /** Add a new element at the end of this list */
        public default boolean add(E e) {
            add(size(), e);
            return true;
        }

        @Override
        /** Return true if this list contains no elements */
        public default boolean isEmpty() {
            return size() == 0;
        }

        @Override
        /** Remove the first occurrence of the element e
         *  from this list. Shift any subsequent elements to the left.
         *  Return true if the element is removed. */
        public default boolean remove(Object e) {
            if (indexOf(e) >= 0) {
                remove(indexOf(e));
                return true;
            } else
                return false;
        }

        @Override
        public default boolean containsAll(Collection<?> c) {
            for (Object i : c) {
                if (!contains(i)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public default boolean addAll(Collection<? extends E> c) {
            for (Object i : c) {
                add((E) i);
            }
            return true;
        }

        @Override
        public default boolean removeAll(Collection<?> c) {
            boolean modded = false;
            for (Object i : c) {
                if (remove(i)) {
                    modded = true;
                }
            }
            return true;
        }

        @Override
        public default boolean retainAll(Collection<?> c) {
            boolean modded = false;
            for (int i = 0; i < size(); i++) {
                if (!c.contains(get(i))) {
                    remove(i);
                    modded = true;
                }
            }
            return modded;
        }

        @Override
        public default Object[] toArray() {
            Object[] arr = new Object[size()];
            for (int i = 0; i < size(); i++) {
                arr[i] = get(i);
            }
            return arr;
        }

        @Override
        public default <T> T[] toArray(T[] array) {
            T[] arr = (T[]) new String[array.length];
            for (int i = 0; i < array.length; i++) {
                arr[i] = (T) get(i);
            }
            return arr;
        }
    }

    public class TwoWayLinkedList<E> implements MyList<E> {
        private Node<E> head, tail;
        private int size = 0; // Number of elements in the list

        /**
         * Create an empty list
         */
        public TwoWayLinkedList() {
        }

        /**
         * Create a list from an array of objects
         */
        public TwoWayLinkedList(E[] objects) {
            for (int i = 0; i < objects.length; i++)
                add(objects[i]);
        }

        /**
         * Return the head element in the list
         */
        public E getFirst() {
            if (size == 0) {
                return null;
            } else {
                return head.element;
            }
        }

        /**
         * Return the last element in the list
         */
        public E getLast() {
            if (size == 0) {
                return null;
            } else {
                return tail.element;
            }
        }

        /**
         * Add an element to the beginning of the list
         */
        public void addFirst(E e) {
            Node<E> newNode = new Node<>(e); // Create a new node
            newNode.next = head; // link the new node with the head
            head = newNode; // head points to the new node
            size++; // Increase list size

            if (tail == null) // the new node is the only node in list
                tail = head;
            else
                (head.next).prev = head;
        }

        /**
         * Add an element to the end of the list
         */
        public void addLast(E e) {
            Node<E> newNode = new Node<>(e); // Create a new for element e

            if (tail == null) {
                head = tail = newNode; // The new node is the only node in list
            } else {
                newNode.prev = tail;
                tail.next = newNode; // Link the new with the last node
                tail = newNode; // tail now points to the last node
            }

            size++; // Increase size
        }

        @Override
        /** Add a new element at the specified index
         * in this list. The index of the head element is 0 */
        public void add(int index, E e) {
            if (index == 0) {
                addFirst(e);
            } else if (index >= size) {
                addLast(e);
            } else {
                Node<E> toadd = new Node<>(e);
                Node<E> atindex = head;
                for (int i = 0; i <= index; i++) {
                    atindex = atindex.next;
                }
                Node<E> prev = atindex.prev;
                prev.next = toadd;
                toadd.prev = prev;
                toadd.next = atindex;
                atindex.prev = toadd;
                size++;
            }
        }

        /**
         * Remove the head node and
         * return the object that is contained in the removed node.
         */
        public E removeFirst() {
            if (size == 0) {
                return null;
            } else {
                E temp = head.element;
                head = head.next;
                head.prev = null;
                size--;
                if (head == null) {
                    tail = null;
                }
                return temp;
            }
        }

        /**
         * Remove the last node and
         * return the object that is contained in the removed node.
         */
        public E removeLast() {
            if (size == 0) {
                return null;
            } else if (size == 1) {
                E temp = head.element;
                head = tail = null;
                size = 0;
                return temp;
            } else {
                Node<E> current = head;

                for (int i = 0; i < size - 2; i++) {
                    current = current.next;
                }

                E temp = tail.element;
                tail = current;
                tail.next = null;
                size--;
                return temp;
            }
        }

        @Override
        /** Remove the element at the specified position in this
         *  list. Return the element that was removed from the list. */
        public E remove(int index) {
            if (index < 0 || index >= size) {
                return null;
            } else if (index == 0) {
                return removeFirst();
            } else if (index == size - 1) {
                return removeLast();
            } else {
                Node<E> toremove = head;
                for (int i = 1; i < index; i++) {
                    toremove = toremove.next;
                }
                Node<E> prev = toremove.prev;
                Node<E> next = toremove.next;
                prev.next = next;
                next.prev = prev;
                size--;
                return toremove.element;
            }
        }

        public void demonstrate() {
            Node<E> current = head;
            while (current.next != null) {
                String pVal = "null";
                if (current.prev != null) {
                    pVal = current.prev.element.toString();
                }
                System.out.println("PREV: " + pVal + ", CURR: " + current.element + ", NEXT: " + current.next.element);
                current = current.next;
            }
        }

        @Override
        /** Override toString() to return elements in the list */
        public String toString() {
            StringBuilder result = new StringBuilder("[");

            Node<E> current = head;
            for (int i = 0; i < size; i++) {
                result.append(current.element);
                current = current.next;
                if (current != null) {
                    result.append(", "); // Separate two elements with a comma
                } else {
                    result.append("]"); // Insert the closing ] in the string
                }
            }

            return result.toString();
        }

        @Override
        /** Clear the list */
        public void clear() {
            size = 0;
            head = tail = null;
        }

        @Override
        /** Return true if this list contains the element e */
        public boolean contains(Object e) {
            Node<E> current = head;
            for (int i = 0; i < size; i++) {
                if (current.element.equals(e)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        private void checkLimits(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds");
            }
        }

        @Override
        /** Return the element at the specified index */
        public E get(int index) {
            checkLimits(index);
            Node<E> current = head;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return current.element;
                }
                current = current.next;
            }
            return null;
        }

        @Override
        /** Return the index of the head matching element in
         *  this list. Return -1 if no match. */
        public int indexOf(Object e) {
            Node<E> current = head;
            for (int i = 0; i < size; i++) {
                if (current.element.equals(e)) {
                    return i;
                }
                current = current.next;
            }
            return -1;
        }

        @Override
        /** Return the index of the last matching element in
         *  this list. Return -1 if no match. */
        public int lastIndexOf(E e) {
            Node<E> current = head;
            int last = -1;
            for (int i = 0; i < size; i++) {
                if (current.element.equals(e)) {
                    last = i;
                }
                current = current.next;
            }
            return last;
        }

        @Override
        /** Replace the element at the specified position
         *  in this list with the specified element. */
        public E set(int index, E e) {
            checkLimits(index);
            Node<E> current = head;
            E old = null;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    old = current.element;
                    current.element = e;
                    break;
                }
                current = current.next;
            }
            return old;
        }

        @Override
        /** Override iterator() defined in Iterable */
        public java.util.Iterator<E> iterator() {
            return new LinkedListIterator();
        }

        public java.util.Iterator<E> iterator(int i) {
            return new LinkedListIterator(i);
        }

        private class LinkedListIterator implements java.util.Iterator<E> {
            private Node<E> current;

            public LinkedListIterator() {
                current = head;
            }

            public LinkedListIterator(int index) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            public boolean hasPrevious() {
                return current != null;
            }

            public E previous() {
                E e = current.element;
                current = current.prev;
                return e;
            }

            @Override
            public E next() {
                E e = current.element;
                current = current.next;
                return e;
            }

            @Override
            public void remove() {
                // Left as an exercise
            }
        }

        private class Node<E> {
            E element;
            Node<E> prev;
            Node<E> next;

            public Node(E element) {
                this.element = element;
            }
        }

        @Override
        /** Return the number of elements in this list */
        public int size() {
            return size;
        }
    }
}
