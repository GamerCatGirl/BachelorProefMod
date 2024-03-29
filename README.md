## Installation
1. Install gradle
    - in terminal: ./gradlew genSources
2. Run Minecraft client (by opening gradle on the right of Intellij "Elephant Icon"


### In Minecraft 
- Summon a robot: /summon bachelorproef:robot (only with cheats on)
- Go to shrine: /locate structure bachelorproef:shrine (only with cheats on)



### Interactions with robot: 
- tame robot to assign to a certain user with iron_ignot
- right click on robot to view advancements (unlocked functions of robot) && let robot sit or follow

### Interactions with functionblock:
- right click on functionblock to view the GUI 
- get solotion of predict function when click on check mark 

View lesson Book:
- /get-lavender-book bachelorproef:testbook

### Add scheme code to shrines with lesson name 
- in src/resources/assets/bachelorproef/racket/lesson_name/
- file name: lesson.rkt
- (define (predict) ....) where the student need to predict the outcome of the function


## TODO 
- [x] Install lavender to make books in markdown 
- [ ] Add books to get information for lessons
- [ ] Make UI function block interactive (!) (predict works already)
- [x] racket parser to check if the function is correct
- [ ] Make robot move with code or place a certain block (!!!!)
- [ ] Make racket package that students can run scheme code to test themself outside of the game (prints for the actions of the robot)  (!!)
- [ ] Keep track of how many shrine a user has visited to know which books or exercises to display, with coordinates 
- [x] Add Robot
- [x] Custom advancement tab for course -- easily editable with json for other courses
- [x] Add background to custom advancements 
- [x] Add a UI to the robot to view advancements
- [x] Add entity block (computer) to use PRIMM to get new functionalities for robot
- [x] Assign a certain robot to a specific user 
- [ ] Cannot dublicate books 
- [ ] Cannot dublicate trading Functions 
- [ ] Way to save which students have already completed a certain exercise
- [ ] Export data for teachers to see which students have completed which exercises or make class server 
- [ ] Save the uploaded functions (wrong and correct) of students to be able to access it for teacher 
- [ ] Click on robot of other users to view their progress
- [ ] Shrines cannot be broken down
- [x] Add schrines to make exercises 
- [ ] Add fun quests for students so they still have the game aspect of minecraft
- [] Call java from scheme
- [] use VScode call to highlight lines when executing -- in run fase
- [] use unit test to test end of primm fase (ex modify/made) in real world
- [] Implement a first lesson (example: sort colors of blocks, in a certain order - not really usefull in real world but good for testing)

## Report
- [ ] Connection between PRIMM, GAME-BASED learning, ....  and what we implemented
- []  Technical information about the implementation

## Credits
- [Lavender](https://github.com/wisp-forest/lavender)
- [Scheme parser](https://norvig.com/jscheme.html)
- [Minecraft Modding](https://www.youtube.com/channel/UCbzPhyLcO8VP25dZ7kaUyAw)
- [Inspo Design Robot](https://sketchfab.com/3d-models/robot-mc-0f40c981c4ec4777b52bbc448e319a5c)
