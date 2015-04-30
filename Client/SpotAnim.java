// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class SpotAnim {

	public static void unpackConfig(StreamLoader streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("spotanim.dat"));
		int length = stream.readUnsignedWord() + 600;
		if(cache == null)
			cache = new SpotAnim[length];
		for(int j = 0; j < length; j++) {
			if(cache[j] == null)
				cache[j] = new SpotAnim();
			cache[j].anInt404 = j;
			if(j < 666)
				cache[j].readValues(stream);
			else
				setGFXBase(j);
			if(j == 1223){ // Bandos
				cache[j].anInt405 = 28211;// Model
				cache[j].anInt406 = 7077; // animation
				cache[j].aAnimation_407 = Animation.anims[cache[j].anInt406];// Update
			} else if(j == 1222){ // Armadyl
				cache[j].anInt405 = 28249;
				cache[j].anInt406 = 7075;
				cache[j].aAnimation_407 = Animation.anims[cache[j].anInt406];
			} else if(j == 1220){ // Saradomin
				cache[j].anInt405 = 28195;
				cache[j].anInt406 = 7068;
				cache[j].aAnimation_407 = Animation.anims[cache[j].anInt406];
			} else if(j == 1221){ // zamorak
				cache[j].anInt405 = 28223;
				cache[j].anInt406 = 7069;
				cache[j].aAnimation_407 = Animation.anims[cache[j].anInt406];
			} else if(j == 1224){ // saradomin sword
				cache[j].anInt405 = 28176;
				cache[j].anInt406 = 6968;
				cache[j].aAnimation_407 = Animation.anims[cache[j].anInt406];
			}
		}
	}

	public static void setGFXBase(int j) {
		cache[j].anInt405 = cache[369].anInt405;
		cache[j].aAnimation_407 = cache[369].aAnimation_407;
		cache[j].anInt406 = cache[369].anInt406;
		cache[j].anInt410 = cache[369].anInt410;
		cache[j].anInt411 = cache[369].anInt411;
		cache[j].anInt412 = cache[369].anInt412;
		cache[j].anInt413 = cache[369].anInt413;
		cache[j].anInt414 = cache[369].anInt414;
		cache[j].anIntArray408 = cache[369].anIntArray408;
		cache[j].anIntArray409 = cache[369].anIntArray409;
	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if(i == 0)
				return;
			if(i == 1)
				anInt405 = stream.readUnsignedWord();
			else if(i == 2) {
				anInt406 = stream.readUnsignedWord();
				if(Animation.anims != null)
					aAnimation_407 = Animation.anims[anInt406];
			} else if(i == 4)
				anInt410 = stream.readUnsignedWord();
			else if(i == 5)
				anInt411 = stream.readUnsignedWord();
			else if(i == 6)
				anInt412 = stream.readUnsignedWord();
			else if(i == 7)
				anInt413 = stream.readUnsignedByte();
			else if(i == 8)
				anInt414 = stream.readUnsignedByte();
			else if(i >= 40 && i < 50)
				anIntArray408[i - 40] = stream.readUnsignedWord();
			else if(i >= 50 && i < 60)
				anIntArray409[i - 50] = stream.readUnsignedWord();
			else
				System.out.println("Error unrecognised spotanim config code: " + i);
		} while(true);
	}

	public Model getModel() {
		Model model = (Model) aMRUNodes_415.insertFromCache(anInt404);
		if(model != null)
			return model;
		model = Model.method462(anInt405);
		if(model == null)
			return null;
		for(int i = 0; i < 6; i++)
			if(anIntArray408[0] != 0)
				model.method476(anIntArray408[i], anIntArray409[i]);

		aMRUNodes_415.removeFromCache(model, anInt404);
		return model;
	}

	private SpotAnim() {
		anInt400 = 9;
		anInt406 = -1;
		anIntArray408 = new int[6];
		anIntArray409 = new int[6];
		anInt410 = 128;
		anInt411 = 128;
	}

	private final int anInt400;
	public static SpotAnim cache[];
	private int anInt404;
	private int anInt405;
	private int anInt406;
	public Animation aAnimation_407;
	private int[] anIntArray408;
	private int[] anIntArray409;
	public int anInt410;
	public int anInt411;
	public int anInt412;
	public int anInt413;
	public int anInt414;
	public static MRUNodes aMRUNodes_415 = new MRUNodes(30);

}
