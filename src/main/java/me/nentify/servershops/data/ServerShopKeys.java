package me.nentify.servershops.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

public class ServerShopKeys {

    public static final Key<Value<String>> SHOP_TYPE = KeyFactory.makeSingleKey(String.class, Value.class, DataQuery.of("ShopType"));
    public static final Key<Value<String>> ITEM_ID = KeyFactory.makeSingleKey(String.class, Value.class, DataQuery.of("ItemId"));
    public static final Key<Value<Integer>> ITEM_META = KeyFactory.makeSingleKey(Integer.class, Value.class, DataQuery.of("ItemMeta"));
    public static final Key<Value<Integer>> PRICE = KeyFactory.makeSingleKey(Integer.class, Value.class, DataQuery.of("Price"));
    public static final Key<Value<Integer>> QUANTITY = KeyFactory.makeSingleKey(Integer.class, Value.class, DataQuery.of("Quantity"));
}
