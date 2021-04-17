package cn.fusionfish.customitem.bullets;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public abstract class Bullet {
    private Location position;
    private final double speed;
    private Vector direction;
    private final int life;

    private Consumer<Player> hitAction = player -> {};
    private Consumer<Bullet> flightAction = bullet -> {};

    protected Bullet(double speed, int life) {
        this.speed = speed;
        this.life = life;
    }

    public Location getPosition() {
        return position;
    }

    public void setPosition(Location position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Consumer<Player> getHitAction() {
        return hitAction;
    }

    public void setHitAction(Consumer<Player> hitAction) {
        this.hitAction = hitAction;
    }

    public Consumer<Bullet> getFlightAction() {
        return flightAction;
    }

    public void setFlightAction(Consumer<Bullet> flightAction) {
        this.flightAction = flightAction;
    }

    public void checkCollision() {

    }
}
