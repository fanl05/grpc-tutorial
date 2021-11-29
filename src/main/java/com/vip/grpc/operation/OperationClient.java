package com.vip.grpc.operation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Ryland
 */
public class OperationClient {

    private final OperationServiceGrpc.OperationServiceBlockingStub stub;

    public OperationClient() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 9999).usePlaintext().build();
        stub = OperationServiceGrpc.newBlockingStub(managedChannel);
    }

    public static void main(String[] args) {
        OperationClient client = new OperationClient();
        int addRes = client.stub.calculate(Operation.OperationRequest.newBuilder().setA(10).setB(5).setOperator(Operation.Operator.ADD).build()).getRes();
        int subtractRes = client.stub.calculate(Operation.OperationRequest.newBuilder().setA(10).setB(5).setOperator(Operation.Operator.SUBTRACT).build()).getRes();
        int multiplyRes = client.stub.calculate(Operation.OperationRequest.newBuilder().setA(10).setB(5).setOperator(Operation.Operator.MULTIPLY).build()).getRes();
        int divideRes = client.stub.calculate(Operation.OperationRequest.newBuilder().setA(10).setB(5).setOperator(Operation.Operator.DIVIDE).build()).getRes();
        System.out.println("addRes: " + addRes);
        System.out.println("subtractRes: " + subtractRes);
        System.out.println("multiplyRes: " + multiplyRes);
        System.out.println("divideRes: " + divideRes);
    }

}
