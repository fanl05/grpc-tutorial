package com.vip.grpc.supplier.warehouse.forecast;

import com.google.common.base.Strings;
import io.grpc.*;

import java.util.Objects;

/**
 * @author Ryland
 */
public class HeaderServerInterceptor implements ServerInterceptor {

    static final Metadata.Key<String> GRPC_SERVICE_DOMAIN = Metadata.Key
            .of("grpc-service-domain", Metadata.ASCII_STRING_MARSHALLER);

    static final Metadata.Key<String> GRPC_SERVICE_VERSION = Metadata.Key
            .of("grpc-service-version", Metadata.ASCII_STRING_MARSHALLER);

    static final Metadata.Key<String> CALLER_ID = Metadata.Key
            .of("caller-id", Metadata.ASCII_STRING_MARSHALLER);

    static final Metadata.Key<String> RETURN_CODE = Metadata.Key
            .of("return-code", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("Headers from the Client: " + headers.toString());
        String domain = headers.get(GRPC_SERVICE_DOMAIN);
        String version = headers.get(GRPC_SERVICE_VERSION);
        String callerId = headers.get(CALLER_ID);
        if (!Objects.equals(domain, "com.vip.grpc") || Strings.isNullOrEmpty(version) || Strings.isNullOrEmpty(callerId)) {
            System.err.println("Validate Fail...");
            call.close(Status.DATA_LOSS, headers);
        }

        ServerCall<ReqT, RespT> serverCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata headers) {
                headers.put(RETURN_CODE, "200");
                super.sendHeaders(headers);
            }
        };
        return next.startCall(serverCall, headers);

    }

}
