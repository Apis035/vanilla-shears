package net.apis035.vanillashears;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.event.GameEvent;

public class ModShearsItem extends ShearsItem {
    public ModShearsItem(Settings settings, ToolMaterials material) {
        super(settings.group(ItemGroup.TOOLS).maxDamage(material.getDurability()-12));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity.world.isClient)
            return ActionResult.PASS;

        if (entity instanceof SheepEntity sheepEntity) {
            if (sheepEntity.isShearable()) {
                sheepEntity.sheared(SoundCategory.PLAYERS);
                sheepEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }
}
