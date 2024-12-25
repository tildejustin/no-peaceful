package dev.tildejustin.no_peaceful.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.LevelProperties;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin {
    @Unique
    private Difficulty cachedDifficulty = null;

    @Shadow
    private Difficulty difficulty;

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.cachedDifficulty = this.difficulty;
    }

    @ModifyExpressionValue(method = "method_19833", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/OptionsScreen;difficulty:Lnet/minecraft/world/Difficulty;", ordinal = 1, opcode = Opcodes.GETFIELD))
    private Difficulty modifyDifficulty(Difficulty difficulty) {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null || server.isRemote() || difficulty != Difficulty.PEACEFUL || Screen.hasShiftDown() || ((LevelProperties) server.getSaveProperties()).no_peaceful$isPeacefulAllowed()) {
            return difficulty;
        }
        return cachedDifficulty;
    }
}
