package ru.clevertec.models;

public class Request {

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

