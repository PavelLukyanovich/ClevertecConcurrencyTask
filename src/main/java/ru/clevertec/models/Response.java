package ru.clevertec.models;

public class Response {

    public int responseValue;

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
