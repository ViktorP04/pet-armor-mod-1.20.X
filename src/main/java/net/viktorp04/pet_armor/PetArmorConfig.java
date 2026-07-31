package net.viktorp04.pet_armor;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public final class PetArmorConfig {
	/** Retains the misspelled legacy key for compatibility with the 1.20 configuration. */
	private static final String DURABILITY_KEY = "armorLooseDurability";
	private static boolean armorLosesDurability = true;

	private PetArmorConfig() {
	}

	public static void load() {
		Path configPath = FabricLoader.getInstance().getConfigDir().resolve("pet-armor.properties");
		Properties properties = new Properties();
		if (Files.exists(configPath)) {
			try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
				properties.load(reader);
			} catch (IOException exception) {
				PetArmor.LOGGER.error("Could not read {}", configPath, exception);
				return;
			}

			String value = properties.getProperty(DURABILITY_KEY);
			if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
				armorLosesDurability = Boolean.parseBoolean(value);
			} else if (value != null) {
				PetArmor.LOGGER.warn("Invalid {} value '{}'; using true", DURABILITY_KEY, value);
			}
			return;
		}

		properties.setProperty(DURABILITY_KEY, Boolean.toString(armorLosesDurability));
		try {
			Files.createDirectories(configPath.getParent());
			try (Writer writer = Files.newBufferedWriter(
				configPath,
				StandardCharsets.UTF_8,
				StandardOpenOption.CREATE_NEW
			)) {
				properties.store(writer, "Set to false to prevent equipped Pet Armor from losing durability.");
			}
		} catch (IOException exception) {
			PetArmor.LOGGER.error("Could not create {}", configPath, exception);
		}
	}

	public static boolean armorLosesDurability() {
		return armorLosesDurability;
	}
}
