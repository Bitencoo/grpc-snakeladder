package com.snakeladder.server;

import com.snakeladder.models.Die;
import com.snakeladder.models.GameServiceGrpc;
import com.snakeladder.models.GameState;
import com.snakeladder.models.Player;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        Player client = Player
                .newBuilder()
                .setName("Client")
                .setPosition(0)
                .build();
        Player server = Player
                .newBuilder()
                .setName("Server")
                .setPosition(0)
                .build();
        return new DieStreamingRequest(client, server, responseObserver);
    }
}
