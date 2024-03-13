```json
{
  "title": "Strict Comparison Bubble Sort",
  "icon": "minecraft:white_stained_glass",
  "associated_items": [
    "minecraft:nether_quartz_ore"
  ]
}
```

From the theory we have seen that bubble sort and insertion sort only work when we use a strict comparison (ex. >, not >=). We want to understand the algorithms to understand why this is the case.

;;;;;

For this exercise you will need the following items:

<recipe;minecraft:white_stained_glass>
<recipe;minecraft:gray_stained_glass>

;;;;;

<recipe;minecraft:black_stained_glass>

To make it a little bit easier for you, we give you 64 blocks of sand, this will be in your inventory ones you go to the learning environment. You can use the furnace to make the glass blocks you need.

;;;;;

You can make [white dye](https://minecraft.fandom.com/wiki/White_Dye) by putting bone meal or a "Lily of the Valley" (white flower) in the crafting table.

You can make [black dye](https://minecraft.fandom.com/wiki/Black_Dye) with an ink sac, this you can get from squids. Squids are very easy to find in the water.

Gray dye can be made by putting black dye and toghether in the crafting table.

;;;;;

**Fase 1: Predict**

When you have following vector of blocks with color: [black, gray, black, white], what will be the output if you sort the vector from light to dark using the insertion sort algorithm? Answer this too for the bubble sort algorithm. Place down the blocks on the ... blocks and see if your predictions were correct.

;;;;;

**Fase 2: Run**

You now can choose to run both algorithms with the given vector and see if your predictions were correct. Write down what the core difference is between the two algorithms.

;;;;;

**Fase 3: Investigate**

Where exactly in the algorithm occurs the problem with non-strict comparisons and why?

;;;;;

**Fase 4: Modify**

Can you modify the algorithms so it works with non-strict comparisons, even though this would make the algorithms less efficient?

;;;;;

**Fase 5: Make**

We know how the selection sort algoritm works from theory, can you now implement this algorithm so we have a simple sorting algorithm that also works with non-strict comparisons?