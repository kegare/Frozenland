package kegare.frozenland.core;

import java.io.File;

import kegare.frozenland.block.BlockIceFrozenland;
import kegare.frozenland.item.FrozenItem;
import kegare.frozenland.item.ItemAxeIce;
import kegare.frozenland.item.ItemBowIce;
import kegare.frozenland.item.ItemFrozenlandDimensionalBook;
import kegare.frozenland.item.ItemHoeIce;
import kegare.frozenland.item.ItemIceStick;
import kegare.frozenland.item.ItemPickaxeIce;
import kegare.frozenland.item.ItemSpadeIce;
import kegare.frozenland.item.ItemSwordIce;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

import com.google.common.base.Optional;

import cpw.mods.fml.common.Loader;

public class Config
{
	public static boolean versionNotify;

	public static int frozenlandDimensionalBook;
	public static int stickIce;
	public static int swordIce;
	public static int bowIce;
	public static int pickaxeIce;
	public static int axeIce;
	public static int shovelIce;
	public static int hoeIce;

	public static int dimensionFrozenland;
	public static boolean generateCaves;
	public static boolean generateRavine;
	public static boolean generateMineshaft;
	public static boolean generateVillage;
	public static boolean generateLakes;
	public static boolean generateDungeons;

	public static boolean replaceCustomIce;

	public static void buildConfiguration()
	{
		Configuration cfg = new Configuration(new File(Loader.instance().getConfigDir(), "Frozenland.cfg"));

		try
		{
			cfg.load();

			cfg.addCustomCategoryComment(Configuration.CATEGORY_ITEM, "If multiplayer, values must match on client-side and server-side.");
			cfg.addCustomCategoryComment("frozenland", "If multiplayer, server-side only.");
			cfg.addCustomCategoryComment("advanced", "You don't need to change this category settings normally.");

			versionNotify = cfg.get("general", "versionNotify", true, "Whether or not to notify when a new Frozenland version is available. [true/false]").getBoolean(true);

			frozenlandDimensionalBook = cfg.getItem("frozenlandDimensionalBook", 5030, "ItemID - Frozenland Dimensional Book").getInt(5030);
			stickIce = cfg.getItem("stickIce", 5031, "ItemID - Ice Stick").getInt(5031);
			swordIce = cfg.getItem("swordIce", 5032, "ItemID - Ice Sword").getInt(5032);
			bowIce = cfg.getItem("bowIce", 5033, "ItemID - Ice Bow").getInt(5033);
			pickaxeIce = cfg.getItem("pickaxeIce", 5034, "ItemID - Ice Pickaxe").getInt(5034);
			axeIce = cfg.getItem("axeIce", 5035, "ItemID - Ice Axe").getInt(5035);
			shovelIce = cfg.getItem("shovelIce", 5036, "ItemID - Ice Shovel").getInt(5036);
			hoeIce = cfg.getItem("hoeIce", 5037, "ItemID - Ice Hoe").getInt(5037);

			dimensionFrozenland = cfg.get("frozenland", "dimensionFrozenland", -30, "DimensionID - Frozenland").getInt(-30);
			generateCaves = cfg.get("frozenland", "generateCaves", true, "Whether or not to generate caves to Frozenland. [true/false]").getBoolean(true);
			generateRavine = cfg.get("frozenland", "generateRavine", true, "Whether or not to generate ravine to Frozenland. [true/false]").getBoolean(true);
			generateMineshaft = cfg.get("frozenland", "generateMineshaft", true, "Whether or not to generate mineshaft to Frozenland. [true/false]").getBoolean(true);
			generateVillage = cfg.get("frozenland", "generateVillage", true, "Whether or not to generate village to Frozenland. [true/false]").getBoolean(true);
			generateLakes = cfg.get("frozenland", "generateLakes", true, "Whether or not to generate lakes to Frozenland. [true/false]").getBoolean(true);
			generateDungeons = cfg.get("frozenland", "generateDungeons", true, "Whether or not to generate dungeons to Frozenland. [true/false]").getBoolean(true);

			replaceCustomIce = cfg.get("advanced", "replaceCustomIce", true).getBoolean(true);
		}
		finally
		{
			if (cfg.hasChanged())
			{
				cfg.save();
			}

			initialize();
		}
	}

	private static void initialize()
	{
		FrozenItem.toolIce.customCraftingMaterial = Item.itemsList[Block.ice.blockID];

		if (frozenlandDimensionalBook > 0) FrozenItem.frozenlandDimensionalBook = Optional.of((Item)new ItemFrozenlandDimensionalBook(frozenlandDimensionalBook, "frozenlandDimensionalBook"));
		if (stickIce > 0) FrozenItem.stickIce = Optional.of((Item)new ItemIceStick(stickIce, "stickIce"));
		if (swordIce > 0) FrozenItem.swordIce = Optional.of((Item)new ItemSwordIce(swordIce, "swordIce"));
		if (bowIce > 0) FrozenItem.bowIce = Optional.of((Item)new ItemBowIce(bowIce, "bowIce"));
		if (pickaxeIce > 0) FrozenItem.pickaxeIce = Optional.of((Item)new ItemPickaxeIce(pickaxeIce, "pickaxeIce"));
		if (axeIce > 0) FrozenItem.axeIce = Optional.of((Item)new ItemAxeIce(axeIce, "axeIce"));
		if (shovelIce > 0) FrozenItem.shovelIce = Optional.of((Item)new ItemSpadeIce(shovelIce, "shovelIce"));
		if (hoeIce > 0) FrozenItem.hoeIce = Optional.of((Item)new ItemHoeIce(hoeIce, "hoeIce"));

		if (replaceCustomIce)
		{
			Block.blocksList[Block.ice.blockID] = null;
			Block.blocksList[Block.ice.blockID] = new BlockIceFrozenland(Block.ice.blockID);
		}
	}
}