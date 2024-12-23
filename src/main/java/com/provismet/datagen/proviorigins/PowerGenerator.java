package com.provismet.datagen.proviorigins;

import com.provismet.datagen.proviorigins.provider.POPowerProvider;
import com.provismet.proviorigins.ProviOriginsMain;
import com.provismet.proviorigins.content.particles.effects.FlowerParticleEffect;
import com.provismet.proviorigins.content.registries.POSounds;
import com.provismet.proviorigins.utility.BadgeUtil;
import io.github.apace100.apoli.action.type.entity.PlaySoundEntityActionType;
import io.github.apace100.apoli.action.type.entity.SpawnParticlesEntityActionType;
import io.github.apace100.apoli.action.type.entity.meta.SequenceEntityActionType;
import io.github.apace100.apoli.power.type.ActionOnKeyPressPowerType;
import io.github.apace100.apoli.power.type.Active;
import io.github.apace100.apoli.power.type.ParticlePowerType;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.origins.badge.Badge;
import io.github.apace100.origins.badge.TooltipBadge;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class PowerGenerator extends POPowerProvider {
    public PowerGenerator (FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void generate (PowerCollector collector) {
        ParticlePowerType particles = new ParticlePowerType(
            Optional.empty(),
            new FlowerParticleEffect(new Vector3f(0.71f, 0.84f, 0.96f), 1),
            new Vec3d(0.25, 0.25, 0.25),
            new Vec3d(0, 0.1, 0),
            6,
            1,
            0,
            false,
            false,
            false,
            Optional.empty()
        );

        ActionOnKeyPressPowerType propulsion = new ActionOnKeyPressPowerType(
            new SequenceEntityActionType(
                List.of(
                    new SpawnParticlesEntityActionType(
                        Optional.empty(),
                        ParticleTypes.SQUID_INK,
                        Vec3d.ZERO,
                        Vec3d.ZERO,
                        false,
                        0.25f,
                        30
                    ).createAction(),
                    new PlaySoundEntityActionType(
                        POSounds.KRAKEN_INK,
                        Optional.of(SoundCategory.PLAYERS),
                        1f,
                        1f
                    ).createAction()
                )
            ).createAction(),
            HudRender.DONT_RENDER,
            1,
            new Active.Key(),
            Optional.empty()
        );
        Badge badge1 = new TooltipBadge(Identifier.of("origins", "textures/gui/badge/active.png"), Text.translatable("origins.gui.badge.active"));
        Badge badge2 = BadgeUtil.cost(Text.translatable("power.proviorigins.decaykraken/propulsion.badge"));

        collector.add(ProviOriginsMain.identifier("common/propel"), propulsion, List.of(badge1, badge2));
    }

    private void common (String name, PowerType powerType, PowerCollector collector) {
        collector.add(ProviOriginsMain.identifier("common/" + name), powerType);
    }

    private void alraune (String name, PowerType powerType, PowerCollector collector) {
        collector.add(ProviOriginsMain.identifier("alraune/" + name), powerType);
    }

    private void decaykraken (String name, PowerType powerType, PowerCollector collector) {
        collector.add(ProviOriginsMain.identifier("decaykraken/" + name), powerType);
    }
}
