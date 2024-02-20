## Installation
1. Install gradle
    - in terminal: ./gradlew genSources
2. Run Minecraft client (by opening gradle on the right of Intellij "Elephant Icon"


### In Minecraft 
- Summon a robot: /summon bachelorproef:robot (only with cheats on)
- Go to shrine: /locate structure bachelorproef:shrine (only with cheats on)



### Interactions with robot: 
- tame robot to assign to a certain user with iron_ignot
- right click on robot to view advancements (unlocked functions of robot)

### Interactions with functionblock:
- right click on functionblock to view the GUI 

View lesson Book:
- /get-lavender-book bachelorproef:testbook

### Add scheme code to shrines with lesson name 
- in src/resources/assets/bachelorproef/racket/lesson_name/
- file name: lesson.rkt
- (define (predict) ....) where the student need to predict the outcome of the function


## TODO 
- [x] Install lavender to make books in markdown 
- [ ] Add books to get information for lessons
- [ ] Make UI function block interactive (!)
- [ ] racket parser to check if the function is correct (!!!) 
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

## Credits
- [Lavender](https://github.com/wisp-forest/lavender)
- [Scheme parser](https://norvig.com/jscheme.html)
- [Minecraft Modding](https://www.youtube.com/channel/UCbzPhyLcO8VP25dZ7kaUyAw)
- [Inspo Design Robot](https://sketchfab.com/3d-models/robot-mc-0f40c981c4ec4777b52bbc448e319a5c)
