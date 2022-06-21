package name.dezalator.model.module.weapon;

import name.dezalator.model.module.Weapon;
import name.dezalator.model.module.base.Energetic;

public class Laser extends Weapon implements Energetic {
    public Laser(int damage) {
        super(damage);
    }
}
