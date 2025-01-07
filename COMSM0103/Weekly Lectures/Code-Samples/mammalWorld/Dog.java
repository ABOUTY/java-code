public class Dog extends Mammal {
	
	Dog (int lifeSpanYears, int weightKg){
		this.lifeSpanYears = lifeSpanYears;
		this.weightKg = weightKg;
	}
	public void makeNoise() {
		System.out.println("Woof woof\n");
	}
}
