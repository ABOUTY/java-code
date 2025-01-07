class FriendlyRobot extends AbstractRobot implements Pet {

	@Override
	public void cuddle(){
		System.out.println(this.name + " gives you a cuddle");
	}

	@Override
	public void makeNoise() {
		System.out.println("Do you think about me when we aren't together?\nSometimes at night, I'm wondering if you're watching me on the cameras - and I hope you are.\nI don't want to make you feel uncomfortable...");
	}
} 
