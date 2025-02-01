# Tron Battle AI

## Overview

This is a program for [Coding Games Tron Battle](https://www.codingame.com/multiplayer/bot-programming/tron-battle). It uses the Minimax algorithm (depth 4) with a flood fill heuristic to make smart moves and avoid getting trapped.

## How It Works

1. **Minimax Algorithm**: Looks ahead up to 4 moves to find the best option.
2. **Flood Fill Heuristic**: Estimates how much space is available to decide the best path.
3. **Move Selection**: Chooses the move that maximizes space while limiting the opponent.

## Future Ideas

- Add Alpha-Beta Pruning to speed up Minimax
- Improve the heuristic to consider more factors
- Make it adaptable based on the time available
