package net.qwertyle.pet_armor;

import net.fabricmc.api.ModInitializer;
import net.qwertyle.pet_armor.config.ModConfigs;
import net.qwertyle.pet_armor.item.ModItemGroups;
import net.qwertyle.pet_armor.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetArmor implements ModInitializer {

	public static final String MOD_ID = "pet-armor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModConfigs.registerConfigs();

		ModItemGroups.registerItemGroups();
		ModItems.RegisterModItems();

	}
}