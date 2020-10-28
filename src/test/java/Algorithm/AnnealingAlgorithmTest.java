package Algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.AnnealingAlgorithm;
import service.Decision;

public class AnnealingAlgorithmTest {

		@Test
		public void shouldComputeEnergy()
				throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
				AnnealingAlgorithm annealingAlgorithm = new AnnealingAlgorithm();
				Decision decision = new Decision();
				decision.setAccommodation(Arrays.asList(2, 0, 3, 4, 5, 1, 9, 8, 6, 7));
				decision.setEnergy(0);
				annealingAlgorithm.computeEnergy(decision);
				Assertions.assertEquals(8, decision.getEnergy());
		}
}
