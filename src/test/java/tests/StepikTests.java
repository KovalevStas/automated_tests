package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class StepikTests {

    @BeforeEach
    void openingSite(){
        // Переход на страницу тестируемого сайта
        open("https://stepik.org/");
        //Нажатие кнопки "Войти"
        $("#ember201").click();
        // Ввести логин
        $(byName("login")).setValue("test.user.pikabu@gmail.com");
        // Ввести пароль и нажать Enter
        $(byName("password")).setValue("pikabu123").pressEnter();
    }

    @Test
    void checkProfileTest() {

        // Проверить наличие аватара пользователя
        $(".navbar__profile-img").isEnabled();
        //Вызов выпадающего меню
        $(".navbar__profile-img").click();
        //Нажатие на пункт "Профиль"
        $(".drop-down__body a").click();
        //Проверка отображения имени пользователя
        $(".profile-header-widget__name-wrapper h1").shouldHave(text("Тестовый пользователь"));
    }

    @Test
    void searchTest(){
        $(byText("Каталог")).click();
        //$(".st-course-filters").shouldHave(text("Математика")).click();
        //Вводим запрос в поле поиска
        $(".explore__search__input-wrapper input[placeholder = 'Поиск по каталогу']").setValue("Уравнения").pressEnter();
        //Проверяем результат поиска
        $(".course-pack").shouldHave(text("Уравнения"));
    }

    @Test
    void joinAndExitCourseTest(){
        //Найти курс и перейти на его страницу
        $(byText("Машинное обучение и управление проектами в IT для преподавателей")).click();
        //Записаться на курс
        $(byText("Поступить на курс")).click();
        //Дождаться загрузки страницы первого урока
        $(".lesson-sidebar__course-title").shouldBe(visible);
        //Открыть список меню пользователя и перейти в пункт "Мои курсы"
        $(".navbar__profile-img").click();
        $(".drop-down__body a",1).click();
        //Проверить наличие курса в списке курсов пользователя
        $(".course-widget__main-info a").shouldHave(text("Машинное обучение и управление проектами в IT для преподавателей"));
        //Покинуть курс
        $(".course-widget__menu").click();
        $(byText("Покинуть")).click();
        //Подтвердить действие
        switchTo().alert().accept();
        //Проверить, что у объекта курса пропали контролы
        Assertions.assertEquals(true,$$("course-widget__menu-toggler").size() ==0);
        //Обновить страницу
        refresh();
        //Проверить,что список курсов пустой
        Assertions.assertEquals(true,$$(".course-widget__main-info").size() ==0);
    }

    @AfterEach
    void exit(){
        $(".navbar__profile-img").click();
        $(".drop-down__body button",1).click();
        switchTo().alert().accept();
        closeWindow();
    }
}
