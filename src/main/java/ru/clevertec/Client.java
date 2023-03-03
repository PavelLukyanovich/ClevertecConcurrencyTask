package ru.clevertec;

import ru.clevertec.Server.Response;

import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable {

    public static List<Request> requestList;
    public static List<Response> responseList;

    public Client() {

        responseList = new ArrayList<>();
        requestList = List.of(
                new Request(11),
                new Request(22),
                new Request(33),
                new Request(44),
                new Request(55),
                new Request(66),
                new Request(77),
                new Request(88),
                new Request(99));
    }

    public void sendRequest(Request request) {

        Server.incomingRequestList.add(request);

    }

    @Override
    public void run() {
        System.out.println("Send request start");
        for (Request req : requestList
        ) {
            sendRequest(req);
        }
        System.out.println("Send request stop");
    }

    static class Request {

        public int requestValue;

        public Request(int requestValue) {
            this.requestValue = requestValue;
        }

        @Override
        public String toString() {
            return "Request{" +
                    "requestValue=" + requestValue +
                    '}';
        }
    }
}
