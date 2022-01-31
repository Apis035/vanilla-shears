package net.apis035.vanillashears;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/*
 * TODO:
 *  Don't drop block's loot_table items when sheared
 *  Shearing tripwire (doesn't trigger redstone signal)
 *  Shearing pumpkin (pumpkin -> carved pumpkin)
 *  Shearing beehive (beehive lv.5 drop 3 honeycombs)
 */

public class ModShearsItem extends ShearsItem {
    public ModShearsItem(Settings settings, ToolMaterials material) {
        super(settings.group(ItemGroup.TOOLS).maxDamage(material.getDurability() - 12));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity.world.isClient)
            return ActionResult.PASS;

        // Shearing sheep
        if (entity instanceof SheepEntity sheepEntity) {
            if (sheepEntity.isShearable()) {
                sheepEntity.sheared(SoundCategory.PLAYERS);
                sheepEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
        }

        // Shearing mooshroom
        if (entity instanceof MooshroomEntity mooshroomEntity) {
            if (mooshroomEntity.isShearable()) {
                mooshroomEntity.sheared(SoundCategory.PLAYERS);
                mooshroomEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
        }

        // Shearing snow golem
        if (entity instanceof SnowGolemEntity snowGolemEntity) {
            if (snowGolemEntity.isShearable()) {
                snowGolemEntity.sheared(SoundCategory.PLAYERS);
                snowGolemEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient) {
            if (state.isIn(VanillaShears.SHEARABLE)) {
                ItemEntity drop = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), state.getBlock().asItem().getDefaultStack());
                drop.setToDefaultPickupDelay();
                world.spawnEntity(drop);
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}