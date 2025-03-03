package com.meetingroom;

import java.util.*;

public class RoomScheduler {
    private final Map<String, MeetingRoom> meetingRooms = new HashMap<>();

    public void addMeetingRoom(MeetingRoom room) {
        meetingRooms.put(room.roomId, room);
        System.out.println("Room added: " + room.roomName + ", ID: " + room.roomId);
    }

    public String bookRoom(String roomId, EnumSet<RoomFeature> requiredFeatures) {
        MeetingRoom room = meetingRooms.get(roomId);
        if (room != null && room.features.containsAll(requiredFeatures)) {
            return "Room " + roomId + " booked successfully.";
        } else {
            return "Room " + roomId + " does not meet the requirements.";
        }
    }

    public List<String> listAvailableRooms(EnumSet<RoomFeature> requiredFeatures) {
        List<String> availableRooms = new ArrayList<>();
        for (MeetingRoom room : meetingRooms.values()) {
            if (room.features.containsAll(requiredFeatures)) {
                availableRooms.add(room.roomName);
            }
        }
        return availableRooms;
    }
}
