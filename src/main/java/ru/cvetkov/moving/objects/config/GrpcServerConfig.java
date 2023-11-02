package ru.cvetkov.moving.objects.config;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.cvetkov.moving.objects.services.DeviceServiceImpl;
import ru.cvetkov.moving.objects.services.ExternalGrpcService;
import ru.cvetkov.moving.objects.services.GeopositionServiceImpl;

import java.io.IOException;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class GrpcServerConfig {

    @Autowired
    private DeviceServiceImpl deviceService;

    @Autowired
    private GeopositionServiceImpl geopositionService;
    

    @Value("${grpc.server.port}")
    private int grpcServerPort;

    @Bean
    public Server grpcServer() throws IOException {
        return NettyServerBuilder.forPort(grpcServerPort)
                .addService(new ExternalGrpcService(deviceService, geopositionService))
                .build()
                .start();
    }
}