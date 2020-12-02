## Description

This bug occurs any time an exception that uses String.format() occurred. The properties key is wrong so the exception says Exception bundle cannot be found (default message) instead of what it is supposed to say

## Expected Behavior

We expect it to say the error message given 

## Current Behavior

It was printing the default exception message 

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. A key is passed into the exception printer method for printing with a string format
2. The key is formatted with that additional message before it can access the key in the exceptions so it prints the error as a default message since the key is wrong 

## Failure Logs

Nothing was printed exception for the default exception message that I describe above 

## Hypothesis for Fixing the Bug

It’s a really easy one line fix- I am just trying to get the key from the exception bundle in the wrong spot. I can test the output of an error message to test this