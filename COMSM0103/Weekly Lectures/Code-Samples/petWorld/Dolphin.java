public class Dolphin extends Mammal implements Pet{
	
	Dolphin (int lifeSpanYears, int weightKg){
		this.lifeSpanYears = lifeSpanYears;
		this.weightKg = weightKg;
	}

	@Override
	public void cuddle() {
		System.out.println("Wet cuddle");
	}

	public void makeNoise() {
		System.out.println("Squeek Click\n");
	}
}
