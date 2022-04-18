package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;

public class CallAllCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String GROUP_MESSAGE_EMPTY = """
            Пока нет никаких групп. Чтобы добавить группу, напиши /group_create и укажи название группы через пробел, например:
            /group_create TechSupport
             для создания группы с названием 'TechSupport'""";


    public CallAllCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {


        Long chatId;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            chatId = callbackQuery.getMessage().getChatId();
            UserGroup userGroup = userGroupService.findByTitle(chatId, callbackQuery.getData());
            sendBotMessageService.sendMessage(chatId, userGroup.getTitle() + " \uD83D\uDD0A " + userGroup.getUsers());
            return;
        }

        chatId = getChatId(update);


        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            List<UserGroup> userGroups = userGroupService.findAllByChatId(chatId);
            if (CollectionUtils.isEmpty(userGroups)) {
                sendBotMessageService.sendMessage(chatId, GROUP_MESSAGE_EMPTY);
            } else {

                SendMessage message = new SendMessage();
                message.setChatId(chatId.toString());
                message.setText("Кого вызвать?");

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

                for (int i = 0; i < userGroups.size(); i++) {
                    String title = userGroups.get(i).getTitle();
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(title);
                    button.setCallbackData(title);
                    List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>(1);
                    inlineKeyboardButtons.add(button);
                    rowList.add(inlineKeyboardButtons);
                }

                inlineKeyboardMarkup.setKeyboard(rowList);

                message.setReplyMarkup(inlineKeyboardMarkup);


                sendBotMessageService.sendMessage(message);


            }
        } else if (getMessage(update).split(Constant.REGEX_GROUP).length > 1) {
            String[] groupTitles = getMessage(update).split(Constant.REGEX_GROUP);
            for (int i = 1; i < groupTitles.length; i++) {
                if (userGroupService.isExistGroup(chatId, groupTitles[i])) {
                    UserGroup userGroup = userGroupService.findByTitle(chatId, groupTitles[i]);
                    sendBotMessageService.sendMessage(chatId, userGroup.getTitle() + " \uD83D\uDD0A " + userGroup.getUsers());
                }
            }
        }
    }
}
