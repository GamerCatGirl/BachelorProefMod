```json
{
  "title": "2. Run",
  "icon": "minecraft:orange_stained_glass",
  "associated_items": [
    "minecraft:nether_quartz_ore"
  ]
}
```

**Run1**

When you go to the learning environment,

> (define run-1 (insertion-sort vect-1 lighter-than))

will start running, the robot will execute the code step by step and will show you the result of each step.  

;;;;;

When we have 3 blocks: a white block, gray block and a black block. At the moment they are in a given vector:

> (define vect-1 (vector 'white-stained-glass 'tinted-glass 'gray-stained-glass))

**Q3: Can you explain what exactly happend to the blocks, in which order to get them sorted?**

;;;;;

**Run2** 

Now you can go back to the learnning environment and the algorithm will show you what happends if the comparison of elements are not strict anymore. The code will run following code:

> (define run-2 (insertion-sort vect-2 lighter-than))

;;;;;

Now we are given a vector of 4 blocks, but we have 2 black blocks. Will the algorithm behave different?

> (define vect-2 (vector 'white-stained-glass 'tinted-glass 'tinted-glass 'gray-stained-glass))

**Q4: What happend differently, was this different from what you wrote down in Q6?**