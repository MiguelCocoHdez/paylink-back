package com.paylink.user.application.port.in;


public interface DeleteContactUseCase {

	void deleteContact(Long userId, Long contactUserId);
}
