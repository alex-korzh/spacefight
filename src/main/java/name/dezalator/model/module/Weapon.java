package name.dezalator.model.module;

import name.dezalator.model.module.base.BaseModule;

public class Weapon extends BaseModule {
    protected int damage;

    public Weapon(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
