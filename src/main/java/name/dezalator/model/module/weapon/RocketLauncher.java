package name.dezalator.model.module.weapon;

import name.dezalator.model.module.Weapon;
import name.dezalator.model.module.base.Rocket;

public class RocketLauncher extends Weapon implements Rocket {
    public RocketLauncher(int damage) {
        super(damage);
    }
}
