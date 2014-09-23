package com.FaustGames.Core.Controllers;

import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Mathematics.Vertex;

public interface IPointerController {
    void setScene(Scene scene);
    void move(Vertex pointerPosition);
    void down(Vertex pointerPosition);
    void up(Vertex pointerPosition);
    void cancel(Vertex pointerPosition);
    void leave(Vertex pointerPosition);
}
