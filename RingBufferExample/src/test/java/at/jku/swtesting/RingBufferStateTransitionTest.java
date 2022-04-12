package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RingBufferStateTransitionTest {

    @Test
    void testSequenceOne() {
        //  new Ringbuffer(0) - final
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RingBuffer<>(0);
        });
        assertEquals("Initial capacity is less than one", exception.getMessage());
    }

    @Test
    void testSequenceTwo() {
        //new Ringbuffer(3) - empty - peek() -final
        RingBuffer<String> ringBuffer = new RingBuffer<>(3);
        assertTrue(ringBuffer.isEmpty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ringBuffer.peek();
        });
        assertEquals("Empty ring buffer.", exception.getMessage());
    }

    @Test
    void testSequenceThree() {
        //new Ringbuffer(3) - empty - dequeue() - final
        RingBuffer<String> ringBuffer = new RingBuffer<>(3);
        assertTrue(ringBuffer.isEmpty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ringBuffer.dequeue();
        });
        assertEquals("Empty ring buffer.", exception.getMessage());
    }

    @Test
    void testSequenceFour() {
        // new Ringbuffer(3) - empty
        RingBuffer<String> ringBuffer = new RingBuffer<>(3);
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    void testSequenceFive() {
        // new Ringbuffer(3) - empty - enqueue(a) -  filled - dequeue(a) - empty
        RingBuffer<String> ringBuffer = new RingBuffer<>(3);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.dequeue();
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    void testSequenceSix() {
        // new Ringbuffer(3) - empty - enqueue(a) - filled - peek() - filled
        RingBuffer<String> ringBuffer = new RingBuffer<>(3);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        assertEquals("a", ringBuffer.peek());
        assertEquals(1, ringBuffer.size());
    }

    @Test
    void testSequenceSeven() {
        // new Ringbuffer(3) - empty - enqueue(a) - filled - enqueue(b) - filled
        RingBuffer<String> ringBuffer = new RingBuffer<>(3);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.enqueue("b");
        assertEquals(2, ringBuffer.size());
    }

    @Test
    void testSequenceEight() {
        // new Ringbuffer(2) - empty - enqueue(a) - filled - enqueue(b) - full
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.enqueue("b");
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testSequenceNine() {
        //  new Ringbuffer(3) - empty - enqueue(a) - filled - enqueue(b) - filled - dequeue() - filled
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.enqueue("b");
        assertEquals(2, ringBuffer.size());
        ringBuffer.dequeue();
        assertEquals(1, ringBuffer.size());
    }

    @Test
    void testSequenceTen() {
        //  new Ringbuffer(2) - empty - enqueue(a) - filled - enqueue(b) - full - peek() - full
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.enqueue("b");
        assertTrue(ringBuffer.isFull());
        assertEquals("a", ringBuffer.peek());
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testSequenceEleven() {
        // new Ringbuffer(2) - empty - enqueue(a) - filled - enqueue(b) - full - enqueue(c) - full
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.enqueue("b");
        assertTrue(ringBuffer.isFull());
        ringBuffer.enqueue("c");
        assertTrue(ringBuffer.isFull());
    }

    @Test
    void testSequenceTwelve() {
        // new Ringbuffer(2) - empty - enqueue(a) - filled - enqueue(b) - full - dequeue(a) - filled
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.enqueue("a");
        assertEquals(1, ringBuffer.size());
        ringBuffer.enqueue("b");
        assertEquals(2, ringBuffer.size());
        ringBuffer.dequeue();
        assertEquals(1, ringBuffer.size());
    }

}
