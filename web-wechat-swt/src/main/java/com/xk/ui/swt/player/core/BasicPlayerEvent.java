package com.xk.ui.swt.player.core;
/*
 * BasicPlayerEvent.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */


/**
 * This class implements player events. 
 */
public class BasicPlayerEvent {

    public static final int UNKNOWN = -1;
    public static final int OPENING = 0;
    public static final int OPENED = 1;
    public static final int PLAYING = 2;
    public static final int STOPPED = 3;
    public static final int PAUSED = 4;
    public static final int RESUMED = 5;
    public static final int SEEKING = 6;
    public static final int SEEKED = 7;
    public static final int EOM = 8;
    public static final int PAN = 9;
    public static final int GAIN = 10;
    private int code = UNKNOWN;
    private int position = -1;
    private double value = -1.0;
    private Object source = null;
    private Object description = null;

    /**
     * Constructor
     * @param source of the event
     * @param code of the envent
     * @param position optional stream position
     * @param value opitional control value
     * @param desc optional description
     */
    public BasicPlayerEvent(Object source, int code, int position, double value, Object desc) {
        this.value = value;
        this.position = position;
        this.source = source;
        this.code = code;
        this.description = desc;
    }

    /**
     * Return code of the event triggered.
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * Return position in the stream when event occured.
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * Return value related to event triggered. 
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * Return description.
     * @return
     */
    public Object getDescription() {
        return description;
    }

    public Object getSource() {
        return source;
    }

    public String toString() {
        switch (code) {
            case OPENED:
                return "OPENED:" + position;
            case OPENING:
                return "OPENING:" + position + ":" + description;
            case PLAYING:
                return "PLAYING:" + position;
            case STOPPED:
                return "STOPPED:" + position;
            case PAUSED:
                return "PAUSED:" + position;
            case RESUMED:
                return "RESUMED:" + position;
            case SEEKING:
                return "SEEKING:" + position;
            case SEEKED:
                return "SEEKED:" + position;
            case EOM:
                return "EOM:" + position;
            case PAN:
                return "PAN:" + value;
            case GAIN:
                return "GAIN:" + value;
            default:
                return "UNKNOWN:" + position;
        }
    }
}
