package at.jku.swtesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

class RingBufferCoverageTest {

    private RingBuffer<String> ringBuffer;

    @BeforeEach
    void setUp() {
        ringBuffer = new RingBuffer<>(3);
    }

    @Test
    void testNextIterator() {
        setUpBuffer(new String[] { "1", "2", "3" });
        Iterator<String> actualIterator = ringBuffer.iterator();

        assertEquals("1", actualIterator.next());
        assertEquals("2", actualIterator.next());
        assertEquals("3", actualIterator.next());

        assertThrows(NoSuchElementException.class, () -> {
            actualIterator.next();
        });
    }


    //region ------------ Helper methods ------------

    private void setUpBuffer(String... bufferElements) {
        for (String element : bufferElements) {
            ringBuffer.enqueue(element);
        }
    }

    //endregion
}