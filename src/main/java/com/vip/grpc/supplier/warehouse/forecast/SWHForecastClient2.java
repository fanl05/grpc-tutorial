package com.vip.grpc.supplier.warehouse.forecast;

import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.PostConstruct;

/**
 * @author Ryland
 */
public class SWHForecastClient2 {

    private final SWHForecastServiceGrpc.SWHForecastServiceBlockingStub stub;

    public SWHForecastClient2(){
        ManagedChannel originChannel = ManagedChannelBuilder
                .forAddress("127.0.0.1", 9999)
                .usePlaintext()
                .build();
        HeaderClientInterceptor interceptor = new HeaderClientInterceptor();
        Channel channel = ClientInterceptors.intercept(originChannel, interceptor);
        stub = SWHForecastServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        SWHForecastClient2 client = new SWHForecastClient2();
        SWHForecastApis.SWHForecastResponse response = client.stub.forecast(null);
        response.getProbabilityOfSWHMap()
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue() > 0 ? 1 : -1)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

}
