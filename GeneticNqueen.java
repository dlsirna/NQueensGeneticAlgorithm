// Course: CS3642
// Student name: Dylan Sirna
// Student ID: x163-78-1736 
// Assignment #: #2 
// Due Date: 10/01/2020

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class GeneticNqueen {
	static ArrayList<member> curGeneration = new ArrayList<member>();
	static ArrayList<member> solutionSet = new ArrayList<member>();
	static ArrayList<member> uniqueSolutionSet = new ArrayList<member>();
	static int SizeNbyN;
	public static int solutionsCount = 0;
	public static int count11 = 0;

	// change the number of generations
	static int generations;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("enter n number of queens");
		SizeNbyN = scan.nextInt();
		System.out.println("enter number of generations");
		generations = scan.nextInt();
		Integer[] Adam = new Integer[SizeNbyN];
		Integer[] Eve = new Integer[SizeNbyN];
		for (int i = 0; i < SizeNbyN; i++) {
			Adam[i] = i;
		}
		// unboxing and cloning
		int[] unboxed = Arrays.stream(Adam).mapToInt(Integer::intValue).toArray();
		int[] holdBox = unboxed.clone();
		Integer[] boxed = Arrays.stream(holdBox).boxed().toArray(Integer[]::new);

		Eve = boxed;
		Collections.shuffle(Arrays.asList(Adam));
		Collections.shuffle(Arrays.asList(Eve));

		member seed1 = new member("", Adam, fitness(Adam));
		member seed2 = new member("", Eve, fitness(Eve));
		curGeneration.add(seed1);
		curGeneration.add(seed2);

		for (int i = 0; i < generations; i++) {
			newGeneration(curGeneration);
		}

		checkMaxSolutions();
		System.out.println("set of unique solutions: " + uniqueSolutionSet);

		// System.out.println(fitness(Adam));
	}

//this is the main driver method that creates a new generation with consideration for mutation and genetic crossover
	@SuppressWarnings("unchecked")
	static void newGeneration(ArrayList<member> d) {

		Integer[] Adam = d.get(0).getDNA();
		Integer[] Eve = d.get(1).getDNA();

		System.out.println("Adam for Generation " + count11 + " is " + Arrays.toString(Adam));
		System.out.println("Eve for Generation " + count11 + " is " + Arrays.toString(Eve));

		curGeneration.clear();

		for (int i = 0; i < (SizeNbyN * SizeNbyN); i++) {
			Integer[] hold = commonGenetics(Adam, Eve);
			curGeneration.add(new member(count11 + "." + i, hold, fitness(hold)));
			// System.out.println(curGeneration.get(i).getFitness());
		}
		count11++;
		;
		Collections.sort(curGeneration);

		for (int i = 1; i < curGeneration.size(); i++) {

			if (curGeneration.get(i).getFitness() == 1.0)
				solutionSet.add(new member(curGeneration.get(i).getName(), curGeneration.get(i).getDNA(),
						curGeneration.get(i).getFitness()));

		}

	}

	// this is our cross-over function
	static Integer[] commonGenetics(Integer[] Adam, Integer[] Eve) {

		// to use Collections.shuffle I was using Integer Wrapper, but to copy, I have
		// to unbox to a primitive copy and then rebox the result so I can work with the
		// Integer wrapper again, this is where genetic crossover takes place, I shuffle
		// the elements except for the elements that adam and Eve have in common..
		int[] unboxed = Arrays.stream(Adam).mapToInt(Integer::intValue).toArray();
		int[] holdBox = unboxed.clone();
		Integer[] boxed = Arrays.stream(holdBox).boxed().toArray(Integer[]::new);
		Integer[] hold = boxed;

		Collections.shuffle(Arrays.asList(hold));
		int swapValue1;
		for (int i = 0; i < Adam.length; i++) {
			if (Adam[i] == Eve[i]) {
				// this is where we preserve common elements between the 2 DNA strands
				for (int j = 0; j < Adam.length; j++) {
					if (hold[j] == Adam[i]) {
						swapValue1 = hold[i];
						hold[i] = hold[j];
						hold[j] = swapValue1;

					}
				}

			}

		}
		// System.out.println(Arrays.toString(hold));
		System.out.println("before mutation " + Arrays.toString(hold));
		return mutate(hold);
	}

	// mutation function
	static Integer[] mutate(Integer[] temp) {
		// unboxing to copy
		int[] unboxed = Arrays.stream(temp).mapToInt(Integer::intValue).toArray();
		int[] holdBox = unboxed.clone();
		Integer[] boxed = Arrays.stream(holdBox).boxed().toArray(Integer[]::new);
		Integer[] hold1 = boxed;

		int index1 = (int) (Math.random() * (SizeNbyN - 0) + 0);
		int index2 = (int) (Math.random() * (SizeNbyN - 0) + 0);

		// most of the time only mutate 1 element
		Integer swap = hold1[index1];
		hold1[index1] = hold1[index2];
		hold1[index2] = swap;

		// this allows for a a possibly large swap, this is to break free from a
		// local maxima
		if (index1 < index2) {
			Collections.shuffle(Arrays.asList(hold1).subList(index1, index2));
		}
		System.out.println("after mutation " + Arrays.toString(hold1));
		return hold1;

	}

	// this checks for diagonal conflicts, also by not allowing repeats, and 1
	// position per index, in the array we eliminate the possibility of row and
	// column conflict
	static double fitness(Integer[] child) {
		double count1 = 0;
		for (int i = 0; i < child.length; i++) {
			for (int j = 0; j < child.length; j++) {
				if (i != j) {
					if (Math.abs(child[i] - i) == Math.abs(child[j] - j)) {
						count1++;
						System.out.println("conflicting points " + child[i] + ", " + i + " and " + child[j] + ", " + j);
					}
				}
			}
		}
		double fitness = 1.0 / (1.0 + count1);
		if (fitness == 1.0) {
			System.out.println("An answer is " + Arrays.toString(child));

		}

		return fitness;

	}

//we get rid of repeat solutions in this function
	static void checkMaxSolutions() {

		int setSize = solutionSet.size();

		for (int i = 0; i < setSize; i++) {
			Boolean count = true;
			for (int j = 0; j < setSize; j++) {
				if (i != j) {
					if (Arrays.equals(Arrays.stream(solutionSet.get(i).getDNA()).mapToInt(Integer::intValue).toArray(),
							Arrays.stream(solutionSet.get(j).getDNA()).mapToInt(Integer::intValue).toArray())) {
						count = false;

					}
				}

			}

			if (count) {
				uniqueSolutionSet.add(new member(solutionSet.get(i).getName(), solutionSet.get(i).getDNA(),
						solutionSet.get(i).getFitness()));
			}
		}

		switch (SizeNbyN) {

		case 4:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 2");
			break;
		case 5:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 10");
			break;
		case 6:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 4");
			break;
		case 7:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 40");
			break;
		case 8:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 92");
			break;
		case 9:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 352");
			break;
		case 10:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 724");
			break;
		case 11:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 2,680");
			break;
		case 12:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 14,200");
			break;
		case 13:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 73,712");
			break;
		case 14:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 365,596");
			break;
		case 15:
			System.out.println("Number of unique solutions found in " + generations + " generations = "
					+ uniqueSolutionSet.size());
			System.out.println("Number of solutions possible for N = " + SizeNbyN + " puzzle is 2,279,184");
			break;
		default:
			System.out.println("Number of unique solutions found = " + uniqueSolutionSet.size() + "\n for N = "
					+ SizeNbyN + " puzzle in " + generations + "generations");

		}

	}
}
//custom object to hold important info about members of a generation
@SuppressWarnings("rawtypes")
class member implements Comparable {
	private Integer[] DNA;
	private double fitness;
	private String name;

	public member(String name, Integer[] DNA, double fitness) {
		this.name = name;
		this.DNA = DNA;
		this.fitness = fitness;
	}

	public Integer[] getDNA() {
		return DNA;
	}

	public void setState(Integer[] DNA) {
		this.DNA = DNA;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Object comp) {
		double compareFitness = ((member) comp).getFitness();
		return (int) ((compareFitness * 1000) - (this.fitness * 1000));
	}

	@Override
	public String toString() {
		return "MemberValues : name " + name + " - fitness " + fitness + " - DNA " + Arrays.toString(DNA) + "\n";
	}

}