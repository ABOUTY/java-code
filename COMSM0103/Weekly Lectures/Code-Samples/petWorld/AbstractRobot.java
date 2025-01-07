abstract class AbstractRobot {
	protected String name;
	protected int powerLevel;
	
	AbstractRobot(){
		this("Ava", 100);
	}
	AbstractRobot(String name, int powerLevel){
		this.name = name;
		this.powerLevel = powerLevel;
	}
	
	void talk(String phrase) {
		if (powerLevel >= 1) {
			System.out.println(name + " says " + phrase);
			powerLevel -= 1;
		} else {
			System.out.println(name + " is too weak to talk.");
		}
	}
}