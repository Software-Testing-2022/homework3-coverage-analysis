package at.jku.swtesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class RingBufferCoverageTest {

    private RingBuffer<String> ringBuffer;

    @BeforeEach
    void setUp() {
        ringBuffer = new RingBuffer<>(3);
    }

    @Test
    void testRingBufferConstructor() {
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    void testRingBufferConstructorBufferException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RingBuffer<>(0);
        });
        assertEquals("Initial capacity is less than one", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideDifferentStringCapacties")
    void testCapacity(String... bufferElements) {
        setUpBuffer(bufferElements);
        assertEquals(3, ringBuffer.capacity());
    }

    @ParameterizedTest
    @MethodSource("provideDifferentSizes")
    void testSize(int referenceSize, String... bufferElements) {
        setUpBuffer(bufferElements);
        assertEquals(referenceSize, ringBuffer.size());

        ringBuffer.dequeue();
        assertEquals(--referenceSize, ringBuffer.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertFalse(ringBuffer.isEmpty());
    }

    @Test
    void testIsFull() {
        setUpBuffer(new String[] { "1", "2", "3" });
        assertTrue(ringBuffer.isFull());
        ringBuffer.dequeue();
        assertFalse(ringBuffer.isFull());
    }

    @Test
    void testEnqueueOverriding() {
        ringBuffer.enqueue("a");
        ringBuffer.enqueue("b");
        ringBuffer.enqueue("c");

        ringBuffer.enqueue("d");
        assertEquals("d", ringBuffer.peek());
        ringBuffer.enqueue("e");
        assertEquals("e", ringBuffer.peek());
        ringBuffer.enqueue("f");
        assertEquals("f", ringBuffer.peek());
    }

    private String getLastBufferElement() {
        String element = "";
        Iterator<String> iterator = ringBuffer.iterator();
        while (iterator.hasNext()) {
            element = iterator.next();
        }

        return element;
    }

    @Test
    void testDequeue() {
        ringBuffer.enqueue("1");
        assertEquals("1", ringBuffer.dequeue());
        ringBuffer.enqueue("2");
        ringBuffer.enqueue("3");
        ringBuffer.dequeue();
        assertEquals("3", ringBuffer.dequeue());
    }

    @Test
    void testDequeueException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ringBuffer.dequeue();
        });
        assertEquals("Empty ring buffer.", exception.getMessage());
    }

    @Test
    void testPeek() {
        ringBuffer.enqueue("a");
        assertEquals("a", ringBuffer.peek());
        ringBuffer.enqueue("b");
        assertNotEquals("b", ringBuffer.peek());
    }

    @Test
    void testPeekException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ringBuffer.peek();
        });
        assertEquals("Empty ring buffer.", exception.getMessage());
    }

    @Test
    void TestNextIterator() {
        Iterator<String> referenceIterator = List.of("1", "2", "3").iterator();
        setUpBuffer(new String[] { "1", "2", "3" });
        Iterator<String> actualIterator = ringBuffer.iterator();

        for (int i = 0; i < ringBuffer.size(); i++) {
            assertEquals(referenceIterator.next(), actualIterator.next());
        }
    }

    @Test
    void testHasNextIterator() {
        setUpBuffer(new String[] { "1", "2", "3" });
        Iterator<String> actualIterator = ringBuffer.iterator();
        // check that hasNext is idempotent
        for (int i = 0; i < 10; i++) {
            assertTrue(actualIterator.hasNext());
        }
    }

    @Test
    void testRemoveIteratorException() {
        /*
         * method is not implemented, check for correct exception
         */
        setUpBuffer(new String[] { "1", "2", "3" });
        Iterator<String> actualIterator = ringBuffer.iterator();
        assertThrows(UnsupportedOperationException.class, () -> {
            actualIterator.remove();
        });
    }


    //region ------------ Helper methods ------------

    private static Stream<Arguments> provideDifferentStringCapacties() {
        //@formatter:off
        return Stream.of(
                Arguments.of((Object) new String[] { null }),
                Arguments.of((Object) new String[] { "1", "2" }),
                Arguments.of((Object) new String[] { "1", "2", "3" }),
                Arguments.of((Object) new String[] { "1", "2", "3", "4" }),
                Arguments.of((Object) new String[] { "" })
        );
    }

    private static Stream<Arguments> provideDifferentSizes() {
        return Stream.of(
                Arguments.of(1, (Object) new String[] { "1" }),
                Arguments.of(2, (Object) new String[] { "1", "2" }),
                Arguments.of(3, (Object) new String[] { "1", "2", "3" }),
                Arguments.of(3, (Object) new String[] { "1", "2", "3", "4" }),
                Arguments.of(3, (Object) new String[] { "1", "2", "3", "4", "5" }),
                Arguments.of(3, (Object) new String[] { "1", "2", "3", "4", "5", "6" }),
                Arguments.of(3, (Object) new String[] { "1", "2", "3", "4", "5", "6", "7" }),
                Arguments.of(3, (Object) new String[] { "1", "2", "3", "4", "5", "6", "7", "8" })
        );
    }
    //@formatter:on

    private void setUpBuffer(String... bufferElements) {
        for (String element : bufferElements) {
            ringBuffer.enqueue(element);
        }
    }

    //endregion
}