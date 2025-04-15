Natalie Wilkins and Neeraja Vasa
27 November 2023


Design decision: using the provided NetworkController instead of using our own, using a different background picture


3 main jobs of the client:
Allow the user to connect to a server at a certain address, and provide the player's name.
Draw the state of the world, as described by the server.
Send control commands ot hte server. These commands represent player inputs.

Implementing separation of concerns: sticking to MVC

GameController handles processing the info received from the server and updates the model, informs the view when to redraw

GameController has project reference to NetworkController (which lives in a folder called Libraries inside GameController)
View points to Model and Controller
Model points to Vector2D

GameController has logic for parsing data from the server, updates the model. 

View will draw objects in the game as well as names and scores, draw GUi cnotrols, such as text boxes and buttons, and register basic event handlers for user inputs and controller events. 

Here is what the app can do:
1. Allow a player to declare a name and choose a server by IP address or host name.
2. Provide a way to display connection errors and to retry connecting. 
3. Draw the scene, including the snakes, powerups, etc. The first 8 players (at least)
	should be drawn with a unique color or graphic that identifies the player. Beyond 8 players, graphics
	or colors may be reused. The name and score of each player will be displayed by their snake.
4. GUI can keep up with server and draw frames as fast as the server sends new information about the world (30 frames per second)
	with at least 3 clients.
5. Followed the defined communication sequence and protocol
6. Register keyboard handlers that recognize basic user inputs and invoke appropriate 
	controller methods to process the inputs. 


*** POTENTIAL ISSUE WITH THE GIVEN DLL?
When we try to connect to an invalid name, our app gives a popup that asks you to try again. Except if we put in localhost and have no server running for it to connect to....
I'm not sure why this would be. I have no idea how to handle this, and it seems like it could be a problem in the networking dll?
This has been one of the biggest issues in our process of doing this assignment. 





