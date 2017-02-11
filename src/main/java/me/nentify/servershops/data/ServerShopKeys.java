package me.nentify.servershops.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.reflect.TypeToken;

import me.nentify.servershops.ServerShops;

public class ServerShopKeys {

//	public static final Key<Value<String>> SHOP_TYPES = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>() {}, DataQuery.of("ShopType"), ServerShops.PLUGIN_ID+":shop_type", name);
	
    public static final Key<Value<String>> SHOP_TYPE = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>() {}, DataQuery.of("ShopType"), ServerShops.PLUGIN_ID+":shop_type","shop_type");
    public static final Key<Value<ItemStack>> ITEM = KeyFactory.makeSingleKey(TypeToken.of(ItemStack.class), new TypeToken<Value<ItemStack>>() {}, DataQuery.of("ItemStack"), ServerShops.PLUGIN_ID+":itemstack","itemstack");
    public static final Key<Value<Double>> PRICE = KeyFactory.makeSingleKey(TypeToken.of(Double.class), new TypeToken<Value<Double>>() {}, DataQuery.of("Price"), ServerShops.PLUGIN_ID+":price","price");
    public static final Key<Value<Integer>> QUANTITY = KeyFactory.makeSingleKey(TypeToken.of(Integer.class), new TypeToken<Value<Integer>>() {}, DataQuery.of("Quantity"), ServerShops.PLUGIN_ID+":quantity","quantity");
	
//    public static final Key<Value<String>> SHOP_TYPE = KeyFactory.makeSingleKey(String.class, Value.class, DataQuery.of("ShopType"));
//    public static final Key<Value<ItemStack>> ITEM = KeyFactory.makeSingleKey(ItemStack.class, Value.class, DataQuery.of("ItemStack"));
//    public static final Key<Value<Double>> PRICE = KeyFactory.makeSingleKey(Double.class, Value.class, DataQuery.of("Price"));
//    public static final Key<Value<Integer>> QUANTITY = KeyFactory.makeSingleKey(Integer.class, Value.class, DataQuery.of("Quantity"));
}
