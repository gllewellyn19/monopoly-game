## Description

We found that for some of the spaces, the players were being told to pay $0 in rent when really the space wasn't purchased yet because we were calculating the correct rent, then reassinging it based on the number of real estates, even if there were no real estates on the property.

## Expected Behavior

We expect that an owned property should have a positive rent value unless mortgaged and an unowned property should give the option to buy it.

## Current Behavior

Before the fix, the players were being asked to pay $0 when they landed on an owned space.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Remove the if statements around the this.rent assignments in calculateRent() in OwnablePropertySpace

## Failure Logs

N/A

## Hypothesis for Fixing the Bug

The test will check that after the player buys the property the rent is initialized. The code fix is to move checkForMonopoly() below the two real estate statements in calculateRent() in OwnablePropertySpace