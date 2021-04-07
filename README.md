# JAntiyoy
Hexagonal turn-based strategy game based on game made by Yoitro: https://github.com/yiotro/Antiyoy
![JAntiyoy screenshot](https://github.com/mgurga/JAntiyoy/blob/master/docs/screenshot1.png)

## Description
A Simple game where you pay for soldiers and try and take over the small island. 
This includes going against a friend in loacl multiplayer mode.
The game revolves around money, it pays for towers that protect your village.
You start off with $10 which is just enough to play for a level 1 worker, use this person to get unclaimed land.
For every hexagon of land you conquer you get $1 per turn.

## How To Play
There are no keyboard buttons, only icons to click. Each item on the toolbar has a price, 
- Farms: 
  - Cost = $12+(amount of farms you have*2)
  - Generates $2 per turn
- Tower: 
  - Low level tower = $15
  - High level tower = $35
  - Protects adjacent hexs from enemy attack
- Soldier:
  - Level 1 costs = $10
  - Level 2 costs = $20
  - Level 3 costs = $30
  - Level 4 costs = $40
  - Can move 7 hexs per turn
  - Can only take down soldiers of lower level than them
    - does not apply to level 4 soldiers

When you are done with your turn press the end turn button in the lower left

## Interesting Features
1. Small and lightweight java game using only the base Java-1.8 libraries, no extra libraries. 
Very easy to clone and create your own fork
2. Simple and Easily modifiable world generator algorithm
3. Completely Hexagonal Map with the World class to help organize it
