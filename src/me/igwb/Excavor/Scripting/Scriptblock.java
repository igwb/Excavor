package me.igwb.Excavor.Scripting;

import java.util.ArrayList;

public class Scriptblock {

	private String name;
	
	private Scriptblock parent;	
	private ArrayList<Scriptblock> childs = new ArrayList<Scriptblock>();
	
	private ArrayList<Command> commands = new ArrayList<Command>();

	public Scriptblock() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scriptblock getParent() {
		return parent;
	}

	public void setParent(Scriptblock parent) {
		this.parent = parent;
	}

	public ArrayList<Scriptblock> getChilds() {
		return childs;
	}

	public void setChilds(ArrayList<Scriptblock> childs) {
		this.childs = childs;
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}
	
}
