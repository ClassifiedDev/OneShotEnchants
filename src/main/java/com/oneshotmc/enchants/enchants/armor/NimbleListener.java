package com.oneshotmc.enchants.enchants.armor;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

final class NimbleListener implements Listener {
    List<SkillType> effectedSkills = new ArrayList<>(Arrays.asList(new SkillType[] { SkillType.ACROBATICS }));

    @EventHandler
    public void onMcMMOPlayerXpGain(McMMOPlayerXpGainEvent e) {
        if (this.effectedSkills.contains(e.getSkill())) {
            Player p = e.getPlayer();
            if (p.getEquipment().getBoots() != null && OneShotEnchants.itemHasEnchantment(p.getEquipment().getBoots(), "Nimble")) {
                int enchLevel = ((Integer)OneShotEnchants.getEnchantments(p.getEquipment().getBoots()).get(OneShotEnchants.getEnchantment("Nimble"))).intValue();
                e.setRawXpGained((float)(e.getRawXpGained() * (1.0D + enchLevel * 0.1D)));
            }
        }
    }
}
