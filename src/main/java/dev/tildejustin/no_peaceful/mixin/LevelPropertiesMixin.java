package dev.tildejustin.no_peaceful.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.tildejustin.no_peaceful.LevelPropertiesInterface;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements LevelPropertiesInterface {
    @Unique
    private boolean peacefulAllowed;

    @Inject(method = "<init>(Lcom/mojang/datafixers/DataFixer;ILnet/minecraft/nbt/CompoundTag;ZIIIJJIIIZIZZZLnet/minecraft/world/border/WorldBorder$Properties;IILjava/util/UUID;Ljava/util/LinkedHashSet;Lnet/minecraft/world/timer/Timer;Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/world/gen/GeneratorOptions;Lcom/mojang/serialization/Lifecycle;)V", at = @At("TAIL"))
    private void initPeacefulAllowed(CallbackInfo ci, @Local(argsOnly = true) LevelInfo levelInfo) {
        peacefulAllowed = levelInfo.getDifficulty() == Difficulty.PEACEFUL;
    }

    @Inject(method = "setDifficulty", at = @At("TAIL"))
    private void updatePeacefulAllowed(Difficulty difficulty, CallbackInfo ci) {
        if (difficulty == Difficulty.PEACEFUL) {
            System.out.println("Changed difficulty to peaceful");
            this.peacefulAllowed = true;
        }
    }

    @Override
    public boolean no_peaceful$isPeacefulAllowed() {
        return this.peacefulAllowed;
    }
}
