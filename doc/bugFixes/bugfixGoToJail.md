## Description

Summary of the feature's bug (without describing implementation details)

When the player lands on the Go To Jail space, the correct messages pop up, but the graphic of the player piece does not get moved to the Jail space.

## Expected Behavior

Describe the behavior you expect

When a piece needs to be moved after the player has landed on a space, turnableView is supposed to be called in order to call the appropriate front-end methods 
to move the user piece graphic.

## Current Behavior

Describe the actual behavior

The user piece graphic is remaining on the Go To Jail space, even though the rest of the process is functioning fine.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Start the program
 1. Roll dice or call methods in test in order to send a player to the Go To Jail space (int ID = 30 in classic monopoly)

## Failure Logs

Include any relevant print statements or stack traces.

## Hypothesis for Fixing the Bug

We went through several different iterations of methods that control the player turns and communication between the back-end and front-end. It is very likely that the 
method call to the front-end, which is supposed to be made through the TurnableView interface, is being made in the wrong method or even the wrong class.
