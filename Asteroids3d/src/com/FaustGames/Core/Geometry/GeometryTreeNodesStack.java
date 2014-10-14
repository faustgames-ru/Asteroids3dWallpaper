package com.FaustGames.Core.Geometry;

public class GeometryTreeNodesStack {
    GeometryTreeNode[] _items;
    int _size = 0;

    public GeometryTreeNodesStack(int limit){
        _items = new GeometryTreeNode[limit];
    }

    public int getSize(){
        return _size;
    }

    public GeometryTreeNode get(int i){
        return _items[i];
    }

    public void clear(){
        _size = 0;
    }

    public GeometryTreeNode pop(){
        _size--;
        return _items[_size];
    }

    public void push(GeometryTreeNode node){
        _items[_size] = node;
        _size++;
    }
}
