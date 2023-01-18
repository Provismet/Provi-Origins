package com.provismet.proviorigins.extras;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/*
*             end
*           /|
*          / |
*       h /  | a
*        /   |
*       /    |
*      /_____|
* start    o
*/
public class RightAngledTriangle {
    private final double hypotenuseLength; // Defined as the line between the start and end vectors.
    private final double oppositeLength; // The side opposite to the end vector.
    private final double adjacentLength; // The non-hypotenuse side adjacent to the end vector.

    public RightAngledTriangle (Vec3d hypotenuseStart, Vec3d hypotenuseEnd) {
        this.oppositeLength = clamped(hypotenuseEnd.getX() - hypotenuseStart.getX());
        this.adjacentLength = clamped(hypotenuseEnd.getZ() - hypotenuseStart.getZ());
        this.hypotenuseLength = Math.sqrt(this.oppositeLength * this.oppositeLength + this.adjacentLength * this.adjacentLength);
    }

    private double clamped (double value) {
        final double MIN_LENGTH = 0.001;
        if (MIN_LENGTH > MathHelper.abs((float)value)) {
            return MIN_LENGTH;
        }
        return value;
    }

    public double cosine () {
        return this.adjacentLength / this.hypotenuseLength;
    }

    public double sine () {
        return this.oppositeLength / this.hypotenuseLength;
    }

    public double tangent () {
        return sine() / cosine();
    }
}
