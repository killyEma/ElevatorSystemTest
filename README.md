Elevator System: Basic functionality about elevator system

Logic elevators: 
save "call the elevator" by the floors(button in floor).
Elevator Stops due to being overweight. 
Add the target elevator floor.
There are two types of elevator with and without keycard security, this keycard is for access to specific floors.

Project Status: In Progress. 

Task to enhance: 
*Add unite test and Isolation test. 
*Add propertyconfig.xml (these files contain parameters like total of floors, keycard for floors, weight limit, timers second, disable local example "call elevator"). 
*Refactor smell code. 
*Create Log files. 
*Template pattern for the classes ElevatorCardSystem and Elevator system. 
*abstract layers in order to access external information: 
  -getNexFloorTarget When the floor set the elevator arrives. 
  -When the floor set the elevator door. 
  -Start and end Alarm. 
  -Save "Call the elevator". 
  -Input Elevator Weight.
How to test it locally:
Run project "Ingrese los pisos antes que se cierre la puerta'' then enter a number between -1 and 50 including 0. (range in propertyconfig.xml) 
Enter 70 when the input floor is over "El peso total es:'' you have to enter a valid wight (weight in propertyconfig.xml)
When you call the elevator and it has arrivate you have seconds to enter a new destination floor. 
If you don't enter a new destination floor and there are more "call elevators" for other floors then the elevator starts to get to the floor.


Technical requirements: java 8 or higher. junit 4.12 or higher.

Bugs: there is a bug in Elevator.getNexFloorTarget() due to threads. But not frequently. a try catch should be a quick solution

FAQs:

