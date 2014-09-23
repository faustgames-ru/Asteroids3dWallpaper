package com.FaustGames.Core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.FaustGames.Core.Entities.Scene;

import javax.microedition.khronos.egl.*;

public class BasicRenderView extends GLSurfaceView implements IUpdatable {
    BasicRenderer renderer;
    public BasicRenderView(Context context, Scene scene) {
        super(context);
        renderer = new BasicRenderer(context, scene);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
/*
        setEGLContextFactory(new EGLContextFactory() {
            @Override
            public EGLContext createContext(EGL10 egl10, EGLDisplay eglDisplay, EGLConfig eglConfig) {
                final int EGL_CONTEXT_CLIENT_VERSION = 0x3098; /// ??????
                int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE};
                renderer.RenderContext = egl10.eglCreateContext(eglDisplay, eglConfig, egl10.EGL_NO_CONTEXT, attrib_list);
                return renderer.RenderContext;
            }

            @Override
            public void destroyContext(EGL10 egl10, EGLDisplay eglDisplay, EGLContext eglContext) {
                egl10.eglDestroyContext(eglDisplay, eglContext);
            }
        });

        setEGLWindowSurfaceFactory(new EGLWindowSurfaceFactory() {
            @Override
            public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eglDisplay, EGLConfig eglConfig, Object o) {
                renderer.EGLSurface = egl10.eglCreateWindowSurface(eglDisplay, eglConfig, o, null);

                return renderer.EGLSurface;
            }

            @Override
            public void destroySurface(EGL10 egl10, EGLDisplay eglDisplay, EGLSurface eglSurface) {
                EGL10 egl = (EGL10)EGLContext.getEGL();
                EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
                egl.eglMakeCurrent(display, renderer.EGLSurface, renderer.EGLSurface, renderer.RenderContext);
                renderer.destroyContext();
                egl.eglMakeCurrent(display, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);

                egl10.eglDestroySurface(eglDisplay, eglSurface);
            }
        });
*/
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);

        //setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return renderer.onTouchEvent(event);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }

    public void forceDestroyRenderer(){
        renderer.destroy();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void update(float timeDelta) {
        renderer.update(timeDelta);
    }
}
