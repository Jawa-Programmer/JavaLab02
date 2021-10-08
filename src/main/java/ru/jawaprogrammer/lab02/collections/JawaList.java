package ru.jawaprogrammer.lab02.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * Двунаправленный список
 */
public class JawaList<T extends Comparable> implements Iterable<T> {

    private class Iterator implements java.util.Iterator<T> {
        Item cur;

        private Iterator(Item start) {
            cur = start;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public T next() {
            T ret = cur.data;
            cur = cur.next;
            return ret;
        }
    }

    private class Item {
        T data;
        Item prev, next;

        public Item(T data, Item prev, Item next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        // Данный метод вызывается перед удалением элемента списка. Помогает сборщику мусора поскорее удалить неиспользуемые экземпляры
        public void clear() {
            data = null;
            next = null;
            prev = null;
        }
    }

    private Item head, tail;
    private int size;

    // можно еще оптимизировать поиск (если pos > size/2, то искать от конца), но мне лень))
    private Item itemByPos(int pos) {
        Item cur = head;
        for (int i = 0; i < pos; ++i)
            cur = cur.next;
        return cur;
    }

    /**
     * Конструктор инициализирует пустой список
     */
    public JawaList() {
        size = 0;
        head = tail = null;
    }

    /**
     * констуктор инициализирует список и наполняет его элементами переданной коллекции
     * @param list коллекция для наполнения списка
     */
    public JawaList(Iterable<T> list) {
        size = 0;
        head = tail = null;
        for (T item : list)
            add(item);
    }

    /**
     * Вставляет новый элемент в конец списка
     *
     * @param val элемент для вставки
     */
    public void add(T val) {
        if (head == null) {
            tail = head = new Item(val, null, null);
            size = 1;
        } else {
            tail.next = new Item(val, tail, null);
            tail = tail.next;
            ++size;
        }
    }

    /**
     * Вставляет новый элемент в указанную позицию. если позиция меньше нуля, будет выброшено исключение IndexOutOfBoundsException.
     * Если индекс больше или равен размеру списка, элемент будет вставлен в конец.
     *
     * @param val данные для вставки
     * @param pos позиция вставки
     * @throws IndexOutOfBoundsException если индекс меньше ноля
     */
    public void add(T val, int pos) {
        if (pos >= size) add(val); // если индекс больше или равен размеру списка - пихаем новый элемент в конец.
        else if (pos < 0)
            throw new IndexOutOfBoundsException("Индекс не может быль меньше ноля. Переданный индекс: " + pos);
        else if (pos == 0) {
            head = new Item(val, null, head);
            ++size;
        } else {
            Item it = itemByPos(pos);
            it.prev.next = new Item(val, it.prev, it);
            ++size;
        }
    }

    /**
     * Удаляет элемент списка по его позиции.
     *
     * @param pos позиция элемента в списке
     * @return значение удаляемого из списка элемента
     * @throws IndexOutOfBoundsException если индекс меньше ноля или не меньше размера списка.
     */
    public T remove(int pos) {
        if (pos < 0 || pos >= size)
            throw new IndexOutOfBoundsException("Индекс выходит за границы списка. Переданный индекс: " + pos);
        else if (pos == 0) {
            T ret = head.data;
            Item tmp = head;
            head = head.next;
            tmp.clear();
            head.prev = null;
            --size;
            return ret;
        } else if (pos == size - 1) {
            T ret = tail.data;
            Item tmp = tail;
            tail = tail.prev;
            tail.next = null;
            tmp.clear();
            --size;
            return ret;
        } else {
            Item it = itemByPos(pos);
            T ret = it.data;
            it.prev.next = it.next;
            it.clear();
            --size;
            return ret;
        }
    }

    /**
     * Замещает элемент списка на переданный по его позиции.
     *
     * @param pos позиция элемента в списке
     * @return значение заменяемого элемента
     * @throws IndexOutOfBoundsException если индекс меньше ноля или не меньше размера списка.
     */
    public T set(T val, int pos) {
        if (pos < 0 || pos >= size)
            throw new IndexOutOfBoundsException("Индекс выходит за границы списка. Переданный индекс: " + pos);
        else if (pos == 0) {
            T ret = head.data;
            head.data = val;
            return ret;
        } else if (pos == size - 1) {
            T ret = tail.data;
            tail.data = val;
            return ret;
        } else {
            Item it = itemByPos(pos);
            T ret = it.data;
            it.data = val;
            return ret;
        }
    }

    /**
     * Возвращает размер списка
     *
     * @return количество элементов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет список на пустоту
     *
     * @return size == 0
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * проверяет наличие элемента в списке
     *
     * @param val элемент для проверки
     * @return true, если хотя бы один элемент списка возвращает 0 при сравнении с val через compareTo. Иначе false
     */
    public boolean contains(T val) {
        return indexOf(val) != -1;
    }

    /**
     * Возвращает первое вхождение переданного значения (проверка через compareTo)
     *
     * @param val элемент для проверки
     * @return индекс первого вхождения элемента в список, или -1, если элемент не входит в список
     */
    public int indexOf(T val) {
        int i = 0;
        for (Item cur = head; cur != null; cur = cur.next, ++i) {
            if (cur.data.compareTo(val) == 0) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Item cur = head; cur != null; cur = cur.next)
            sb.append(cur.data).append(", ");
        sb.append(']');
        return sb.toString();
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator(head);
    }

    /**
     * Метод очищает список.
     */
    public void clear() {
        for (Item cur = head; cur != null; ) {
            Item tmp = cur.next;
            cur.clear();
            cur = tmp;
        }
    }

    /**
     * Метод производит слияние двух списков. Возвращается новый список, содержащий элементы обоих. Если исходные списки были отсортированы от меньшего к большему, то новый список так же будет отсортирован
     *
     * @param b список для слияния
     * @return новый список, содержащий элементы текущего списка и списка b
     */
    public JawaList<T> merge(@NotNull JawaList<T> b) {
        JawaList<T> ret = new JawaList<>();
        if (size != 0 && b.size != 0) // оба списка не пусты
        {
            Item i1 = head, i2 = b.head;
            while (i1 != null && i2 != null) {
                if (i2.data.compareTo(i1.data) < 0) {
                    ret.add(i2.data);
                    i2 = i2.next;
                } else {
                    ret.add(i1.data);
                    i1 = i1.next;
                }
            }
            while (i1 != null) {
                ret.add(i1.data);
                i1 = i1.next;
            }
            while (i2 != null) {
                ret.add(i2.data);
                i2 = i2.next;
            }
        } else if (size != 0) { // текущий список не пуст
            for (T it : this) ret.add(it);
        } else if (b.size != 0) {// если текущий список пуст - копируем b
            for (T it : b) ret.add(it);
        }
        // если оба списка пусты, то ничего делать не нужно
        return ret;
    }
}
