package pro.sky.Algoritme1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pro.sky.Algoritme1.exception.InvalidIndexException;
import pro.sky.Algoritme1.exception.NullItemException;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerListTest {
    private final IntegerList out = new IntegerListImpl();

    private final static Random RANDOM = new Random();
    private static int SIZE;

    @BeforeEach
    void setup() {
        RANDOM.ints(5, 100, 105)
                .forEach(out::add);

        SIZE = out.size();
    }

    @Test
    void testAddAtIndex() {
        out.add(111);
        out.add(222);
        out.add(0, 333);
        assertEquals(8, out.size());
        assertEquals(333, out.get(0));

    }

    @Test
    void testSet() {
        out.add(0, 333);
        out.set(5, 111);
//      assertEquals(6, out.size());
        assertEquals(333, out.get(0));
    }


    @Test
    void testRemoveByIndex() {
        out.add(0, 333);
        out.remove(2);
//        assertEquals(5, out.size());
        assertEquals(333, out.get(0));
    }

    @Test
    void testContains() {
        out.add(111);
        out.add(222);
 //     assertTrue(out.contains(111));
        assertFalse(out.contains(456));
    }


    @Test
    void testIndexOf() {
        out.add(111);
        assertEquals(5, out.indexOf(111));
    }

    @Test
    void testLastIndexOf() {
        out.add(222);
        out.add(333);
        assertEquals(5, out.lastIndexOf(222));
        assertEquals(6, out.lastIndexOf(333));
    }

    @Test
    void testGet() {
        out.add(0,444);
        assertEquals(444, out.get(0));
    }
    @Test
    void testEquals() {
        IntegerList otherList = new IntegerListImpl();
        otherList.add(900);
        otherList.add(901);
        otherList.add(902);
        assertTrue(out.equals(otherList));
    }

    @Test
    void testSize() {
        assertEquals(5, out.size());
    }

    @Test
    void testIsEmptyAndClear() {
  //      assertFalse(out.isEmpty());
        out.clear();
       assertTrue(out.isEmpty());
        assertEquals(0, out.size());
    }

    @Test
    void testToArray() {
        out.clear();
        out.add(111);
        out.add(222);
        out.add(333);
        Integer[] array = out.toArray();
        assertEquals(3, array.length);
        assertEquals(111, array[0]);
        assertEquals(222, array[1]);
        assertEquals(333, array[2]);
    }


    @ParameterizedTest
    @ValueSource(ints = {-15, 0, 10})
    void add(Integer item) {
        int expected = item;

        int actual = out.add(item);

        assertEquals(expected, actual);
        assertNotEquals(SIZE, out.size());
    }

    @ParameterizedTest
    @NullSource
    void add_whenItemIsNull_shouldThrowsNullItemException(Integer invalidItem) {
        assertThrows(NullItemException.class,
                () -> out.add(invalidItem));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPositiveTest")
    void add(int index, Integer item) {
        setup();

        int actual = out.add(index, item);

        assertEquals(item, actual);
        assertEquals(out.get(index), item);
        assertNotEquals(SIZE, out.size());
    }


    @ParameterizedTest
    @MethodSource("provideParamsForPositiveTest")
    void set(int index, Integer item) {
        setup();

        int actual = out.set(index, item);

        assertEquals(item, actual);
        assertTrue(out.contains(item));
    }


    @ParameterizedTest
    @MethodSource("provideInvalidIndexForNegativeTest")
    void set_whenInvalidIndex_shouldThrowsInvalidIndexException(int index, Integer item) {
        assertThrows(InvalidIndexException.class,
                () -> out.set(index, item));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void remove(Integer item) {
        Integer expected = out.get(item);

        Integer actual = out.remove(expected);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void remove(int index) {
        Integer expected = out.get(2);

        Integer actual = out.remove(2);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @NullSource
    void remove_whenitemIsNull_shouldThrowsNullItemException(Integer invaliditem) {
        assertThrows(NullItemException.class,
                () -> out.remove(invaliditem));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIndexForNegativeTest")
    void remove_whenInvalidIndex_shouldThrowsInvalidIndexException(int index) {
        assertThrows(InvalidIndexException.class,
                () -> out.remove(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-56, 0, 362})
    void remove_whenElementNotExist_shouldThrowsInvalidIndexException(Integer item) {
        assertThrows(InvalidIndexException.class,
                () -> out.remove(item));
    }

    @ParameterizedTest
    @CsvSource(value = {"0, 555", "4, -19", "3, 0"})
    void contains(int index, Integer item) {
        out.add(index, item);

        boolean actual = out.contains(item);

        assertTrue(actual);
    }



    @ParameterizedTest
    @ValueSource(ints = {-34, 0, 59})
    void contains_negativeTest(Integer item) {
        boolean actual = out.contains(item);

        assertFalse(actual);
    }


    @ParameterizedTest
    @CsvSource(value = {"1, 555", "4, -19", "3, 0"})
    void indexOf(int index, Integer item) {
        out.add(index, item);

        int actual = out.indexOf(item);

        assertEquals(index, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {325, 0, -12})
    void indexOf_negativeTest(Integer item) {
        int expected = -1;

        int actual = out.indexOf(item);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"0, -169", "2, 0", "4, 57"})
    void lastIndexOf(int index, Integer item) {
        out.add(index, item);
 //       out.add(index, item);
        int expected = index + 1;

        int actualIndex = out.lastIndexOf(item);

        assertEquals(expected, actualIndex);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -32, 256})
    void lastIndexOf_negativeTest(Integer item) {
        int expected = -1;

        int actual = out.lastIndexOf(item);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void get_negativeTest(int index) {
        assertThrows(InvalidIndexException.class,
                () -> out.get(index));
    }

    @Test
    void equals() {
        IntegerList expected = new IntegerListImpl();
        Arrays.stream(out.toArray())
                .forEach(expected::add);

        assertTrue(expected.equals(out));
    }

    @Test
    void equals_negativeTest() {
        IntegerList expected = new IntegerListImpl();

        assertFalse(expected.equals(out));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void size_whenRemoveElement_shouldReturnCorrectSize(int index) {
        out.remove(2);
        int expected = SIZE - 1;

        int actual = out.size();

        assertEquals(expected, actual);
    }

    @Test
    void isEmpty_whenNotEmpty_shouldReturnFalse() {
        assertFalse(out.isEmpty());
    }

    @Test
    void isEmpty_whenIsEmpty_shouldReturnTrue() {
           out.clear();

           assertTrue(out.isEmpty());
    }


    @Test
    void clear() {
        out.clear();

        assertEquals(0, out.size());
    }

    @Test
    void toArray() {
        assertNotNull(out.toArray());
        assertEquals(SIZE, out.toArray().length);
    }

    private static Stream<Arguments> provideParamsForPositiveTest() {
        return Stream.of(
                Arguments.of(0, -37),
                Arguments.of(0, 555),
                Arguments.of(3, -11),
                Arguments.of(4, -69),
                Arguments.of(8, 0)
        );
    }

    private static Stream<Arguments> provideInvalidIndexForNegativeTest() {
        return Stream.of(
                Arguments.of(-1, -37),
                Arguments.of(5, 555)
        );
    }
}
