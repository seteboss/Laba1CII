package service;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Decision {
		List<Integer> accommodation = new ArrayList<>();
		Integer energy;
}
