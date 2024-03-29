package cn.denghanxi.grpc_client;

import cn.denghanxi.grpc.helloworld.GreeterGrpc;
import cn.denghanxi.grpc.helloworld.HelloReply;
import cn.denghanxi.grpc.helloworld.HelloRequest;
import cn.denghanxi.grpc.proto.time.TimerServiceGrpc;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Created by dhx on 2022/4/20.
 */
public class AppClient {

    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final TimerServiceGrpc.TimerServiceBlockingStub timerServiceBlockingStub;

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    public AppClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = GreeterGrpc.newBlockingStub(channel);
        timerServiceBlockingStub = TimerServiceGrpc.newBlockingStub(channel);
    }


    /**
     * Say hello to server.
     */
    public void greet(String name) {
        System.out.println("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return;
        }
        System.out.println("Greeting: " + response.getMessage());
    }

    public LocalDateTime getTime() {
        Timestamp response;
        response = timerServiceBlockingStub.whatTimeIsIt(Empty.newBuilder().build());
        LocalDateTime time = LocalDateTime.ofEpochSecond(response.getSeconds(), response.getNanos(), ZoneOffset.UTC);
        System.out.println("seconds:" + response.getSeconds() + " nano: " + response.getNanos());
        return time;
    }

    public static void main(String[] args) throws InterruptedException {
        String user = "dhx";
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            AppClient client = new AppClient(channel);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(2000);
                LocalDateTime localDateTime = client.getTime();
                System.out.println(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
