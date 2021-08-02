package com.oneshotmc.enchants.enchants.tools;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import java.util.ArrayList;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

final class SkillingListener implements Listener {
    List<SkillType> effectedSkills = new ArrayList<>(SkillType.GATHERING_SKILLS);

    @EventHandler
    public void onMcMMOPlayerXpGain(McMMOPlayerXpGainEvent e) {
        if (this.effectedSkills.contains(e.getSkill())) {
            Player p = e.getPlayer();
            if (p.getItemInHand() != null && OneShotEnchants.itemHasEnchantment(p.getItemInHand(), "Skilling")) {
                int enchLevel = ((Integer)OneShotEnchants.getEnchantments(p.getItemInHand()).get(OneShotEnchants.getEnchantment("Skilling"))).intValue();
                e.setRawXpGained((float)(e.getRawXpGained() * (1.0D + enchLevel * 0.04D)));
            }
        }
    }
}
