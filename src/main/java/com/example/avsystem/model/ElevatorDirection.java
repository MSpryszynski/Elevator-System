package com.example.avsystem.model;

public enum ElevatorDirection {

    UP {
        @Override
        public int toInt() {
            return 1;
        }
    },
    DOWN {
        @Override
        public int toInt() {
            return -1;
        }
    },
    NONE;

    public int toInt() {
        return 0;
    }
}
