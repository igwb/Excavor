package me.igwb.Excavor.Scripting;

public class Script {

	private Scriptblock superBlock;
	
	public Script(Scriptblock superBlock) {
		setSuperBlock(superBlock);
	}
	
	public Scriptblock getSuperBlock() {
		return superBlock;
	}

	private void setSuperBlock(Scriptblock superBlock) {
		this.superBlock = superBlock;
	}
	
}
