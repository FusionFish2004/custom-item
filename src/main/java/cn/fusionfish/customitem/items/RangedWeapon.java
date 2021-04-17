package cn.fusionfish.customitem.items;

import cn.fusionfish.customitem.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public abstract class RangedWeapon extends Item{

    public static final NamespacedKey WEAPON_RANGE = new NamespacedKey(Main.getInstance(), "weapon_range");
    public static final NamespacedKey WEAPON_COOLDOWN = new NamespacedKey(Main.getInstance(), "weapon_cooldown");

    private double range = 0;
    private int cooldown = 0;

    public RangedWeapon(Builder builder) {
        super(builder);
        this.range = builder.getRange();
        this.cooldown = builder.getCooldown();
    }

    @Override
    public void addKeys(Set<ItemKey> keySet) {
        keySet.add(new ItemKey(WEAPON_RANGE, PersistentDataType.DOUBLE, range));
        keySet.add(new ItemKey(WEAPON_COOLDOWN, PersistentDataType.INTEGER, cooldown));
    }

    public static abstract class Builder extends Item.Builder {
        private double range;
        private int cooldown;

        public Builder range(double range) {
            this.range = range;
            return this;
        }

        public Builder cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public double getRange() {
            return range;
        }

        public int getCooldown() {
            return cooldown;
        }

        @Override
        public void getKeys(PersistentDataContainer container) {
            double range = container.get(WEAPON_RANGE, PersistentDataType.DOUBLE);
            int cooldown = container.get(WEAPON_COOLDOWN, PersistentDataType.INTEGER);

            range(range);
            cooldown(cooldown);
        }
    }
}
