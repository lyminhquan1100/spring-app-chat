package spring.boot.module.chat.utils;

public class Destinations {

	public static class ChatRoom {
		
		public static String publicMessages(Long chatRoomId) {
			return "/topic/" + chatRoomId + ".public.messages";
		}
		
		public static String privateMessages(Long chatRoomId) {
			return "/queue/" + chatRoomId + ".private.messages";
		}
		
		public static String connectedUsers(Long chatRoomId) {
			return "/topic/" + chatRoomId + ".connected.users";
		}
	}
}
