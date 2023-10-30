package ru.cvetkov.moving.objects.config;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.cvetkov.moving.objects.services.DeviceServiceImpl;
import ru.cvetkov.moving.objects.services.ExternalGrpcService;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    @Autowired
    private DeviceServiceImpl deviceService;

    @Value("${grpc.server.port}")
    private int grpcServerPort;

    @Bean
    public Server grpcServer() throws IOException {
        return NettyServerBuilder.forPort(grpcServerPort)
                .addService(new ExternalGrpcService(deviceService))
                .build()
                .start();
    }
}