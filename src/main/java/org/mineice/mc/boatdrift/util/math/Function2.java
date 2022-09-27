package org.mineice.mc.boatdrift.util.math;

@FunctionalInterface
public interface Function2<One, Two, R> {
    public R apply(One one, Two two);
}
