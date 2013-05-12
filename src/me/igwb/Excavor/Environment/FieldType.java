package me.igwb.Excavor.Environment;

public enum FieldType {

	SMOOTHSTONE1		(0, true, "Smooth stone #1"),
	SMOOTHSTONE2		(1, true, "Smooth stone #2"),
	SMOOTHSTONE3		(2, true, "Smooth stone #3"),
	STONEBORDER_LEFT	(3, true, "Stoneborder - Left"),
	STONEBORDER_UP		(4, true, "Stoneborder - Up"),
	STONEBORDER_RIGHT	(5, true, "Stoneborder - Right"),
	STONEBORDER_DOWN	(6, true, "Stoneborder - Down"),
	COBBLESTONE			(7, true, "Cobblestone"),
	STONEWALL_UP		(8, false , false, "Stonewall - Up"),
	STONEWALL_LEFT		(9, false, false, "Stonewall - Left"),
	STONEWALL_RIGHT		(10, false, false, "Stonewall - Right"),
	STONECORNER_LEFT	(11, false, false, "Stonecorner - Left"),
	STONECORNER_RIGHT	(12, false, false, "Stonecorner - Right"),
	ERROR				(13, true, false, true, "ERROR"),
	WIDE_TREE			(14, false, false, true, "Tree"),
	SPIKY_TREE			(15, false, false, true, "Spiky tree");
	
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
		Transparent = true;
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
