package com.snakeladder.server;

import com.snakeladder.models.Die;
import com.snakeladder.models.GameState;
import com.snakeladder.models.Player;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DieStreamingRequest implements StreamObserver<Die> {
    private Player client;
    private Player server;
    private StreamObserver<GameState> gameStateStreamObserver;

    public DieStreamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
        this.client = client;
        this.server = server;
        this.gameStateStreamObserver = gameStateStreamObserver;
    }

    @Override
    public void onNext(Die die) {
        this.client = getNewPlayerPosition(this.client, die.getValue());
        if(this.client.getPosition() != 100){
            this.server = getNewPlayerPosition(this.server, ThreadLocalRandom.current().nextInt(1, 7));
        }
        this.gameStateStreamObserver.onNext(this.getGameState());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        System.out.println("Acabou mesmo");
        this.gameStateStreamObserver.onCompleted();
    }

    private Player getNewPlayerPosition(Player player, int dieValue) {
        int position = player.getPosition() + dieValue;
        position = SnakeLaddersMAP.getPosition(position);
        if(position <= 100){
            player = player
                    .toBuilder()
                    .setPosition(position)
                    .build();
        }
        return player;
    }

    private GameState getGameState(){
        return GameState.newBuilder().addPalyers(this.client).addPalyers(this.server).build();
    }
}
