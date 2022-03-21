package com.gustyflows.patterns.structural.facade;

/*
As a developer in finance, we often build consoles - terminals that ouitputs huge amounts of data
that comes from the stock market
 */

import java.util.ArrayList;
import java.util.List;

//stores characters, a simple api for accessing array
class Buffer {
    private char[] characters;
    private int lineWidth;
    private int lineHeight;

    public Buffer(int lineWidth, int lineHeight) {
        this.lineWidth = lineWidth;
        this.lineHeight = lineHeight;
        characters = new char[lineWidth * lineHeight];
    }

    public char charAt(int x, int y) {
        return characters[y * lineWidth + x];
    }
}

//a view into the buffer at particular offsets
class Viewport {
    private final Buffer buffer;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public Viewport(Buffer buffer, int width, int height, int offsetX, int offsetY) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x, int y) {
        return buffer.charAt(x + offsetX, y + offsetY);
    }
}

//stores and displays viewports
class Console {
    private List<Viewport> viewports = new ArrayList<>();
    int width, height;

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //thats our facade - a construct that spans all low level steps
    public static Console newConsole(int width, int height) {
        Buffer buffer = new Buffer(width, height);
        Viewport viewport = new Viewport(buffer, width, height, 0, 0);
        Console console = new Console(width, height);
        console.addViewport(viewport);
        return console;
    }

    public void addViewport(Viewport viewport) {
        viewports.add(viewport);
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Viewport vp : viewports) {
                    System.out.println(vp.charAt(x, y));
                }
                System.out.println();
            }
        }
    }
}
//in most cases when a user wants to do smth simple with a console they dont really want to manually
//create a buffer, or a viewport - none of that ... they just want to make a console which has a single viewport
//matching the console, and a single buffer which matches the viewport, and want it all initiualzied..

public class FacadePatternDemo {
    public static void main(String[] args) {
        //without facade pattern - init of console is quite low level
        Buffer buffer = new Buffer(30, 20);
        Viewport viewport = new Viewport(buffer, 30, 20, 0, 0);
        Console console = new Console(30, 20);
        console.addViewport(viewport);
        console.render();
        //with facade - a simplified api over a set of subsystems - in the form of com.gustyflows.patterns.structural.facade.Console.newConsole
        // in the factory method, everything from line 88-91 would be included
        Console consoleWithFacade = Console.newConsole(30, 20);
        consoleWithFacade.render();

        //so the idea of Facade - to provide a simplified api
    }
}
