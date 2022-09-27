package org.mineice.mc.boatdrift;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DriftHandler implements AutoCloseable {
    private BossBar bar;
    private final BoatDrift plugin;
    private final Player player;

    private double multiplier, counter, heat;

    private boolean raceMode = false;
    private double raceDelta = 0;

    private int taskId = -1;

    public DriftHandler(@NotNull BoatDrift plugin, @NotNull Player player) {
        this.player = player;
        this.plugin = plugin;
    }

    public void enable() {
        bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        bar.setProgress(.5);
        bar.addPlayer(player);
        bar.setVisible(true);
        startCounting();
    }

    public void disable() {
        stopCounting();
        bar.removeAll();
        bar.setVisible(false);
    }

    public boolean isRaceModeEnabled() {
        return raceMode;
    }

    public void setRaceMode(boolean raceMode) {
        this.raceMode = raceMode;
    }

    public void setRaceDelta(double newDelta) {
        raceDelta = newDelta;
    }

    void setHeat(double newHeat) {
        heat = lerp(heat, newHeat, .01);
        bar.setProgress(heat);
    }

    void updateLabel() {
        var msg = String.format("Multipiler: %.2f   Score: %,.0f", multiplier, counter);
        bar.setTitle(msg);
    }

    void startCounting() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            double prevPosX = player.getVehicle().getLocation().getX();
            double prevPosY = player.getVehicle().getLocation().getY();
            double prevPosZ = player.getVehicle().getLocation().getZ();

            double mul = 0f;
            double _score, _counter;
            boolean trig;
            double a,b;
            Vector getVelo(){
                var boat = player.getVehicle();
                var prev = new Vector(prevPosX, prevPosY, prevPosZ);
                prevPosX = boat.getLocation().getX();
                prevPosY = boat.getLocation().getY();
                prevPosZ = boat.getLocation().getZ();
                var vec = new Vector(prevPosX, prevPosY, prevPosZ);
                return vec.subtract(prev).multiply(20);
            }

            @Override
            public void run() {
                var deltaTime = 1.0 / 20.0;
                var boat = player.getVehicle();
                var velocity = getVelo();
                var dir = boat.getLocation().getDirection().normalize();
                if(velocity.lengthSquared() == 0) return;
                var angle = Math.acos(velocity.clone().normalize().dot(dir));

                angle = Math.max(angle - 0.13, 0);
                if (angle < Math.PI * 0.8)
                    angle = angle / (Math.PI * 0.6);
                else
                    angle = 0;
                angle = -1 / (angle - 1) - 1;
                if(angle >1000)
                {
                    angle = 1000;
                }
                a = lerp(a,angle,0.01);
                var speed = Math.max(velocity.length() - 6 / (angle + 1) - 0.5, 0);
                speed = Math.pow(speed, 1.2);
                speed *= 0.5;
                var heat = 0.0;
                if (angle * speed > 0.15)
                {
                    angle = a;
                    heat = angle*speed*deltaTime*0.04;
                    if (mul < 50)
                        mul += heat;
                    else mul = 50;
                    _counter += angle * mul * speed;
                    b = 0;
                }
                else
                {
                    b+= deltaTime*0.005*mul;
                    a = 0;
                    if (mul > 0.75)
                        mul -= b;
                    else mul = 0.75;
                }
                if(mul>1f)
                {
                    //multipiler.enabled = true;
                    //multipiler.text = string.Format("x{0:n1}", mul);
                    //counter.enabled = true;
                    //counter.text = string.Format("{0:n0}", _counter);
                    trig = true;
                }
                else
                {
                    if (trig)
                    {
                        _score += _counter;
                        _counter = 0;
                        trig = false;
                    }
                    //multipiler.enabled = false;
                    //counter.enabled = false;
                }
                //score.text = string.Format("{0:n0}", _score)
                heat = Math.max(heat*100, 0);
                var progress = 1 - 1/(heat+1); // this value is below one when heat > 0
                if(!raceMode || raceDelta > 0) {
                    setHeat(progress);
                    multiplier = mul;
                    counter = _counter;
                }
                updateLabel();
            }
        }, 0, 0);
    }

    void stopCounting() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void close() throws Exception {
        disable();
    }

    private double lerp(double a, double b, double v) {
        return a * (1 - v) + b * v;
    }
}
