package ru.clevertec.service;

import ru.clevertec.models.Request;
import ru.clevertec.models.Response;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable {
    public final ExecutorService clientExecutor;
    public final List<Response> responseList;
    private final Lock locker = new ReentrantLock();
    private final Server server;
    private final List<Request> requestList;

    private int counterOfThreads;


    public Client(ExecutorService clientExecutor, Server server, List<Request> requestList, List<Response> responseList) {
        this.clientExecutor = clientExecutor;
        this.server = server;
        this.requestList = requestList;
        this.responseList = responseList;
        this.counterOfThreads = 0;


    }

    @Override
    public void run() {
        requestList.forEach(request -> {
            clientExecutor.submit(() -> {
                counterOfThreads++;
                System.out.println("Start Thread name " + Thread.currentThread().getName());
                Response response = server.handleRequest(request);
                addToResponseList(response);
                System.out.println("End Thread name " + Thread.currentThread().getName());
            });
        });
    }

    public void addToResponseList(Response response) {

        locker.lock();
        responseList.add(response);
        locker.unlock();
    }

    public int getCounterOfThreads() {
        return counterOfThreads;
    }

    public void setCounterOfThreads(int counterOfThreads) {
        this.counterOfThreads = counterOfThreads;
    }
}
