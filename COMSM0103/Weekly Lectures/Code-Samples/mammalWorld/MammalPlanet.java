public class MammalPlanet {

	public static void main(String [] args) {	
		
		Mammal [] mammalArrayObject = new Mammal[3];
	
		mammalArrayObject[0] = new Dolphin(40,20);
		mammalArrayObject[1] = new Dog(10,20);
		mammalArrayObject[2] = new Lion(12,250);		
		
		for(Mammal mCurr : mammalArrayObject)
		{
			mCurr.stateAttributes();
			mCurr.makeNoise();
		}
		Mammal m = new Mammal(10,10);
	}	
}

/*the method to execute is chosen based on the availability of an implementation nearest to the receiver class upwards in the class hierarchy (possibly the class of the receiver object itself)*/

