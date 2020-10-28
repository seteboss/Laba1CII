package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AnnealingAlgorithm {
		private final Random random = new Random();
		private Double initialTemperature;
		private Double finalTemperature;
		private Double temperature;
		private Double delta;
		private Double calc;
		private Double test;
		private Double alpha;
		private Integer step;
		private Decision current = new Decision();
		private Decision working = new Decision();
		private Decision best = new Decision();
		private Integer n;
		private List<Double> temperatures = new ArrayList<>();
		private List<Integer> energies = new ArrayList<>();


		public void calculate() {
				generateSolution(current);
				computeEnergy(current);
				init();
				temperatures.clear();
				energies.clear();
				temperature = initialTemperature;
				while (temperature > finalTemperature) {
						for (int i = 0; i < step; i++) {
								tweakSolution(working);
								computeEnergy(working);
								if (working.energy <= current.energy) {
										saveWorkingDecision();
								} else {
										test = random.nextDouble();
										delta = (double) (working.energy - current.energy);
										calc = Math.exp(-delta / temperature);
										if (calc > test) {
												saveWorkingDecision();
										}
								}
						}
						temperatures.add(-temperature);
						energies.add(current.energy);
						temperature *= alpha;
						if (best.energy == 0) {
								break;
						}
				}
		}

		private void saveWorkingDecision() {
				copySolution(working, current);
				if (current.energy <= best.energy) {
						copySolution(current, best);
				} else {
						copySolution(current, working);
				}
		}

		private void generateSolution(Decision decision){
				decision.accommodation = new ArrayList<>();
				for (int i = 0; i < n; i++) {
						decision.accommodation.add(i);
				}
				for (int i = 0; i < n; i++) {
						tweakSolution(decision);
				}
		}

		private void init(){
				working.accommodation = new ArrayList<>(current.accommodation);
				working.energy = current.energy;
				best.accommodation = new ArrayList<>(current.accommodation);
				best.energy = current.energy;
		}

		private void tweakSolution(Decision decision){
				int index1 = random.nextInt(n);
				int index2;
				do{
						index2 = random.nextInt(n);
				}while (index2 == index1);
				Collections.swap(decision.accommodation, index1, index2);
		}

		private void copySolution(Decision source, Decision target){
				Collections.copy(target.getAccommodation(), source.getAccommodation());
				target.setEnergy(source.getEnergy());
		}

		public void computeEnergy(Decision decision){
				decision.energy = 0;
				for (int i = 0; i < decision.accommodation.size() - 1; i++) {
						Integer item = decision.accommodation.get(i);
						for (int j = i + 1; j < decision.accommodation.size(); j++) {
								if (j - i == item - decision.accommodation.get(j) || j - i == decision.accommodation.get(j) - item) {
										decision.energy++;
								}
						}
				}
		}



}
