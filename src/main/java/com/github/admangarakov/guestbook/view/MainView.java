package com.github.admangarakov.guestbook.view;

import com.github.admangarakov.guestbook.domain.MessageUI;
import com.github.admangarakov.guestbook.services.MessageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.List;
import java.util.stream.Collectors;

@Route
public class MainView extends VerticalLayout {
    private final MessageService messageService;

    private final PaginatedGrid<MessageUI> grid = new PaginatedGrid<>();
    private final Form form;

    @Autowired
    public MainView(MessageService messageService) {
        this.messageService = messageService;

        form = new Form(messageService);
        form.setChangeHandler(this::showMsg);

        grid.addColumn(MessageUI::getUsername).setHeader("Username").setSortable(true);
        grid.addColumn(MessageUI::getEmailAddress).setHeader("Email address").setSortable(true);
        grid.addColumn(MessageUI::getHomepage).setHeader("Homepage");
        grid.addColumn(MessageUI::getText).setHeader("Message");
        grid.addColumn(MessageUI::getCreationDate).setHeader("Creation date").setSortable(true);
        grid.setPageSize(25);
        grid.setPaginatorSize(5);
        add(form, grid);
        showMsg();
    }

    private void showMsg() {
        List<MessageUI> msgsUI = messageService.findAll().stream()
                .map(MessageUI::new)
                .collect(Collectors.toList());
        grid.setItems(msgsUI);
    }
}
