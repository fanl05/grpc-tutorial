package com.vip.grpc.supplier.warehouse.forecast;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Map;

/**
 * @author Ryland
 */
public class SWHForecastClient {

    private final SWHForecastServiceGrpc.SWHForecastServiceBlockingStub stub;

    public SWHForecastClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9999).usePlaintext().build();
        stub = SWHForecastServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        SWHForecastClient client = new SWHForecastClient();
        SWHForecastApis.SWHForecastResponse response = client.stub.forecast(null);
        response.getProbabilityOfSWHMap()
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue() > 0 ? 1 : -1)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

}
