## Description

This bug occurs when a player updates their balance. The money display stops showing the players’ names once their money balance is updated. 

## Expected Behavior

We expect the players name to always remain with the money balance pictures on the side of monopoly 

## Current Behavior

Currently the name disappears after the user updates their balance 

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. The users balance is updated 
2. The text showing the users balance resets but doesn’t continue to show the player’s name

## Failure Logs

Nothing was printed fro this bug

## Hypothesis for Fixing the Bug

It’s a really easy one line fix- I just accidentally lost the players name but now I manipulate the existing text in the Text so that I still can display the name 
