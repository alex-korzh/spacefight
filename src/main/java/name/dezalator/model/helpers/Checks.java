package name.dezalator.model.helpers;

import name.dezalator.model.module.Protection;
import name.dezalator.model.module.Weapon;
import name.dezalator.model.module.base.Energetic;
import name.dezalator.model.module.base.Kinetic;
import name.dezalator.model.module.base.Rocket;

public class Checks {
    public static boolean checkAbilityToDamage(Weapon weapon, Protection protection) {
        return (protection instanceof Energetic && weapon instanceof Energetic) ||
                (protection instanceof Rocket && weapon instanceof Rocket) ||
                (protection instanceof Kinetic && weapon instanceof Kinetic);
    }
}
