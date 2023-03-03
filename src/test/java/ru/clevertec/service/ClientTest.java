package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.models.Request;
import ru.clevertec.models.Response;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ClientTest {
    @Mock
    Server server;
    @Mock
    Request request;
    @Mock
    Response response;

    @Test
    void counterOfThreads_whenLatchIsPresent_everyThreadShouldIncrementCounter() throws InterruptedException {

        int numberOfThreads = 5;
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Client client = new Client(service, server, Arrays.asList(request),Arrays.asList(response));
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                System.out.println("log - " + client.getCounterOfThreads());
                client.setCounterOfThreads(client.getCounterOfThreads() + 1);
                latch.countDown();
            });
        }
        latch.await();
        assertEquals(numberOfThreads,client.getCounterOfThreads());

    }

}