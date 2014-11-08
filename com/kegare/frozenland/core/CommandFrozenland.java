package com.kegare.frozenland.core;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.kegare.frozenland.util.Version;

import cpw.mods.fml.common.Loader;

public class CommandFrozenland implements ICommand
{
	@Override
	public int compareTo(Object obj)
	{
		return getCommandName().compareTo(((ICommand)obj).getCommandName());
	}

	@Override
	public String getCommandName()
	{
		return "frozenland";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		throw new CommandNotFoundException();
	}

	@Override
	public List getCommandAliases()
	{
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length <= 0 || args[0].equalsIgnoreCase("version"))
		{
			ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, Frozenland.metadata.url);
			IChatComponent component;
			IChatComponent message = new ChatComponentText(" ");

			component = new ChatComponentText("Frozenland");
			component.getChatStyle().setColor(EnumChatFormatting.AQUA);
			message.appendSibling(component);
			message.appendText(" " + Version.getCurrent());

			if (Version.DEV_DEBUG)
			{
				message.appendText(" ");
				component = new ChatComponentText("dev");
				component.getChatStyle().setColor(EnumChatFormatting.RED);
				message.appendSibling(component);
			}

			message.appendText(" for " + Loader.instance().getMCVersionString() + " ");
			component = new ChatComponentText("(Latest: " + Version.getLatest() + ")");
			component.getChatStyle().setColor(EnumChatFormatting.GRAY);
			message.appendSibling(component);
			message.getChatStyle().setChatClickEvent(click);
			sender.addChatMessage(message);

			message = new ChatComponentText("  ");
			component = new ChatComponentText(Frozenland.metadata.description);
			component.getChatStyle().setChatClickEvent(click);
			message.appendSibling(component);
			sender.addChatMessage(message);

			message = new ChatComponentText("  ");
			component = new ChatComponentText(Frozenland.metadata.url);
			component.getChatStyle().setColor(EnumChatFormatting.DARK_GRAY).setChatClickEvent(click);
			message.appendSibling(component);
			sender.addChatMessage(message);
		}
		else if (args[0].equalsIgnoreCase("forum") || args[0].equalsIgnoreCase("url"))
		{
			try
			{
				Desktop.getDesktop().browse(new URI(Frozenland.metadata.url));
			}
			catch (Exception e) {}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return sender instanceof MinecraftServer || sender instanceof EntityPlayerMP;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, "version", "forum") : null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}
}