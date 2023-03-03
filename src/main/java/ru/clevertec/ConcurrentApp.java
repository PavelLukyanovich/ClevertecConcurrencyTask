package ru.clevertec;

import ru.clevertec.models.Request;
import ru.clevertec.models.Response;
import ru.clevertec.service.Client;
import ru.clevertec.service.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentApp {
    private static final ExecutorService clientExecutor = Executors.newFixedThreadPool(9);
    private static final ExecutorService serverExecutor = Executors.newFixedThreadPool(9);

    public static void main(String[] args) {

        List<Request> requestList = Arrays.asList(
                new Request(11),
                new Request(22),
                new Request(33),
                new Request(44),
                new Request(55),
                new Request(66),
                new Request(77),
                new Request(88),
                new Request(99));

        List<Response> responses = start(requestList);
        responses.forEach(System.out::println);

    }

    public static List<Response> start(List<Request> requestList) {
        Server server = new Server(serverExecutor);
        List<Response> responseList = new ArrayList<>();
        Client client = new Client(clientExecutor, server, requestList, responseList);

        client.run();
        while (getSize(responseList) != requestList.size()) {
        }
        clientExecutor.shutdown();
        serverExecutor.shutdown();
        return responseList;
    }

    private static synchronized int getSize(List<Response> responseList) {
        return responseList.size();
    }
}
