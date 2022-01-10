package net.apis035.vanillashears;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
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

        //Add loot table to these blocks when destroyed using custom shears
        //TODO: Find a better way to do this
        Block[] drops = {
                Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES,
                Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES,
                Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES};

        for (Block drop : drops) {
            FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .with(ItemEntry.builder(drop))
                    .withCondition(new MatchToolLootCondition(ItemPredicate.Builder.create().tag(SHEARS).build()));

            LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
                if (drop.getLootTableId().equals(id)) {
                    supplier.pool(pool);
                }
            });
        }
    }
}
