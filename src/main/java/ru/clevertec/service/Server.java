package ru.clevertec.service;

import ru.clevertec.models.Request;
import ru.clevertec.models.Response;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Server {

    private final ExecutorService executor;

    public Server(ExecutorService executor) {
        this.executor = executor;
    }

    public Response handleRequest(Request request) {

        System.out.println("log1 - " + request.requestValue);
        Future<Response> submit = executor.submit(new ServerCaller(request));
        try {
            Response response = submit.get();
            System.out.println("log4 - " + request.requestValue);

            return response;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }


    static class ServerCaller implements Callable<Response> {
        private final Request request;

        ServerCaller(Request request) {
            this.request = request;
        }

        private void sleep() {
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Response call() throws Exception {
            System.out.println("log2 - " + request.requestValue);

            Response response = new Response(100 - request.requestValue);
            sleep();
            System.out.println("log3 - " + request.requestValue);

            return response;
        }
    }
}
