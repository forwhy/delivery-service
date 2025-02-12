package ru.hofftech.deliveryservice.telegramclient.model.impl;

import org.springframework.stereotype.Component;
import ru.hofftech.deliveryservice.telegramclient.model.Command;

@Component
public class HelpCommand implements Command {

    private static final String HELP_TEXT = """
        Привет! На связи служба работы с посылками.
            
            Вот описание доступных команд:
            
            1) /create --name "Название посылки" --form "форма посылки" --symbol "символ"
                Команда создаёт новую посылку с указанным названием, формой и символом отображения.
                Пример: /create --name "Квадратное колесо" --form "xxx%nx x%nxxx" --symbol "o"
                В результате будет создана новая посылка Квадратное колесо вида:
                ooo
                o o
                ooo
            
            2) /find "Название посылки"
                 Команда ищет посылку по названию и отображает информацию о ней.
                 Пример: /find "Посылка Тип 1"
                 
            3) /find-all
                 Команда отображает информацию по всем допустимым посылкам.
                 Пример: /find-all
            
            4) /edit "Старое имя" --name "Новое имя" --form "новая форма" --symbol "новый символ"
                 Команда редактирует посылку с указанным именем.
                 Пример: /edit "Квадратное колесо" --name "КУБ" --form "xxx%nxxx%nxxx" -symbol "%"
                 В результате Квадратное колесо будет заменено посылкой КУБ вида:
                 %%%
                 %%%
                 %%%
            
            5) /delete "Название посылки"
                 Команда удаляет посылку по её названию.
                 Пример: /delete "Посылка Тип 4"
            
            6.1) /load --u "почта@mail.ru" --parcels-text "Посылка Тип 1%nПосылка Тип 2" --trucks "3x3%n6x2" --type "Одна посылка - один грузовик" --out text
                 Команда для загрузки посылок с заданными размерами и стратегией в текстовом формате.
            6.2) /load --u "почта@mail.ru" --parcels-file "parcels.csv" --trucks "3x3%n6x2" --type "Сначала широкие" --out json-file --out-filename "Название_файла_вывода.txt"
                 Команда для загрузки посылок с заданными размерами и стратегией в виде файлов. Файл для вывода указывается без расширения (всегда json)
            
            7) /unload --u "почта@mail.ru" --infile "trucks.json" --outfile "parcels.csv" --withcount
                 Команда выгружает посылки из файла с подсчетом количества (опционально).
                 Пример: /unload --u "почта@mail.ru" --infile "trucks.json" --outfile "parcels-with-count.csv" --withcount

            8) /billing --u "почта@mail.ru"
                 Команда отображает квитанции пользователя по его имени
                 Пример: /billing --u "почта@mail.ru"
        """;

    @Override
    public String execute(String commandText) {
        return HELP_TEXT;
    }
}
