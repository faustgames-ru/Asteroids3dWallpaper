package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.IVisitor;

import java.util.ArrayList;

public class GeometryTree {
    GeometryTreeNode _root;
    ArrayList<GeometryTreeNode> _nodes = new ArrayList<GeometryTreeNode>();
    public GeometryTree(Bounds rootBounds, int depth){
        _root = new GeometryTreeNode(null, rootBounds, depth);
        visit(new IVisitor<GeometryTreeNode>() {
            @Override
            public void visit(GeometryTreeNode node) {
                node.Id = _nodes.size();
                _nodes.add(node);
            }
        });
    }

    public void add(IGeometryTreeItem item){
        Bounds bounds = item.getBounds();
        GeometryTreeNode node = _root.GetNode(bounds);
        node.add(item);
        item.setGeometryTreeNodeId(node.Id);
        node.incItemsCount();
    }

    public void update(IGeometryTreeItem item){
        Bounds bounds = item.getBounds();
        GeometryTreeNode node = _nodes.get(item.getGeometryTreeNodeId());
        GeometryTreeNode newNode = _root.GetNode(bounds);
        if (node == newNode) return;
        node.remove(item);
        node.decItemsCount();
        newNode.add(item);
        item.setGeometryTreeNodeId(newNode.Id);
        newNode.incItemsCount();
    }

    public void remove(IGeometryTreeItem item){
        GeometryTreeNode node = _nodes.get(item.getGeometryTreeNodeId());
        node.remove(item);
        node.decItemsCount();
    }

    public int fillContacts(FillContactsArgs args){
        args.getContacts().clear();

        return _root.fillContacts(args);
    }

    public void visit(IVisitor<GeometryTreeNode> nodeVisitor){
        _root.visit(nodeVisitor);
    }
}
