package service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
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
		private Integer step;
		private Decision current;
		private Decision working;
		private Decision best;
		private Integer n;


		@FunctionalInterface
		private interface PositionCheck {
				boolean check(Integer a1, Integer a2);

		}

		public void calculate(){

		}

		private void generateSolution(Decision decision){
				for (int i = 0; i < n; i++) {
						decision.accommodation.add(i);
				}
				for (int i = 0; i < n; i++) {
						tweakSolution(decision);
				}
		}

		private void tweakSolution(Decision decision){
				int index1 = random.nextInt(n);
				int index2;
				do{
						index2 = random.nextInt(n);
				}while (index2 == index1);
				Collections.swap(decision.accommodation, index1, index2);
		}

		public void computeEnergy(Decision decision){
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
