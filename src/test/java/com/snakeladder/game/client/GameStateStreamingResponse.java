package com.snakeladder.game.client;

import com.google.common.util.concurrent.Uninterruptibles;
import com.snakeladder.models.Die;
import com.snakeladder.models.GameState;
import com.snakeladder.models.Player;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GameStateStreamingResponse implements StreamObserver<GameState> {
    private CountDownLatch latch;
    private StreamObserver<Die> dieStreamObserver;
    public GameStateStreamingResponse(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        List<Player> players = gameState.getPalyersList();
        players.forEach(player -> System.out.println(player.getName() + ": " + player.getPosition()));
        System.out.println("-----------------------------");
        boolean isGameOver = players.stream().anyMatch(p -> p.getPosition() == 100);

        if(isGameOver){
            System.out.println("Game over");
            this.dieStreamObserver.onCompleted();
        } else {
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            this.roll();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("ACABOU MESMO DOIS");
        this.latch.countDown();
    }

    public void setDieStreamObserver(StreamObserver<Die> dieStreamObserver) {
        this.dieStreamObserver = dieStreamObserver;
    }

    public void roll(){
        int diceValue = ThreadLocalRandom.current().nextInt(1, 7);
        Die dice = Die.newBuilder().setValue(diceValue).build();
        this.dieStreamObserver.onNext(dice);
    }
}
