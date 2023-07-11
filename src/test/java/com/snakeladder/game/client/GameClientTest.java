package com.snakeladder.game.client;

import com.snakeladder.models.Die;
import com.snakeladder.models.GameServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameClientTest {
    private GameServiceGrpc.GameServiceStub gameServiceStub;

    @BeforeAll
    public void setUp(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        this.gameServiceStub = GameServiceGrpc.newStub(channel);
    }

    @Test
    public void gameClientTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        GameStateStreamingResponse gameStateStreamingResponse = new GameStateStreamingResponse(latch);
        StreamObserver<Die> dieStreamObserver = this.gameServiceStub.roll(gameStateStreamingResponse);
        gameStateStreamingResponse.setDieStreamObserver(dieStreamObserver);
        gameStateStreamingResponse.roll();
        latch.await();
    }
}
