package com.vip.grpc.supplier.warehouse.forecast;

import com.google.common.collect.Maps;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Map;

/**
 * @author Ryland
 */
public class SWHForecastServer2 extends SWHForecastServiceGrpc.SWHForecastServiceImplBase {

    public static void main(String[] args) {
        try {
            ServerBuilder.forPort(9999).intercept(new HeaderServerInterceptor()).addService(new SWHForecastServer2()).build().start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Server Started at Port 9999");
        }

        while (true) {
        }
    }

    @Override
    public void forecast(SWHForecastApis.SWHForecastRequest request, StreamObserver<SWHForecastApis.SWHForecastResponse> responseObserver) {

        Map<String, Double> probabilityOfSWH = Maps.newHashMap();
        probabilityOfSWH.put("WH1", 0.8);
        probabilityOfSWH.put("WH2", 0.7);
        probabilityOfSWH.put("WH3", 0.6);
        probabilityOfSWH.put("WH4", 0.5);
        probabilityOfSWH.put("WH5", 0.5);

        responseObserver.onNext(SWHForecastApis.SWHForecastResponse.newBuilder().putAllProbabilityOfSWH(probabilityOfSWH).build());
        responseObserver.onCompleted();

    }
}
