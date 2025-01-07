public class PetPlanet {

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

		Pet [] petArrayObject = new Pet[4];
	
		petArrayObject[0] = new Dolphin(40,20);
		petArrayObject[1] = new Dog(10,20);
		petArrayObject[2] = new Lion(12,250);
		petArrayObject[3] = new FriendlyRobot();
		
		for(Pet pCurr : petArrayObject)
		{
			pCurr.cuddle();
			pCurr.makeNoise();
		}
		Mammal m = new Mammal(10,10);
	}	
}

