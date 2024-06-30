# Tetris
Fully functional Tetris game in Java with username and score hash mapping, file I/O functionalities, and intuitive input mechanisms.

To play: Download folder and run main.

Home Screen: Enter username to the right of username label. Press play to play the game, press leaderboard to see the top 3 highest scores, and press instructions to see how to play. Press exit to exit.

Game: Press up arrow to rotate block, Press down arrow to move block down, Press right or left arrow to move block right or left, Press 0 to have robot play. If a row of the screen is filled, that row will clear, If a block crosses the top line, the game is over.

The big O time complexity is interesting with the rows moving down. There could be a lot of rows above the row you cleared so the code has to go through each and every box above and move it down. This means that the time complexity is linear or in big O terms, O(n).
