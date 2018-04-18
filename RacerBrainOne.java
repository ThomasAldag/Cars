package car;

public class RacerBrainOne extends RacerBrain {

	private byte[] brainMain;			// das "Zwischen-Gehirn"
	private int distanceMain;			// welche Strecke hat das brainMain zurückgelegt

	public RacerBrainOne(int n) {
		super(n);
		brainMain = new byte[65536];
		copyBrain2BrainMain();
		distance = 0;
	}
	
	private void copyBrain2BrainMain() {
		System.arraycopy(brain, 0, brainMain, 0, brain.length);
	}

	public void mutant() {
		if (distanceMain >= distance) {
			setBrain(brainMain);
		} else {
			copyBrain2BrainMain();
			distanceMain = distance;
		}
		super.mutant();
	}
}
