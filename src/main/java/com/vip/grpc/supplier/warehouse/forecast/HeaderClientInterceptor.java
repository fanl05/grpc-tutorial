package com.vip.grpc.supplier.warehouse.forecast;

import io.grpc.*;

/**
 * @author Ryland
 */
public class HeaderClientInterceptor implements ClientInterceptor {

    static final Metadata.Key<String> GRPC_SERVICE_DOMAIN = Metadata.Key
            .of("grpc-service-domain", Metadata.ASCII_STRING_MARSHALLER);

    static final Metadata.Key<String> GRPC_SERVICE_VERSION = Metadata.Key
            .of("grpc-service-version", Metadata.ASCII_STRING_MARSHALLER);

    static final Metadata.Key<String> CALLER_ID = Metadata.Key
            .of("caller-id", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor,
                                                               CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(GRPC_SERVICE_DOMAIN, "com.vip.grpc");
                headers.put(GRPC_SERVICE_VERSION, "1.0.0");
                headers.put(CALLER_ID, "001");
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        System.out.println("Headers from the Server: " + headers.toString());
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}
