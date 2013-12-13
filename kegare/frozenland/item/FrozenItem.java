package kegare.frozenland.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.google.common.base.Optional;

import cpw.mods.fml.common.registry.GameRegistry;

public class FrozenItem
{
	public static final EnumToolMaterial toolIce = EnumHelper.addToolMaterial("ice", 2, 120, 6.5F, 1.0F, 10);

	public static Optional<Item> frozenlandDimensionalBook = Optional.absent();
	public static Optional<Item> stickIce = Optional.absent();
	public static Optional<Item> swordIce = Optional.absent();
	public static Optional<Item> bowIce = Optional.absent();
	public static Optional<Item> pickaxeIce = Optional.absent();
	public static Optional<Item> axeIce = Optional.absent();
	public static Optional<Item> shovelIce = Optional.absent();
	public static Optional<Item> hoeIce = Optional.absent();

	public static void configure()
	{
		Item item = null;

		if (frozenlandDimensionalBook.isPresent())
		{
			item = frozenlandDimensionalBook.get();

			GameRegistry.registerItem(item, "frozenlandDimensionalBook");

			GameRegistry.addRecipe(new ItemStack(item),
					new Object[]
						{
							" I ",
							"IEI",
							" B ",
							Character.valueOf('I'), Block.ice,
							Character.valueOf('E'), Item.enderPearl,
							Character.valueOf('B'), Item.book
						});

			OreDictionary.registerOre("frozenlandDimensionalBook", item);
		}

		if (stickIce.isPresent())
		{
			item = stickIce.get();

			GameRegistry.registerItem(item, "stickIce");

			GameRegistry.addRecipe(new ItemStack(item, 4),
					new Object[]
						{
							"I",
							"I",
							Character.valueOf('I'), Block.ice
						});

			OreDictionary.registerOre("stickIce", item);
		}

		if (swordIce.isPresent())
		{
			item = swordIce.get();

			GameRegistry.registerItem(item, "swordIce");

			GameRegistry.addRecipe(new ShapedOreRecipe(item,
					new Object[]
						{
							"I",
							"I",
							"S",
							Character.valueOf('I'), Block.ice,
							Character.valueOf('S'), "stickIce"
						}));

			OreDictionary.registerOre("swordIce", item);
		}

		if (bowIce.isPresent())
		{
			item = bowIce.get();

			GameRegistry.registerItem(item, "bowIce");

			GameRegistry.addRecipe(new ShapedOreRecipe(item,
					new Object[]
						{
							" IS",
							"I S",
							" IS",
							Character.valueOf('I'), "stickIce",
							Character.valueOf('S'), Item.silk
						}));

			OreDictionary.registerOre("bowIce", item);
		}

		if (pickaxeIce.isPresent())
		{
			item = pickaxeIce.get();

			GameRegistry.registerItem(item, "pickaxeIce");

			GameRegistry.addRecipe(new ShapedOreRecipe(item,
					new Object[]
						{
							"III",
							" S ",
							" S ",
							Character.valueOf('I'), Block.ice,
							Character.valueOf('S'), "stickIce"
						}));

			OreDictionary.registerOre("pickaxeIce", item);

			MinecraftForge.setToolClass(item, "pickaxe", 2);
		}

		if (axeIce.isPresent())
		{
			item = axeIce.get();

			GameRegistry.registerItem(item, "axeIce");

			GameRegistry.addRecipe(new ShapedOreRecipe(item,
					new Object[]
						{
							"II",
							"IS",
							" S",
							Character.valueOf('I'), Block.ice,
							Character.valueOf('S'), "stickIce"
						}));

			OreDictionary.registerOre("axeIce", item);

			MinecraftForge.setToolClass(item, "axe", 2);
		}

		if (shovelIce.isPresent())
		{
			item = shovelIce.get();

			GameRegistry.registerItem(item, "shovelIce");

			GameRegistry.addRecipe(new ShapedOreRecipe(item,
					new Object[]
						{
							"I",
							"S",
							"S",
							Character.valueOf('I'), Block.ice,
							Character.valueOf('S'), "stickIce"
						}));

			OreDictionary.registerOre("shovelIce", item);

			MinecraftForge.setToolClass(item, "shovel", 2);
		}

		if (hoeIce.isPresent())
		{
			item = hoeIce.get();

			GameRegistry.registerItem(item, "hoeIce");

			GameRegistry.addRecipe(new ShapedOreRecipe(item,
					new Object[]
						{
							"II",
							" S",
							" S",
							Character.valueOf('I'), Block.ice,
							Character.valueOf('S'), "stickIce"
						}));

			OreDictionary.registerOre("hoeIce", item);
		}
	}
}