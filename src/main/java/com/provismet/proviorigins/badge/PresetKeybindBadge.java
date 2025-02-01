package com.provismet.proviorigins.badge;

import com.provismet.proviorigins.registries.POBadgeFactories;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.util.keybinding.KeyBindingUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.badge.Badge;
import io.github.apace100.origins.badge.BadgeFactory;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public record PresetKeybindBadge (Identifier spriteId, String translationKey, String keyId) implements Badge {
    public static final String DEFAULT_TRANSLATION = "origins.gui.badge.active";

    public PresetKeybindBadge (SerializableData.Instance instance) {
        this(instance.getId("sprite"), instance.getString("translation"), instance.getString("keybind"));
    }

    @Override
    public boolean hasTooltip () {
        return this.translationKey != null && !this.translationKey.isEmpty() && this.keyId != null && !this.keyId.isEmpty();
    }

    @Override
    public List<TooltipComponent> getTooltipComponents (Power power, int widthLimit, float time, TextRenderer textRenderer) {
        List<TooltipComponent> tooltips = new ObjectArrayList<>();
        Text keyName = KeyBindingUtil.getLocalizedName(this.keyId);
        Text keyText = Text.translatable(this.translationKey, Text.literal("[").append(keyName).append("]"));

        if (textRenderer.getWidth(keyText) > widthLimit) {
            textRenderer.wrapLines(keyText, widthLimit)
                .stream()
                .map(OrderedTextTooltipComponent::new)
                .forEach(tooltips::add);
        }
        else {
            tooltips.add(new OrderedTextTooltipComponent(keyText.asOrderedText()));
        }
        return tooltips;
    }

    @Override
    public SerializableData.Instance toData (SerializableData.Instance instance) {
        instance.set("sprite", this.spriteId);
        instance.set("translation", this.translationKey);
        instance.set("keybind", this.keyId);
        return instance;
    }

    @Override
    public BadgeFactory getBadgeFactory () {
        return POBadgeFactories.PRESET_KEYBIND;
    }
}
