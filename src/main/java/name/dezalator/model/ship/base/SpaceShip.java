package name.dezalator.model.ship.base;

import name.dezalator.model.base.MovableSpaceObject;
import name.dezalator.model.module.protection.Armor;
import name.dezalator.model.module.protection.Shield;
import name.dezalator.model.module.Protection;
import name.dezalator.model.module.Weapon;
import name.dezalator.model.module.weapon.Laser;
import name.dezalator.model.module.weapon.MassDriver;
import name.dezalator.model.module.weapon.RocketLauncher;
import name.dezalator.model.util.Coordinates;
import name.dezalator.util.exceptions.TooManyProtectionException;
import name.dezalator.util.exceptions.TooManyWeaponsException;

import java.util.ArrayList;

public class SpaceShip extends MovableSpaceObject {
    protected int speed;
    protected ArrayList<Weapon> weapons;
    protected ArrayList<Protection> protectionItems;
    public static final int PLACES = 0;

    public SpaceShip(String name, Coordinates coordinates, int speed, ArrayList<Weapon> weapons, ArrayList<Protection> protectionItems) {
        super(name, coordinates);
        if (weapons == null){
            this.weapons = new ArrayList<>();
        } else if (weapons.size() > PLACES) {
            throw new TooManyWeaponsException();
        } else {
            this.weapons = weapons;
        }
        if (protectionItems == null) {
            this.protectionItems = new ArrayList<>();
        } else if (protectionItems.size() > PLACES) {
            throw new TooManyProtectionException();
        } else {
            this.protectionItems = protectionItems;
        }
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<Protection> getProtectionItems() {
        return protectionItems;
    }

    public int getFullHp() {
        return protectionItems.stream().mapToInt(Protection::getHp).sum();
    }

    public int getShieldHp() {
        return protectionItems.stream().filter(e -> e.getClass().equals(Shield.class)).mapToInt(Protection::getHp).sum();
    }

    public int getArmorHp() {
        return protectionItems.stream().filter(e -> e.getClass().equals(Armor.class)).mapToInt(Protection::getHp).sum();
    }

    public int getFullDamage() {
        return weapons.stream().mapToInt(Weapon::getDamage).sum();
    }

    public int getLaserDamage() {
        return weapons.stream().filter(e -> e.getClass().equals(Laser.class)).mapToInt(Weapon::getDamage).sum();
    }

    public int getRocketDamage() {
        return weapons.stream().filter(e -> e.getClass().equals(RocketLauncher.class)).mapToInt(Weapon::getDamage).sum();
    }

    public int getMassDriverDamage() {
        return weapons.stream().filter(e -> e.getClass().equals(MassDriver.class)).mapToInt(Weapon::getDamage).sum();
    }
}
