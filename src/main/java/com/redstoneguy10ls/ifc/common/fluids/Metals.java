package com.redstoneguy10ls.ifc.common.fluids;

import java.util.Locale;

public enum Metals {
    FIRE_STEEL(0xFFC39E37),
    ICE_STEEL(0xFFB0AE32),
    LIGHTNING_STEEL(0xFF6E0123),
    DWARVEN_ORE(0xFFB7D9BC),
    MYTHRIL(0xFFB7D9BC);

    private final String id;
    private final int color;


    Metals(int color)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.color = color;
    }

    public String getId()
    {
        return id;
    }

    public int getColor()
    {
        return color;
    }
}
