package ru.clevertec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.models.Request;
import ru.clevertec.models.Response;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ConcurrentAppTest {
    @Mock
    Request request;
    @Mock
    Response response;

    @Test
    public void start_returnListThatContainAllValuesFromIncomingList() {
        List<Request> requestList = Arrays.asList(
                new Request(10),
                new Request(11),
                new Request(12),
                new Request(13)
        );
        List<Integer> valuesFromRequestList = requestList.stream()
                .map(request1 -> request.requestValue)
                .toList();
        List<Response> expectedList = ConcurrentApp.start(requestList);
        List<Integer> valuesFromExpectedList = expectedList.stream()
                .map(response1 -> response.responseValue)
                .toList();

        Assertions.assertTrue(valuesFromExpectedList.containsAll(valuesFromRequestList));

    }
}