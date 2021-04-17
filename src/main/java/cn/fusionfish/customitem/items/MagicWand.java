package cn.fusionfish.customitem.items;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;

import java.util.Set;

public class MagicWand extends Item {

    public MagicWand(Builder builder) {
        super(builder);
    }

    @Override
    public void addKeys(Set<ItemKey> keySet) {

    }

    public static class Builder extends Item.Builder {

        public Builder() {
            type("MagicWand");
            name("§d魔杖");
            action(event -> {
                if (!(event instanceof PlayerInteractEvent)) return;
                PlayerInteractEvent interactEvent = (PlayerInteractEvent) event;
                Player player = interactEvent.getPlayer();
                player.setVelocity(new Vector(0,10,0));
                interactEvent.setCancelled(true);
            });
        }

        @Override
        public void getKeys(PersistentDataContainer container) {

        }

        @Override
        public MagicWand build() {
            return new MagicWand(this);
        }
    }
}
