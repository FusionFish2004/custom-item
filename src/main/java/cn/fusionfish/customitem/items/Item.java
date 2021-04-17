package cn.fusionfish.customitem.items;

import cn.fusionfish.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.function.Consumer;

/**
 * The basic item abstract class.
 * This class is the superclass of all customized item classes.
 * @author JeremyHu
 */
public abstract class Item {

    public static final NamespacedKey ITEM_TYPE = new NamespacedKey(Main.getInstance(), "item_type");
    public static final NamespacedKey ITEM_UUID = new NamespacedKey(Main.getInstance(), "item_uuid");
    public static final String ITEM_CLASS_PATH = "cn.fusionfish.customitem.items.";

    private final Material material;  //indicates which material is going to be used in the form of ItemStack.
    private final String type;  //indicates its item type.

    private final UUID uuid;  //indicates its very own unique ID in the item manager.
    private String name;  //indicated its custom name.
    private Consumer<PlayerEvent> action;  //indicates its action when the item is used.
    private final Set<ItemKey> keySet = new HashSet<>();  //indicates a set of keys which needs to be registered.


    /**
     * A static function which can check if a ItemStack is customized.
     * @param itemStack the ItemStack you are going to check
     * @return returns true if it is a customized item.
     */
    public static boolean isItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(ITEM_TYPE, PersistentDataType.STRING) && container.has(ITEM_UUID, PersistentDataType.STRING);
    }

    public Item(Builder builder) {

        this.material = builder.getMaterial();
        this.type = builder.getType();
        this.uuid = builder.getUuid();
        this.name = builder.getName();
        this.action = builder.getAction();

        keySet.add(new ItemKey(ITEM_TYPE, PersistentDataType.STRING, type));
        keySet.add(new ItemKey(ITEM_UUID, PersistentDataType.STRING, uuid.toString()));
        addKeys(keySet);
    }

    /**
     * The builder for the item.
     * Every customized item class must have a inner class extends this one,
     * or else the item cannot be created.
     */
    public abstract static class Builder {

        private Material material = Material.BLAZE_ROD;
        private String type;
        private String name;
        private UUID uuid = UUID.randomUUID();
        private Consumer<PlayerEvent> action = event -> {};

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder action(Consumer<PlayerEvent> action) {
            this.action = action;
            return this;
        }

        /**
         * Is used to get a builder instance to create a item.
         * @param itemStack the ItemStack form of your customized item.
         * @return returns the instance of the Builder of that specific type of item.
         */
        public Builder fromItem(ItemStack itemStack) {
            ItemMeta meta = itemStack.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String type = container.get(ITEM_TYPE, PersistentDataType.STRING);
            UUID uuid = UUID.fromString(container.get(ITEM_UUID, PersistentDataType.STRING));
            getKeys(container);
            type(type);
            uuid(uuid);
            name(meta.getDisplayName());
            material(itemStack.getType());
            return this;
        }

        /**
         * You can obtain all the data from this method.
         * @param container the set of NamespaceKeys which is acquired from the ItemStack.
         */
        public abstract void getKeys(PersistentDataContainer container);

        public Material getMaterial() {
            return material;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public Consumer<PlayerEvent> getAction() {
            return action;
        }

        /**
         * Builds the item.
         * @return the item.
         */
        public abstract Item build();
    }

    /**
     * A class storages all information which is used to be put in the container.
     */
    public static class ItemKey {
        private final NamespacedKey namespacedKey;
        private final PersistentDataType persistentDataType;
        private final Object value;

        public ItemKey(NamespacedKey namespacedKey, PersistentDataType<?, ?> persistentDataType, Object value) {
            this.namespacedKey = namespacedKey;
            this.persistentDataType = persistentDataType;
            this.value = value;
        }

        /**
         * Sets a value for a key in the container.
         * @param container the Persistent data container you are going to modify.
         */
        public void apply(PersistentDataContainer container) {
            container.set(namespacedKey,persistentDataType,value);
        }
    }

    private void regKeys(PersistentDataContainer container) {
        keySet.forEach(itemKey -> itemKey.apply(container));
    }

    public abstract void addKeys(Set<ItemKey> keySet);

    /**
     * @return the ItemStack form of the customized item.
     */
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        regKeys(container);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static Builder getBuilder(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String type = container.get(ITEM_TYPE, PersistentDataType.STRING);

        String classpath = ITEM_CLASS_PATH + type + "$Builder";
        try {
            Class<?> cls = Class.forName(classpath);
            return (Builder) cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Material getMaterial() {
        return material;
    }

    public String getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Consumer<PlayerEvent> getAction() {
        return action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(Consumer<PlayerEvent> action) {
        this.action = action;
    }

}
