package spring.boot.module.chat.utils;

import spring.boot.module.chat.dto.MessageDTO;

public class SystemMessages {
	
	public static final MessageDTO welcome(Long chatRoomId, String username) {
		MessageDTO messageDTO = new MessageDTO();

		return messageDTO;
	}

	public static final MessageDTO goodbye(Long chatRoomId, String username) {
		MessageDTO messageDTO = new MessageDTO();

		return messageDTO;
	}
}
