package me.igwb.Excavor.Environment;

public enum FieldType {

	STONE				(0, false, "Stone"),
	GRASS				(1, false, "Grass"),
	SMOOTHSTONE3		(2, false, "Smooth stone #3"),
	STONEBORDER_LEFT	(3, false, "Stoneborder - Left"),
	STONEBORDER_UP		(4, false, "Stoneborder - Up"),
	STONEBORDER_RIGHT	(5, false, "Stoneborder - Right"),
	STONEBORDER_DOWN	(6, false, "Stoneborder - Down"),
	COBBLESTONE			(7, false, "Cobblestone"),
	STONEWALL_UP		(8, false, "Stonewall - Up"),
	STONEWALL_LEFT		(9, false, "Stonewall - Left"),
	STONEWALL_RIGHT		(10, false, "Stonewall - Right"),
	STONECORNER_LEFT	(11, false, "Stonecorner - Left"),
	STONECORNER_RIGHT	(12, false, "Stonecorner - Right"),
	ERROR				(13, true, true, "ERROR"),
	WIDE_TREE			(14, false, true, "Tree"),
	SPIKY_TREE			(15, false, true, "Spiky tree"),
	HLeft				(94, true, true, false, "Hide Left"),
	HTop				(95, true, true, false, "Hide Top"),
	HRight				(96, true, true, false, "Hide Right"),
	SLeft				(97, true, true, false, "Show Left"),
	STop				(98, true, true, false, "Show Top"),
	SRight				(99, true, true, false, "Show Right");
	
	public int Image;
	
	public boolean Passable, RenderAlways, Transparent;
	
	public String AssetName;
	
	private FieldType (int i, boolean passable, boolean renderAlways, boolean transparent, String name) {
		AssetName = name;
		Image = i;
		Passable = passable;
		RenderAlways = renderAlways;
		Transparent = transparent;
	}
	
	private FieldType (int i, boolean passable, boolean transparent, String name) {
		AssetName = name;
		Image = i;
		Passable = passable;
		RenderAlways = false;
		Transparent = transparent;
	}
	
	private FieldType (int i, boolean passable, String name) {
		AssetName = name;
		Image = i;
		Passable = passable;
		RenderAlways = false;
		Transparent = false;
	}
	
	public static FieldType getType(int id) {
		
		for(FieldType type : FieldType.values())
			if(type.Image == id)
				return type;

		return null;
	}
	
	public static FieldType getType(String assetName) {
		
		for(FieldType type : FieldType.values())
			if(type.AssetName.equalsIgnoreCase(assetName))
				return type;

		return null;		
	}
}
