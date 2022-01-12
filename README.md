Elevator System: Basic functionality about elevator sysmtem

Logic elevators: 
save "call the elevator" by the floors(button in floor).  
Elevator Stops due to being overweight.
Add target elevator floor.

There are thow types of elevator with and without keycard security, this keycard is for access to specific floors.


Proyet Status: In Progress.
Task to enhance:
*Add unite test.
*Add propertyconfig.xml (this files contain parameters like total of floors, keycard for floors, weight limit, timers second, disable local example "call elevator").
*Refactor smell code.
*Create Log files.
*Template pattern for the classes ElevatorCardSystem and Elevator system.
*abstract layers in order to access external informations: getNexFloorTarget
  When the floor set the elevator arrived.
  When the floor set the elevator door.
  Start and end Alarm.
  Save "Call the elevator".
  Input Elevator Weight.


How to test it locally:  
Run project
"Ingrese los pisos antes que se cierre la puerta"
then enter a number between -1 and 50 including 0. (range in propertyconfig.xml) 
Enter 70 when the input floor is over
"El peso total es:"
you have to enter a valid wight (weight in propertyconfig.xml)

When you call the elecator and it has arrivate you have seconds to enter a new destination floor. 
If you don't entered a new destination floor and there are more "call elevator" for others floors then the elevator start to get to the floor.



Tecnical requirements: java 8 or higher. junit 4.12 or higher.


Bugs: there are a bug in Elevator.getNexFloorTarget() due to threads. But is not fruecuently. a try cath should be a quick solution

FAQs:
