package me.dchuiko.spa.persistence;

import me.dchuiko.spa.model.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class EntityTableTest {
    @Test
    public void shouldConcurrentlyPut() throws InterruptedException {
        EntityTable<User> table = new EntityTable<>();

        CountDownLatch latch = new CountDownLatch(1);

        Function<Integer, User> create = i -> {
            final UUID key = UUID.randomUUID();
            final User value = new User(key, "" + i);
            table.put(key, value);
            return value;
        };

        List<Thread> threadList =
                Arrays.asList(new TestThread(1000, 0, latch, create),
                              new TestThread(1000, 1000, latch, create),
                              new TestThread(1000, 2000, latch, create),
                              new TestThread(1000, 3000, latch, create));

        threadList.forEach(Thread::start);
        latch.countDown();
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        int count = 4000;
        assertEquals(count, table.size());

        for (int i = 0; i < count; i++) {
            int finalI = i;
            assertEquals(1, table.find(user -> user.name().equals("" + finalI)).size());
        }
    }

    private static class TestThread extends Thread {
        private final int iterationNumber;
        private final int startNumber;
        private final CountDownLatch latch;
        private final Function<Integer, User> action;

        private TestThread(int iterationNumber, int startNumber, CountDownLatch latch, Function<Integer, User> action) {
            this.iterationNumber = iterationNumber;
            this.startNumber = startNumber;
            this.action = action;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = startNumber; i < startNumber + iterationNumber; i++) {
                action.apply(i);
            }
        }
    }

}
