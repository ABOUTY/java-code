public class Dog extends Mammal implements Pet {
	
	Dog (int lifeSpanYears, int weightKg){
		this.lifeSpanYears = lifeSpanYears;
		this.weightKg = weightKg;
	}
	public void makeNoise() {
		System.out.println("Woof woof\n");
	}

	@Override
	public void cuddle() {
		System.out.println("Smelly cuddle");
	}

}
