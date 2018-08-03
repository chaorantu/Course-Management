package model;

import java.util.ArrayList;

public class RoomList {

	private ArrayList<Room> rooms;
	private int number;
	public RoomList(){
		rooms=new ArrayList<Room>();
	}
	public void addRoom(Room r){
		rooms.add(r);
	}
	public Room removeRoom(String roomid){
		return rooms.remove(getindex(roomid));
	}
	public Room removebyindex(int index){
		return rooms.remove(index);
	}
	
	public int getindex(String roomid){
		int i = 0,n=0;
		for(Room r:rooms){
			if(r.getRoomid().equals(roomid)){
			   n=i;
			}
			i++;
		}
		return n;
	}
	public Room getRoom(String roomid){
		for(Room r:rooms){
			if(r.getRoomid().equals(roomid)){
				return r;
			}
		}
		return null;
	}
	public ArrayList<Room> getValidRooms(Slot s){
		ArrayList<Room> Rooms=new ArrayList<Room>();
		for(Room r:rooms){
			if(r.roomAvailable(s)){
				Rooms.add(r);
			}
		}
		return Rooms;
	}
	public ArrayList<String> getValidRoomList(Slot s){
		ArrayList<String> list=new ArrayList<String>();
		for(Room r:getValidRooms(s)){
			list.add(r.getRoomid());
		}
		return list;
	}
	public ArrayList<Room> getrooms(){
		return rooms;
	}
}
