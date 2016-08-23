package me.nentify.servershops;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public class Utils {

    public static ItemStack createItemWithMeta(ItemType type, int meta) {
        ItemStack stackWithoutMeta = ItemStack.builder()
                .itemType(type)
                .build();

        DataContainer container = stackWithoutMeta.toContainer();
        container.set(DataQuery.of("UnsafeDamage"), meta);

        return ItemStack.builder()
                .fromContainer(container)
                .build();
    }
}
