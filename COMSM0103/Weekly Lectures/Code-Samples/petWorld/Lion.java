public class Lion extends Mammal implements Pet{
	
	Lion (int lifeSpanYears, int weightKg){
		this.lifeSpanYears = lifeSpanYears;
		this.weightKg = weightKg;
	}

	@Override
	public void cuddle() {
		System.out.println("Dangerous cuddle");
	}

	public void makeNoise() {
		System.out.println("GRRRRRRRRRR Roar!\n");
	}
}
