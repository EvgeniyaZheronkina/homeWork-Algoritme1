package pro.sky.Algoritme1;

import pro.sky.Algoritme1.exception.InvalidIndexException;
import pro.sky.Algoritme1.exception.NullItemException;
import pro.sky.Algoritme1.exception.StorageIsFullException;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList {

    private Integer[] storage;
    private int size;

    public IntegerListImpl() {
        this(10);
    }

    public IntegerListImpl(int initSize) {
        storage = new Integer[initSize];
    }


    @Override
    public Integer add(Integer item) {
        if (item == null) {
            throw new NullItemException("Null not supported");
        }
        extendedArrayIfNeeded();

        storage[size] = item;
        size++;

        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (item == null) {
            throw new NullItemException("Null not supported");
        }

        if (index > size - 1) {
            throw new InvalidIndexException();
        }
        extendedArrayIfNeeded();

        for (int i = size - 1; i > index; i--) {
            storage[i] = storage[i - 1];
        }

        storage[index] = item;

        size++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        if (item == null) {
            throw new NullItemException("Null not supported");
        }

        if (index > size - 1) {
            throw new InvalidIndexException();
        }
        storage[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        if (item == null) {
            throw new NullItemException("Null not supported");
        }

        int index = indexOf(item);
        if (index != -size) {
            return remove(index);
        }
        throw new NullItemException("Item not found");

    }

    @Override
    public Integer remove(int index) {
        if (index > size - 1) {
            throw new InvalidIndexException();
        }

        Integer item = storage[index];

        for (int i = index; i < storage.length; i++) {
            if (i == storage.length - 1) {
                storage[i] = null;
            } else {
                storage[i] = storage[i + 1];
            }

        }
        size--;
        splitIfNeeded();
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        Integer[] storageCopy = toArray();
        sort(storageCopy);
        return binarySearch(storageCopy, item);
    }

    @Override
    public int indexOf(Integer item) {
        if (item == null) {
            throw new NullItemException("Null not supported");
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        if (item == null) {
            throw new NullItemException("Null not supported");
        }

        for (int i = size - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index > size - 1) {
            throw new InvalidIndexException();
        }

        return storage[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if(otherList == null) return false;
        if (otherList.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        splitIfNeeded();
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] temp = new Integer[size];
        System.arraycopy(storage, 0, temp, 0, temp.length);
        return temp;
    }

    private void extendedArrayIfNeeded() {
        if (size == storage.length - 1) {
            int length = (int) (storage.length * 1.5);
            Integer[] tempStorage = new Integer[length];
            System.arraycopy(storage, 0, tempStorage, 0, storage.length);
            storage = tempStorage;
        }
    }

    private void splitIfNeeded() {
        if (size < storage.length / 3) {
            int length = (int) (storage.length / 1.5);
            Integer[] tempStorage = new Integer[length];
            System.arraycopy(storage, 0, tempStorage, 0, storage.length);
            storage = tempStorage;
        }
    }


    private static void sort(Integer[] storage) {
        for (int i = 1; i < storage.length; i++) {
            int temp = storage[i];
            int j = i;
            while (j > 0 && storage[j - 1] >= temp) {
                storage[j] = storage[j - 1];
                j--;
            }
            storage[j] = temp;
        }
    }

    private static int partition(Integer[] storage, int low, int high) {
        int pivot = storage[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (storage[j] <= pivot) {
                i++;

                int temp = storage[i];
                storage[i] = storage[j];
                storage[j] = temp;
            }
        }
        int temp = storage[i + 1];
        storage[i + 1] = storage[high];
        storage[high] = temp;

        return i + 1;
    }

    private boolean binarySearch(Integer[] storage, Integer item) {
        int min = 0;
        int max = storage.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (item == storage[mid]) {
                return true;
            }

            if (item < storage[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }


}
