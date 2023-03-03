package ru.clevertec;

import ru.clevertec.Client.Request;
import ru.clevertec.Server.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server implements Callable<List<Response>> {

    Lock locker = new ReentrantLock();
    Condition condition = locker.newCondition();

    public static List<Request> incomingRequestList = new ArrayList<>();
    private final List<Response> outgoingResponse = new ArrayList<>();

    private void prepareResponse(List<Request> list) {
        System.out.println("Prepare response start");

        for (Request req : list
        ) {
            long delay = (long) (Math.random() * 2000);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread will sleep " + delay + "ms");
            outgoingResponse.add(new Response(100 - req.requestValue));
        }
        System.out.println("Prepare response stop");

    }

    public Response sendResponse(Response response) {
        System.out.println("Response is sending");

        Client.responseList.add(response);

        return response;

    }

    @Override
    public List<Response> call() {

        prepareResponse(incomingRequestList);
        {
            locker.lock();
            try {
                while (outgoingResponse.size() < 3)
                    condition.await();
                System.out.println("List of request is not full, wait.");

                System.out.println("Send response start");
                for (Response res : outgoingResponse
                ) {
                    sendResponse(res);
                }
            } catch (InterruptedException ex) {
            } finally {
                locker.unlock();
            }
        }
        System.out.println("Send response stop");
        return Client.responseList;
    }

    static class Response {

        int responseValue;

        public Response(int responseValue) {
            this.responseValue = responseValue;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "responseValue=" + responseValue +
                    '}';
        }
    }
}
