package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;

public class CallCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String GROUP_MESSAGE_EMPTY = """
            Пока нет никаких групп. Чтобы добавить группу, напиши /group_create и укажи название группы через пробел, например:
            /group_create TechSupport
             для создания группы с названием 'TechSupport'""";

    public final static String CALL_GROUP_MESSAGE_NOT_FOUND = " ⚠ группа не найдена";


    public CallCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            List<UserGroup> userGroups = userGroupService.findAllByChatId(chatId);
            if (CollectionUtils.isEmpty(userGroups)) {
                sendBotMessageService.sendMessage(chatId, GROUP_MESSAGE_EMPTY);
            } else {
                sendBotMessageService.sendMessage(chatId, "Чтобы вызвать группу пользователей, введи /call и имя группы, например:\n/call TechSupport");
//                SendMessage message = new SendMessage();
//                message.setChatId(chatId.toString());
//                message.setText("Вызываю");
//                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//                InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//                InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
//                inlineKeyboardButton1.setText("Тык");
//                inlineKeyboardButton1.setCallbackData("/call");
//                inlineKeyboardButton2.setText("Тык2");
//                inlineKeyboardButton2.setCallbackData("/call QA");
//                List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//                List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//                keyboardButtonsRow1.add(inlineKeyboardButton1);
//                keyboardButtonsRow2.add(inlineKeyboardButton2);
//                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//                rowList.add(keyboardButtonsRow1);
//                rowList.add(keyboardButtonsRow2);
//                inlineKeyboardMarkup.setKeyboard(rowList);
//                message.setReplyMarkup(inlineKeyboardMarkup);
//                sendBotMessageService.sendMessage(message);

            }
        } else if (getMessage(update).split(Constant.REGEX_GROUP).length > 1) {
            String[] groupTitles = getMessage(update).split(Constant.REGEX_GROUP);
            for (int i = 1; i < groupTitles.length; i++) {
                if (userGroupService.isExistGroup(chatId, groupTitles[i])) {
                    UserGroup userGroup = userGroupService.findByTitle(chatId, groupTitles[i]);
                    sendBotMessageService.sendMessage(chatId, userGroup.getTitle() + " \uD83D\uDD0A " + userGroup.getUsers());
                } else {
                    sendBotMessageService.sendMessage(chatId, groupTitles[i] + CALL_GROUP_MESSAGE_NOT_FOUND);
                }
            }
        }
    }
}
