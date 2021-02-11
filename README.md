# Snake_Game_JavaFX
Simple Snake Game made using JavaFX.  

Under the hood, the player controls a transparent "paintbrush" that paints green squares to the game board at every tick of an animation timer to represent the Snake's body.  

Each body segment is added to a list, and once the list contains a number of body segments corresponding to a maximum body length variable. 

The oldest body segment to be painted is moved to a "garbage collector" list and removed from the screen, and thereafter the "garbage collector" list.  

With each apple collected by the player, the maximum length of the Snake's body segment list is increased along with its movement speed.  

Functionality: 
•Basic Game functions 
•High Score 
•Reset-ability
