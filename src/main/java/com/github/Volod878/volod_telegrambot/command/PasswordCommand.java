package com.github.Volod878.volod_telegrambot.command;

import com.github.Volod878.volod_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static com.github.Volod878.volod_telegrambot.command.UnknownCommand.UNKNOWN_MESSAGE;

/**
 * Password {@link Command}.
 */
public class PasswordCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static String PASSWORD_MESSAGE;



    public PasswordCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public static ByteArrayOutputStream getPassword(int count) throws IOException {
        char n1 = '0';
        char n2 = '9';
        char chUp1 = 'A';
        char chUp2 = 'Z';
        char chDown1 = 'a';
        char chDown2 = 'z';
        byte[] password = new byte[count];
        Random random = new Random();
        boolean n, up, down;
        n = up = down = false;
        while (!(n && up && down)) {
            n = up = down = false;
            for (int i = 0; i < password.length; i++) {
                byte randomNumber = (byte) (n1 + random.nextInt(n2 - n1 + 1));
                byte randomUp = (byte) (chUp1 + random.nextInt(chUp2 - chUp1 + 1));
                byte randomDown = (byte) (chDown1 + random.nextInt(chDown2 - chDown1 + 1));
                int rand = random.nextInt(4);
                if (rand == 3) {
                    password[i] = randomNumber;
                    n = true;
                }
                else if (rand == 1) {
                    password[i] = randomUp;
                    up = true;
                } else if (rand == 2) {
                    password[i] = randomDown;
                    down = true;
                }
                if (rand == 0) i--;
            }
        }

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        array.write(password);
        return array;
    }

    private static boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void execute(Update update) {
        try {
            String message = update.getMessage().getText().trim().split(" ")[1];
            int i = Integer.parseInt(message);
            if (i < 3) {
                i = 3;
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                        "Ваше число автоматически увеличено до минимальной длины пароля который" +
                                " я могу сгенерировать");
            }
            if (i > 1000) {
                i = 1000;
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                        "Ваше число автоматически уменьшилось до 1000 символов");
            }

            if (isDigit(message)) {
                ByteArrayOutputStream password = getPassword(i);
                PASSWORD_MESSAGE = password.toString();
            } else {
                PASSWORD_MESSAGE = UNKNOWN_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            PASSWORD_MESSAGE = UNKNOWN_MESSAGE;
        }
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), PASSWORD_MESSAGE);
    }
}
