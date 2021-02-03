# NQueensGeneticAlgorithm
 Solving the N queens problem using a genetic algorithm approach, this program is good at finding 1-3 unique solutions to the N-queens problem,  but suffers from rapid convergence, perhaps a more agressive approach to mutation would improve the ability to find more unique solutions. A pretty print out is provided for boards where n = 4-15
 
 1) User can select the size of the n * n playing board and the number of generations the algorithm wil run.
 2) The genetic algorithm uses an adam and eve structure where only the top 2 sucessors survive each generation fo size n^2 , this is implemented with a priority queue and a custom comparable inteface that takes the object and sorts the queue based on the user define fitness function.
 3) The cross-over attempts to maintain the good parts of the sequence by comparing and preserving the common sequence between the 2 most fit individuals, then we random select and indexes to swap to add mutation into the mix(this part needs to be more agresssive or we could add probability of selection into the mix and allow a larger pool of individuals to reproduce to increase genetic diversity).
 4)The eventual readout presents any sucessful solutions as well as the possible number of unique solutions
 
 
 
 
 *something that may look odd, I used an INTEGER wrapped class to use built in class functions, but I was required to unbox and rebox the int arrays when I want to work with the underlying 2d array.
