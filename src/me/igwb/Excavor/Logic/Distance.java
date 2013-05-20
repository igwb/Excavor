package me.igwb.Excavor.Logic;

public class Distance {

	private Integer DistanceX, DistanceY, DistanceZ;

	public Distance(int Dx, int Dy, int Dz) {
		DistanceX = Dx;
		DistanceY = Dy;
		DistanceZ = Dz;
	}

	public Integer getDistance() {
		int Dx = Math.abs(DistanceX);
		int Dy = Math.abs(DistanceY);
		int Dz = Math.abs(DistanceZ);

		double Output = Math.pow(Dx*Dx + Dy*Dy + Dz*Dz, 0.5); //Root of (Dx² + Dy² + Dz²)

		return Math.round((float)Output);
	}

	public Integer getDistance(int modX, int modY, int modZ) {
		int Dx = Math.abs(DistanceX + modX);
		int Dy = Math.abs(DistanceY + modY);
		int Dz = Math.abs(DistanceZ + modZ);

		double Output = Math.pow(Dx*Dx + Dy*Dy + Dz*Dz, 0.5); //Root of (Dx² + Dy² + Dz²)

		return Math.round((float)Output);
	}

	public Integer getDistanceX() {
		return DistanceX;
	}

	public Integer getDistanceY() {
		return DistanceY;
	}

	public Integer getDistanceZ() {
		return DistanceZ;
	}

}
