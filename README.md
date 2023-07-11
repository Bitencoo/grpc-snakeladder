# grpc-snakeladder

This is the SnakeLadder game developed with gRPC and Java. 

The game consists on a 10x10 square numbered from 0-100, starting in position 1 and winning the game when someone gets to the last position 100.

Rules:
  - The Client will play first and then the server!
  - If you run the game on postman, you need to run each dice roll manually, if you run on your machine, it will start and play the game until the end automatically.
  - There are snakes and ladders on the game board, if you end up in a position which contains the begginning of a ladder, you will go up to higher positions. If you end up in a snake head, you will go down to lower positions.
  - The first one to get to the position number 100 wins the game!
