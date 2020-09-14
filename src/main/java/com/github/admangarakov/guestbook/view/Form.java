package com.github.admangarakov.guestbook.view;

import com.github.admangarakov.guestbook.converters.URLConverter;
import com.github.admangarakov.guestbook.domain.MessageUI;
import com.github.admangarakov.guestbook.services.MessageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.regex.Pattern;

public class Form extends VerticalLayout {
    private final static Logger logger = LoggerFactory.getLogger(Form.class);

    private final Binder<MessageUI> binder = new Binder<>(MessageUI.class);
    private final WebBrowser webBrowser = VaadinSession.getCurrent().getBrowser();

    private final EmailField email = new EmailField();
    private final TextField username = new TextField();
    private final TextField homepage = new TextField();
    private final TextArea text = new TextArea();
    private final TextField captchaField = new TextField();
    private final Image cptImg = new Image("img/captcha.jpg", "Captcha");
    private ChangeHandler changeHandler;

    private final MessageService messageService;
    private MessageUI msg;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public Form(MessageService messageService) {
        this.messageService = messageService;
        FormLayout columnLayout = new FormLayout();
        columnLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 2));
        email.setPlaceholder("Email");
        username.setPlaceholder("Username");
        homepage.setPlaceholder("Link to homepage");
        text.setPlaceholder("Enter a text");
        captchaField.setPlaceholder("Enter captcha");
        columnLayout.add(username, email);
        columnLayout.add(homepage, 2);
        columnLayout.add(text, 2);
        columnLayout.add(cptImg, 1);


        columnLayout.add(captchaField, 1);

        add(columnLayout);
        confBinder();

        Button saveButton = new Button("Save",
                buttonClickEvent -> {
                    msg = new MessageUI();
                    try {
                        binder.writeBean(msg);
                        msg.setCreationDate(new Date(System.currentTimeMillis()));
                        if (!captchaField.getValue().equals("smwm")) {
                            captchaField.setInvalid(true);
                            captchaField.setErrorMessage("Wrong captcha!");
                            return;
                        }
                        messageService.save(msg
                                , webBrowser.getBrowserApplication()
                                , webBrowser.getAddress());
                        logger.info("New message: {}", msg);
                    } catch (ValidationException e) {
                        logger.debug(e.getLocalizedMessage());
                    }
                    changeHandler.onChange();
                });
        add(saveButton);
    }

    private void confBinder() {
        binder.forField(username)
                .asRequired("Please, enter your username")
                .withValidator(
                        text -> {
                            String regex = "(\\w|\\d)*";
                            return (Pattern.compile(regex).matcher(text).matches());
                        }
                        , "Only letters and digits are allowed.")
                .bind(MessageUI::getUsername,
                        MessageUI::setUsername);
        binder.forField(email)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .asRequired("Please, enter your email.")
                .bind(MessageUI::getEmailAddress,
                        MessageUI::setEmailAddress);
        binder.forField(homepage)
                .withConverter(new URLConverter())
                .bind(MessageUI::getHomepage,
                        MessageUI::setHomepage);
        binder.forField(text)
                .asRequired("Please, enter your message.")
                .withValidator(
                        text -> {
                            String regex = "(\\w|\\W)*<(\"[^\"]*\"|'[^']*'|[^'\">]*)>(\\w|\\W)*";
                            return !(Pattern.compile(regex).matcher(text).matches());
                        }
                        , "The text should not include HTML tag")
                .bind(MessageUI::getText,
                        MessageUI::setText);
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }


}
