package cn.fusionfish.customitem.manager;

import cn.fusionfish.customitem.items.Item;
import com.google.common.collect.Maps;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

import static cn.fusionfish.customitem.items.Item.ITEM_UUID;

public class ItemManager {

    private final Map<UUID, Item> itemMap = Maps.newHashMap();

    private Item createItem(ItemStack itemStack) {
        Item.Builder builder = Item.getBuilder(itemStack);
        Item item = builder
                .fromItem(itemStack)
                .build();
        UUID uuid = item.getUuid();
        itemMap.put(uuid, item);
        return item;
    }

    public Item get(ItemStack itemStack) {
        UUID uuid = getUUID(itemStack);
        if (itemMap.containsKey(uuid)) {
            return itemMap.get(uuid);
        } else {
            return createItem(itemStack);
        }
    }

    private UUID getUUID(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String uuid = container.get(ITEM_UUID, PersistentDataType.STRING);
        assert uuid != null;
        return UUID.fromString(uuid);
    }
}
