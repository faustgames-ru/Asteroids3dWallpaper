package com.FaustGames.Core.Geometry;

public class FillContactsArgs {
    GeometryContacts _contacts;
    GeometryTreeNodesStack _stack;

    public FillContactsArgs(int stackSize){
        _stack = new GeometryTreeNodesStack(stackSize);
        _contacts = new GeometryContacts();
    }

    public GeometryContacts getContacts() {
        return _contacts;
    }

    public GeometryTreeNodesStack getStack() {
        return _stack;
    }
}
