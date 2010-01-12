package com.calclab.hablar.client.chat;

import com.calclab.emite.core.client.xmpp.stanzas.Message;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.suco.client.events.Listener;

public class ChatLogic {
    private final Chat chat;
    private final ChatView view;

    public ChatLogic(final Chat chat, final ChatView view) {
	this.chat = chat;
	this.view = view;

	// FIXME
	// Two possible bugs:
	// 1) getNode can be null (server messages): recommended URI.toString()
	// 2) to and from can have the save node (dani talking to dani)
	// See ChatUIPresenter.104
	final String name = chat.getURI().getNode();
	view.setHeaderTitle(name);
	chat.onMessageReceived(new Listener<Message>() {
	    @Override
	    public void onEvent(final Message message) {
		view.showMessage(name, message.getBody(), ChatPage.MessageType.incoming);
		view.setStatus("Incoming message from " + name);
	    }
	});
    }

    public void onTalk(final String text) {
	view.showMessage("me", text, ChatPage.MessageType.sent);
	chat.send(new Message(text));
	view.clearAndFocus();
    }

}
