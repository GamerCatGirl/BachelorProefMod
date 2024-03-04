```json
{
  "title": "5. Make",
  "icon": "minecraft:tinted_glass",
  "associated_items": [
    "minecraft:nether_quartz_ore"
  ]
}
```

Can you now write **selection sort algorithm**. 

Like insertion-sort you also have an outer-idx and an inner-idx. The outer-idx selects the first unsorted element and the inner-idx looks for the smallest element in the unsorted part of the vector. When you find that element you swap the element of the outer-idx with the element of the inner-idx. 

;;;;;

Example: [1, 2, 3, 9, 8, 7, 5]

__Outer-idx:__ 3 (elment: 9) (this is the first element that is not sorted)

__Inner-idx:__ 7 (element: 5) (this is the smallest element in the unsorted part of the vector)

When you swap the elements you get: [1, 2, 3, 5, 8, 7, 9]

Now your outer-idx is incremented by 1 and you start again.
__Outer-idx:__ 4 (element: 8)
