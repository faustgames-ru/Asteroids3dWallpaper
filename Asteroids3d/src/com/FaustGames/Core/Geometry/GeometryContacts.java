package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.IVisitor;

import java.util.ArrayList;

public class GeometryContacts {
    ArrayList<GeometryContact> _contacts = new ArrayList<GeometryContact>();
    int _count;

    GeometryContact addContact(){
        if (_count >= _contacts.size())
            _contacts.add(new GeometryContact());
        return _contacts.get(_count++);
    }

    public void clear(){
        _count = 0;
    }

    public void add(IGeometryTreeItem a, IGeometryTreeItem b){
        GeometryContact contact = addContact();
        contact.setA(a);
        contact.setB(b);
    }

    public void visit(IVisitor<GeometryContact> contactVisitor){
        for(int i= 0; i < _count; i++)
            contactVisitor.visit(_contacts.get(i));
    }
}
