package name.dezalator.model.module;

import name.dezalator.model.module.base.BaseModule;

public class Protection extends BaseModule {
    protected int hp;

    public Protection(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void damage(int amount) {
        hp -= amount;
    }
}
