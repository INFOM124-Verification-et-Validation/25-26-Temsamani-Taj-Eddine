# Specification-based Testing

## 1. Goal, inputs and outputs
- Goal: prochaine position de clyde
- Input domain: map +joueur + pos de clyde
- Output domain: direction ou rien 

## 2. Explore the program (if needed)

## 3. Identify input and output partitions

### Input partitions
distance partition : 
- si : distance < 8, il essayera de s enfuire
- si : distance > 8, il essayera de se rapprocher de pacman
- si : distance = 8, il essayera de rester dans cette distance

pac-man not on the board partition
pac-man at the edge of the board partition
pac-man does not have a square partition

obstacle direction partitions:
- d1: path of clyde is free
- d2: path of clyde is blocked
- d3: clyde is on pac-man
- d4: clyde has multiple valid moves

Not valid cases:
- multiple cludes on the map
- multiple pac-man on the map
#### Individual inputs
- distance < 8 & path free
- distance < 8 & path blocked
- distance < 8 & multiple moves 

- distance > 8 & path free
- distance > 8 & path blocked
- distance > 8 & multiple moves

- distance = 8 & path free
- distance = 8 & path blocked
- distance = 8 & multiple moves
#### Combinations of input values

### Output partitions

## 4. Identify boundaries

## 5. Select test cases