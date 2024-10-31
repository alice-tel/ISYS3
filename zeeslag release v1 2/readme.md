# New Game Server

Game list
* Tic-tac-toe
* Reversi [1] 
* Connect4
* Battleship

HTTP server (API & Frontend-app): port 8081

Game server: port 7789

Vue development server: port 8080 (development only)

## Running the server
Download the server and run "java -jar zeeslag-server-alpha.jar"
This will start the Game Server and the HTTP server. 


**running a service on a different port:** <br/>
To change the port of the webserver and/or gameserver you should change them in `server.properties`. Read the official (dutch) manual for more information on configuration.

**Admin Account:** <br/>
To start a tournament you need an admin account on the webfrontend. Everything else can be done without an account. <br/>
The default password is: `HanzeGameserverAdmin`

**Changing the password for the admin account:** <br/>
You can set the password for this account by starting the server with a special parameter.
`java -jar newgameserver.jar --set-password mynewpassword123!`
After this you can start the server the normal way and login using your new password.


The HTTP server will handle API requests and will serve the frontend-app. 

### Api documentation
The Frontend uses a http and websocket api to get the data from the backend.
The websocket API is documented in websocket_protocol.md
