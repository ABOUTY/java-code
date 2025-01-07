public class Mammal {	
	
	public int lifeSpanYears;
	public int weightKg;

	Mammal(int lifeSpanYears, int weightKg){
		this.weightKg = weightKg;
		this.lifeSpanYears = lifeSpanYears;
	}
	
	Mammal(){
		this(-1,-1);
	}	
	
	public void stateAttributes(){
		System.out.println("I am warm blooded, I have lungs and I have 3 bones in the inner ear. I live " + lifeSpanYears + " years and weigh around " + weightKg + "kg");
	}	
	
	public void makeNoise(){
		System.out.println("Generic mammal sound");
	};
}
