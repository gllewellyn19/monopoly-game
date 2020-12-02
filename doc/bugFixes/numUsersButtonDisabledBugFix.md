## Description

This bug occurs when the user selects a number of players on the splash screen. The down down is enabled before the user has selected a type of monopoly which is right but the drop down disables as soon as they click it which is wrong

## Expected Behavior

The number of users drop down should stay enabled after the user clicks it

## Current Behavior

The number of users drop down should disables after the user clicks it

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. The user clicks the number of users drop down to select a number of users for their monopoly game
2. This drop down disables so that the user cannot click it again which is wrong 

## Failure Logs

Nothing is printed during this bug

## Hypothesis for Fixing the Bug

This bug is a fairly easy fix- I only should disabled the number of users button when I am creating the splash screen. Right now it is getting disabled when I refresh the splash screen which is wrong 
