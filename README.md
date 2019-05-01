# Optimal-Placement-In-Square-Room

This application uses Java concurrency to determine the best combination of individuals in a room. 
Each individual has an affinity for each other individual in the room.
This works off a divide and conquer algorithm (see segmentationMethod() in RoomControl.java) 
where each thread is given a subsection fo the room to find the best combination for their section.
After a set amount of time (or X cycles) the local solutions are compared to find the best solution from that pool.
The best solution is then used as a base combination for future threads to find future local solutions.
This process repeats and will terminate when a certain threshold is met or, when no noticle increase in affinity can be found.
