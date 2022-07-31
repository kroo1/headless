#!/bin/bash

sudo Xvfb :99 &

sudo x11vnc -display :99 -forever -rfbauth vncpwd &

sudo x11vnc -storepasswd xxx vncpwd

export DISPLAY=:99