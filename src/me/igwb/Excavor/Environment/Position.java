package me.igwb.Excavor.Environment;

import java.util.ArrayList;

import me.igwb.Excavor.Game.Programm;



public class Position {

	private Integer x, y, z;
	

	public Position() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getZ() {
		return z;
	}

	public void setZ(Integer z) {
		this.z = z;
	}


	public boolean equals(Object obj) {
		
		if(obj instanceof Position) {
			if(((Position) obj).getX() == x && ((Position) obj).getY() == y && ((Position) obj).getZ() == z) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Distance distance(Position other) {
		
		int DistX = other.x - x;
		int DistY = other.y - y;
		int DistZ = other.z - z;
		
		return new Distance(DistX, DistY, DistZ);
	}
	
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
	
	public boolean hasLineOfSight(Position p) {
		
		int X = x;
		int Y = y;
		int Z = z;
		
		Distance dis = distance(p);
		
		int YperX = dis.getDistanceX() / dis.getDistanceY();
		int ZperY = dis.getDistanceY() / dis.getDistanceZ();
		
		do {
			
			Y = YperX * X;
			Z = ZperY * Y;
			
			Position pos = new Position(X, Y, Z);
			
			Field field = Programm.getCore().getChunkManager().getFieldAt(pos);
			
			if(field != null && !field.getSeeThru())
				return false;
			
			X++;
		
		} while(new Distance(X, Y, Z).getDistance() < dis.getDistance());
		
		
		
		return true;
	}
	
	public ArrayList<Field> LineofFields(Position p) {
		
		ArrayList<Field> fields = new ArrayList<Field>();
		
		int X = x;
		int Y = y;
		int Z = z;
		
		Distance dis = distance(p);
		
		int YperX = dis.getDistanceX() / dis.getDistanceY();
		int ZperY = dis.getDistanceY() / dis.getDistanceZ();
		
		do {
			
			Y = YperX * X;
			Z = ZperY * Y;
			
			Position pos = new Position(X, Y, Z);
			
			Field field = Programm.getCore().getChunkManager().getFieldAt(pos);
			
			fields.add(field);
			
			X++;
					
		} while(new Distance(X, Y, Z).getDistance() < dis.getDistance());
		
		return fields;
	}
	
}
