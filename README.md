# Client-Server-Network

The purpose of this project was to create a client and server application for a simple online system that would allow users to sign-up to one of a number of lists. The number of lists and the max number of participants in each list can be specified when the server is launched. The client should then be able to request infromation about the current totals, the members in a list and also request to be added to a specific list.

The requirements for the server application are as follows:
- Accept the number of lists, and the maximum number of members per list, as command line
arguments when launched. All lists should be empty initially.
- Run continuously.
- Use an Executor to manage a fixed thread-pool with 25 connections.
- Store the members of each list, where each member is represented by a single String.
- Return information about the lists, and attempt to add new members to a list, when requested by a client (see below).
- Create the file log.txt on the server directory and log every client request, with one line
per request, in the following format: date|time|client IP address|request. Nothing else should be output to the log file

The requirements for the client application are as follows:
- Accept one of the following commands as command line arguments, and performs the stated
task in conjunction with the server:
- totals: Displays the number of lists, the maximum per list, and the current number
of members of each list.
- list n: Display every member in the given list, one member per line.
- join n name: Attempt to add a member name to list n, and return to the user Success
if this was successful, or Failed if not.
- For the join command, full names with spaces should be enclosed in quotes so that they
are parsed as a single String; see examples below.
- Quits after completing any one command.
