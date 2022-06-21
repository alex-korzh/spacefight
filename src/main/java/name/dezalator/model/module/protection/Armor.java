package name.dezalator.model.module.protection;

import name.dezalator.model.module.Protection;
import name.dezalator.model.module.base.Kinetic;
import name.dezalator.model.module.base.Rocket;

public class Armor extends Protection implements Kinetic, Rocket {
    public Armor(int hp) {
        super(hp);
    }
}
