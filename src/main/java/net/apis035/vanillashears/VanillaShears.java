package net.apis035.vanillashears;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VanillaShears implements ModInitializer {
    public static final String MOD_ID = "vanillashears";

    public static final Item WOODEN_SHEARS = new ModShearsItem(new Item.Settings(), ToolMaterials.WOOD);
    public static final Item STONE_SHEARS = new ModShearsItem(new Item.Settings(), ToolMaterials.STONE);
    public static final Item GOLDEN_SHEARS = new ModShearsItem(new Item.Settings(), ToolMaterials.GOLD);
    public static final Item DIAMOND_SHEARS = new ModShearsItem(new Item.Settings(), ToolMaterials.DIAMOND);
    public static final Item NETHERITE_SHEARS = new ModShearsItem(new Item.Settings(), ToolMaterials.NETHERITE);

    public static final Tag<Item> SHEARS = TagFactory.ITEM.create(new Identifier("c", "shears"));

    public static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }

    @Override
    public void onInitialize() {
        register("wooden_shears", WOODEN_SHEARS);
        register("stone_shears", STONE_SHEARS);
        register("golden_shears", GOLDEN_SHEARS);
        register("diamond_shears", DIAMOND_SHEARS);
        register("netherite_shears", NETHERITE_SHEARS);
    }
}
