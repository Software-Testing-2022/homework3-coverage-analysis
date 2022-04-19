package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RingBufferMutationTest {

    @Test
    void testRingBufferConstructorWithBoundaryCapacity() {
        RingBuffer<String> ringBuffer = new RingBuffer<>(1);
        assertTrue(ringBuffer.isEmpty());
    }
}
