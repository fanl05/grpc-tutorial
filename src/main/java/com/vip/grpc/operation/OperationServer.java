package com.vip.grpc.operation;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * @author Ryland
 */
public class OperationServer extends OperationServiceGrpc.OperationServiceImplBase {

    public static void main(String[] args) {
        try {
            ServerBuilder.forPort(9999).addService(new OperationServer()).build().start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("gRPC Server started at Port 9999");
        }
        while (true) {
        }
    }

    @Override
    public void calculate(Operation.OperationRequest request, StreamObserver<Operation.OperationResponse> responseObserver) {
        int a = request.getA();
        int b = request.getB();
        Operation.Operator operator = request.getOperator();
        int res;
        switch (operator) {
            case ADD:
                res = a + b;
                break;
            case SUBTRACT:
                res = a - b;
                break;
            case MULTIPLY:
                res = a * b;
                break;
            case DIVIDE:
                res = a / b;
                break;
            default:
                res = -1;
        }
        responseObserver.onNext(Operation.OperationResponse.newBuilder().setRes(res).build());
        responseObserver.onCompleted();

    }
}
