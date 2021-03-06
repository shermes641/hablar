package com.calclab.hablar.clipboard.client;

import com.calclab.hablar.chat.client.ui.PairChatPage;
import com.calclab.hablar.chat.client.ui.PairChatPresenter;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.container.PageAddedEvent;
import com.calclab.hablar.core.client.container.PageAddedHandler;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.icon.Icons;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.rooms.client.room.RoomPage;
import com.calclab.hablar.rooms.client.room.RoomPresenter;

public class HablarClipboard {

    public static final String ACTION_ID = "CopyToClipboardAction";

    private static ClipboardMessages messages;

    public static ClipboardMessages i18n() {
	return messages;
    }

    public static void setMessages(final ClipboardMessages messages) {
	HablarClipboard.messages = messages;
    }

    public HablarClipboard(final Hablar hablar) {
	final CopyToClipboardPresenter copyToClipboardPage = new CopyToClipboardPresenter(hablar.getEventBus(),
		new CopyToClipboardWidget());
	hablar.addPage(copyToClipboardPage, OverlayContainer.ROL);

	final String actionName = i18n().copyToClipboardAction();
	final String actionIcon = Icons.CLIPBOARD;

	hablar.addPageAddedHandler(new PageAddedHandler() {
	    @Override
	    public void onPageAdded(final PageAddedEvent event) {
		if (event.isType(PairChatPresenter.TYPE)) {
		    final PairChatPage chatPage = (PairChatPage) event.getPage();
		    chatPage.addAction(new SimpleAction<PairChatPage>(actionName, ACTION_ID, actionIcon) {
			@Override
			public void execute(final PairChatPage page) {
			    copyToClipboardPage.setChatMessagesProvider(page);
			    copyToClipboardPage.requestVisibility(Visibility.focused);
			}
		    });
		} else if (event.isType(RoomPresenter.TYPE)) {
		    final RoomPage roomPage = (RoomPage) event.getPage();
		    roomPage.addAction(new SimpleAction<RoomPage>(actionName, ACTION_ID, actionIcon) {
			@Override
			public void execute(final RoomPage page) {
			    copyToClipboardPage.setChatMessagesProvider(page);
			    copyToClipboardPage.requestVisibility(Visibility.focused);
			}
		    });
		}
	    }
	}, true);
    }
}
