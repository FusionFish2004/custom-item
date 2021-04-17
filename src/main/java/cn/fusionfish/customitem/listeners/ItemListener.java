package cn.fusionfish.customitem.listeners;

import cn.fusionfish.customitem.Main;
import cn.fusionfish.customitem.items.Item;
import cn.fusionfish.customitem.items.MagicWand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This listener listens events triggered by the execution of your customized items.
 * @author JeremyHu
 */
public class ItemListener implements Listener {

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.hasItem()) return;

        ItemStack itemStack = event.getItem();
        assert itemStack != null;
        if (!Item.isItem(itemStack)) return;
        if (player.hasCooldown(itemStack.getType())) return;

        Item item = Main.getInstance().getManager().get(itemStack);
        item.getAction().accept(event);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        MagicWand.Builder builder = new MagicWand.Builder();
        Item item = builder.build();
        player.getInventory().addItem(item.getItem());
    }
}
