package name.dezalator.model.module;

import name.dezalator.model.helpers.Checks;
import name.dezalator.model.module.base.BaseModule;


public class Protection extends BaseModule {
    protected int hp;

    public Protection(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void damage(Weapon weapon) {
        if (Checks.checkAbilityToDamage(weapon, this)) {
            hp -= weapon.getDamage();
        }
    }
}
