package ru.clevertec;

import ru.clevertec.Server.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentApp {

    public static void main(String[] args) {
        System.out.println("Main start");

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(new Client());
        Future<List<Response>> sub = executor.submit(new Server());

        try {
            sub.get().stream()
                    .forEach(System.out::println);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();

    }
}
