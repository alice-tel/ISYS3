
# Websocket Protocol

Websockets gebruiken het STOMP protocol om te communiceren.

De classes/objecten waarna verwezen wordt kunnen gevonden worden in de Java source code. Deze objecten worden omgezet naar een JSON object voordat deze over de websocket gestuurd worden.

## Topics
De frontend kan op verschillende topics subscriben om realtime updates over dat topic te ontvangen.

## Chat:
### `/topic/chat`
Stuurt een `MessageCommand.ChatMessage` object wanneer een client een bericht heeft verstuurd.


## Players:
### `/topic/onplayerjoined`
Stuurt een `Player` object zodra een nieuwe speler/gameclient heeft ingelogd op de gameserver.

### `/topic/onplayerleft`
Stuurt een `Player` object zodra deze speler/gameclient de gameserver heeft verlaten.


## Games:
### `/topic/onnewgame`
Stuurt een `Game` object zodra er een nieuw potje is begonnen op de gameserver.

`Game.gameId` kan gebruikt worden om te subscriben op topics waarin `{gameId}` staat.

### `/topic/ongameended`
Stuurt een het `gameId` (`int`) van het potje dat is afgelopen.

### `/topic/gameupdate/`**`{gameId}`**
Stuurt een `OnTileChangeData` object zodra er iets in het desbetreffende potje is veranderd.

### `/topic/gameresult/`**`{gameId}`**
Stuurt een `OnResultData` object als het desbetreffende potje een uitslag heeft.

## Tournaments:
### `/topic/onnewtournament`
Stuurt een `Tournament` object wanneer er een tournament is gestart.

### `/topic/ontournamentfinished`
Stuurt een `Tournament.OnFinished` object wanneer er een tournament is afgelopen.

### `/topic/onmatchupdate`
Stuurt een `Match` object wanneer er een verandering is in een tournament-potje.
