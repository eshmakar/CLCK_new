package com.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ClckRu {
    public static void main(String[] args) throws IOException {
        LocalDateTime l = LocalDateTime.now();
        String date = l.getYear() + "-" + l.getMonthValue() + "-" + l.getDayOfMonth();
        FileWriter f = new FileWriter(new File("D:/CLCK.RU/" + date + ".txt"), true);

        long start = 0;
        int count = 0;
        int GlobalCount = 0;
        String url;

//WBsXx
//0-0 1-1 2-2 3-3 4-4 5-5 6-6 7-7 8-8 9-9 A-10
//B-11 C-12 D-13 E-14 F-15 G-16 H-17 I-18 J-19 K-20 L-21 M-22 N-23 O-24 P-25 Q-26 R-27 S-28 T-29 U-30 V-31 W-32 X-33 Y-34 Z-35
//a-36 b-37 c-38 d-39 e-40 f-41 g-42 h-43 i-44 j-45 k-46 l-47 m-48 n-49 o-50 p-51 q-52 r-53 s-54 t-55 u-56 v-57 w-58 x-59 y-60 z-61

        char[] symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        System.out.println("Работу начал...");
        for (int one = 32; one < 62; one++) {
            for (int two = 11; two < 62; two++) {
                for (int three = 37; three < 62; three++) {
                    for (int four = 0; four < 62; four++) {
                        for (int five = 0; five < 62; five++) {

                            GlobalCount++;
                            url = String.format("https://clck.ru/%c%c%c%c%c", symbols[one], symbols[two], symbols[three], symbols[four], symbols[five]);

                            InputStream response;
                            byte[] delimetr;

                            try {
                                URLConnection con = new URL(url).openConnection();
                                con.setConnectTimeout(3000);
                                con.setReadTimeout(5000);
                                response = con.getInputStream();//открывается поток данных из url

                                delimetr = response.readNBytes(15_000);//читаем первые 15 000 байтов
                                response.close();

                                String temp = new String(delimetr, StandardCharsets.UTF_8);//считанные байты переобразуется в String с кодировкой utf-8
                                String titleFromSite = temp.substring(temp.indexOf("<title>") + 7, temp.indexOf("</title>")).strip();//выделяется (название страницы) после тега title и до закрывающийся тега title

                                if (titleFromSite.contains("�")) {//если выводятся крякозябры, то меняем кодировку
                                    temp = new String(delimetr, "windows-1251");
                                    titleFromSite = temp.substring(temp.indexOf("<title>") + 7, temp.indexOf("</title>"));
                                }

                                if (skryvat(titleFromSite)) {
                                    double sec = (double) (System.currentTimeMillis() - start) / 1000;
                                    double sec2 = (double) (System.currentTimeMillis() - start) % 1000;
                                    double min = (double) ((System.currentTimeMillis() - start) / 1000) % 60;

                                    String SECUND = "%d. %s - %s (%d) - %.3f сек. %s\n";
                                    String MINUTE = "%d. %s - %s (%d) - %.0f мин. и %.0f сек. %s\n";

                                    LocalDateTime HoursMinutesSeconds = LocalDateTime.now();
                                    String date2 = HoursMinutesSeconds.getHour() + ":" + HoursMinutesSeconds.getMinute() + ":" + HoursMinutesSeconds.getSecond();

                                    if (sec < 60) {
                                        System.out.printf(SECUND, ++count, url, titleFromSite, GlobalCount, sec, date2);
                                        f.write(String.format(SECUND, count, url, titleFromSite, GlobalCount, sec, date2));

                                    } else {
                                        System.out.printf(MINUTE, ++count, url, titleFromSite, GlobalCount, min, sec2, date2);
                                        f.write(String.format(MINUTE, count, url, titleFromSite, GlobalCount, min, sec2, date2));
                                    }
                                    f.flush();
                                    start = System.currentTimeMillis();
                                    System.gc();
                                }
                                if (GlobalCount % 100 == 0) {
                                    System.gc();
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean skryvat(String s) {
        return !s.equals("Google Документы ‒ бесплатно создавайте и редактируйте документы в Интернете.")
                && !s.equals("Facebook") && !s.equals("РСТ МЕТРОЛОГИЯ") && !s.contains("Ivermectin") && !s.contains("SEX")
                && !s.equals("Google Таблицы ‒ бесплатно создавайте и редактируйте таблицы в Интернете.")
                && !s.contains("Яндекс.Карты") && !s.equals("Пройди тест на денежное мышление") && !s.contains("گ")
                && !s.equals("Обратная связь - Группа медицинских компаний студия") && !s.contains("Опрос ИМ- Testograf.ru")
                && !s.equals("МТС-Банк") && !s.equals("Index - MerchantNew") && !s.contains("Скажите, доктор…")
                && !s.contains("РУСХИТ") && !s.equals(" Google Карты ") && !s.equals("Яндекс.Картинки") && !s.equals("Google Карты")
                && !s.equals("Срочноденьги Заявка") && !s.equals("Best dating worldwide") && !s.contains("Перевод №15322856R82")
                && !s.equals("Яндекс") && !s.equals("МВД РОССИИ: УВЕДОМЛЕНИЕ О ШТРАФЕ") && !s.contains("Один Диск для всех файлов")
                && !s.equals("Страница оплаты") && !s.contains("html") && !s.equals("New phoenix") && !s.contains("Одноклассники")
                && !s.equals("Корзина") && !s.equals("Ru_kitchent at Taplink") && !s.equals("Самолет") && !s.contains("Установка натяжных потолков")
                && !s.contains("Авито") && !s.equals("Оценка курьера") && !s.equals("Добро пожаловать!") && !s.contains("БАРЬЕР")
                && !s.contains("PeoplePass") && !s.equals("axilog.ru - служба доставки") && !s.contains("бесплатно создавайте и редактируйте")
                && !s.equals("QIWI") && !s.equals("Расчёт стоимости полиса ОСАГО онлайн") && !s.contains("потрахаться")
                && !s.toLowerCase().contains("секс") && !s.equals("Ваш заказ | Вилгуд") && !s.equals("TikTok") && !s.equals("Альфа-Банк")
                && !s.contains("mistery") && !s.equals("Как самозанятым получать бесплатные смены  — Яндекс.Про")
                && !s.contains("xvideo") && !s.contains("ЮMoney") && !s.equals("Оплата") && !s.contains("Оцените уровень сервиса Bungly boo!")
                && !s.contains("porno") && !s.equals("") && !s.contains("Линзмастер") && !s.contains("На чём ты спишь?")
                && !s.contains("Telegraph") && !s.equals("Короткий URL для всех!") && !s.equals("301 Moved Permanently")
                && !s.contains("Ташкент") && !s.equals(" ") && !s.contains("Redirecting...") && !s.contains("Telegram:")
                && !s.equals("Get Laid Tonight") && !s.equals("Ой!") && !s.equals("КэшДрайв") && !s.equals("Faberlic")
                && !s.equals("Google Таблицы ‒ бесплатно создавайте и редактируйте таблицы в Интернете.")
                && !s.equals("PravSistem.Club") && !s.equals("Warning! | There might be a problem with the requested link")
                && !s.contains("Квартиры свободные") && !s.equals("Статус заказа") && !s.equals("Закрытый мужской клуб");
    }
}
