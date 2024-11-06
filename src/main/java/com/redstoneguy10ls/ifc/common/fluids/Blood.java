package com.redstoneguy10ls.ifc.common.fluids;

import java.util.Locale;

public enum Blood {
    FIRE(0xFFEF5F11),
    ICE(0xFF18FDFF),
    LIGHTNING(0xFF7200FF);

    private final String id;
    private final int color;


    Blood(int color)
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
