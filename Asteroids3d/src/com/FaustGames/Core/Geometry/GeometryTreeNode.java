package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.IVisitor;
import com.FaustGames.Core.Mathematics.Vertex;

import java.util.ArrayList;

public class GeometryTreeNode {
    Bounds _bounds;
    GeometryTreeNode[] _childs;
    ArrayList<IGeometryTreeItem> _items = new ArrayList<IGeometryTreeItem>();
    GeometryTreeNode _parent;
    int _itemsCount = 0;

    public int Id;

    public GeometryTreeNode(GeometryTreeNode parent, Bounds bounds, int depth){
        _parent = parent;
        _bounds = bounds;
        if (depth == 0) return;
        Vertex size = _bounds.getSize();
        Bounds[] childBounds;
        if (size.getX() > size.getY()) {
            childBounds = size.getX() > size.getZ()
                    ? _bounds.divideX()
                    : _bounds.divideZ();
        }
        else
        {
            childBounds = size.getY() > size.getZ()
                    ?_bounds.divideY()
                    :_bounds.divideZ();
        }
        _childs = new GeometryTreeNode[]
        {
            new GeometryTreeNode(this, childBounds[0], depth - 1),
            new GeometryTreeNode(this, childBounds[1], depth - 1)
        };
    }

    public void add(IGeometryTreeItem item){
        _items.add(item);
    }

    public void remove(IGeometryTreeItem item){
        _items.remove(item);
    }

    public GeometryTreeNode GetNode(Bounds bounds){
        if (!_bounds.contains(bounds)) return null;
        if (_childs != null) {
            GeometryTreeNode child0 = _childs[0].GetNode(bounds);
            if (child0 != null) return child0;
            GeometryTreeNode child1 = _childs[1].GetNode(bounds);
            if (child1 != null) return child1;
        }
        return this;
    }

    public int getItemsCount(){
        return _itemsCount;
    }

    public void incItemsCount(){
        _itemsCount++;
        if (_parent != null)
            _parent.incItemsCount();
    }

    public void decItemsCount(){
        _itemsCount--;
        if (_parent != null)
            _parent.decItemsCount();
    }

    public int fillContacts(FillContactsArgs args){
        if (getItemsCount() == 0) return 0;
        int result = 0;
        if (_childs != null) {
            boolean hasItems = _items.size() > 0;
            if (hasItems)
                args.getStack().push(this);
            result += _childs[0].fillContacts(args);
            result += _childs[1].fillContacts(args);
            if (hasItems)
                args.getStack().pop();
        }

        for(int i = 0, size = _items.size(); i < size; i++){
            IGeometryTreeItem item0 = _items.get(i);
            for(int j = i+1; j < size; j++){
                IGeometryTreeItem item1 = _items.get(j);
                if (Bounds.cross(item0.getBounds(), item1.getBounds())){
                    args.getContacts().add(item0, item1);
                }
                result++;
            }
            /*
            GeometryTreeNodesStack stack = args.getStack();
            for (int k = 0, stackSize = stack.getSize(); k < stackSize; k++)
            {
                GeometryTreeNode parent = stack.get(k);
                for(int j = 0, parentSize = parent._items.size(); j < parentSize ; j++){
                    IGeometryTreeItem item1 = parent._items.get(j);
                    if (Bounds.cross(item0.getBounds(), item1.getBounds())){
                        args.getContacts().add(item0, item1);
                    }
                    result++;
                }
            }
            */
            GeometryTreeNode parent = _parent;
            while(parent != null){
                for(int j = 0, parentSize = parent._items.size(); j < parentSize ; j++){
                    IGeometryTreeItem item1 = parent._items.get(j);
                    if (Bounds.cross(item0.getBounds(), item1.getBounds())){
                        args.getContacts().add(item0, item1);
                    }
                    result++;
                }
                parent = parent._parent;
            }

        }
        return result;
    }

    public void visit(Bounds bounds, IVisitor<GeometryTreeNode> nodeVisitor){
        if (!_bounds.contains(bounds)) return;
        nodeVisitor.visit(this);
        if (_childs == null) return;
        _childs[0].visit(nodeVisitor);
        _childs[1].visit(nodeVisitor);
    }

    public void visit(IVisitor<GeometryTreeNode> nodeVisitor){
        nodeVisitor.visit(this);
        if (_childs == null) return;
        _childs[0].visit(nodeVisitor);
        _childs[1].visit(nodeVisitor);
    }
}
