package com.redstoneguy10ls.ifc.world;

public enum DragonOres
{
    NATIVE_COPPER(true),
    NATIVE_GOLD(true),
    HEMATITE(true),
    NATIVE_SILVER(true),
    CASSITERITE(true),
    BISMUTHINITE(true),
    GARNIERITE(true),
    MALACHITE(true),
    MAGNETITE(true),
    LIMONITE(true),
    SPHALERITE(true),
    TETRAHEDRITE(true),
    BITUMINOUS_COAL(false),
    LIGNITE(false),
    GYPSUM(false),
    GRAPHITE(false),
    SULFUR(false),
    CINNABAR(false),
    CRYOLITE(false),
    SALTPETER(false),
    SYLVITE(false),
    BORAX(false),
    HALITE(false),
    AMETHYST(false,3),
    DIAMOND(false),
    EMERALD(false),
    LAPIS_LAZULI(false),
    OPAL(false),
    PYRITE(false),
    RUBY(false,1),
    SAPPHIRE(false,2),
    TOPAZ(false);

    private final boolean graded;
    private final int dragontype;

    DragonOres(boolean graded)
    {
        this(graded,0);
    }
    DragonOres(boolean graded, int dragontype)
    {
        this.graded = graded;
        this.dragontype = dragontype;
    }
    public boolean isGraded()
    {
        return graded;
    }
    public int dragonType(){return dragontype;}
}
