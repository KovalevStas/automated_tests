package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class StepikTests {
    private final String url = "https://stepik.org/";
    private final String login = "test.user.pikabu@gmail.com";
    private final String password = "pikabu123";

    @BeforeAll
    static void config(){
        Configuration.timeout = 10000;
    }

    @Test
    void unSuccessfulAuthorizationTest() {
        open(url);
        
        $(".navbar__auth_login").click(); // Нажатие кнопки "Войти"
        $(byName("login")).setValue(login); // $("#id_login_email").setValue(login);
        $(byName("password")).setValue("123").pressEnter();
        
        $("ul.sign-form__messages li[role='alert']").shouldBe(visible); // Проверка наличия срообщения об ошибке
        //$(".modal-dialog-top__close").click();
    }

    @Test
    void checkProfileTest() {
        open(url);
        
        $(".navbar__auth_login").click(); // Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();
        
        // $(".navbar__profile-img").isEnabled(); // Проверить наличие аватара пользователя
        // assertTrue($(".navbar__profile-img").isEnabled())
        $(".navbar__profile-img").shouldBe(enabled).click(); // Вызов выпадающего меню
        $(".drop-down__body a").click(); // Нажатие на пункт "Профиль"
        
        //Проверка отображения имени пользователя
        $(".profile-header-widget__name").shouldHave(text("Тестовый пользователь"));
    }

    @Test
    void searchTest() {
        open(url);
        
        $(".navbar__auth_login").click(); // Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();
        
        $(byText("Каталог")).click();
        //$(".st-course-filters").shouldHave(text("Математика")).click();
        //Вводим запрос в поле поиска
        $(".explore__search__input-wrapper input[placeholder = 'Поиск по каталогу']").setValue("Уравнения").pressEnter();
        $(by("placeholder", "Поиск по каталогу").setValue("Уравнения").pressEnter();
       
        //Проверяем результат поиска
        $(".course-pack").shouldHave(text("Уравнения"));
    }

    @Test
    void joinAndExitCourseTest() {
        open(url);
        
        $(".navbar__auth_login").click(); // Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();
        
        // Найти курс и перейти на его страницу
        $(byText("Машинное обучение и управление проектами в IT для преподавателей")).click();
        // Записаться на курс
        $(byText("Поступить на курс")).click();
        // Дождаться загрузки страницы первого урока
        $(".lesson-sidebar__course-title").shouldBe(visible);
        // Открыть список меню пользователя и перейти в пункт "Мои курсы"
        $(".navbar__profile-img").click();
        $(".drop-down__body a", 1).click();
        // Проверить наличие курса в списке курсов пользователя
        $(".course-widget__main-info a").shouldHave(text("Машинное обучение и управление проектами в IT для преподавателей"));
        // Покинуть курс
        $(".course-widget__menu").click();
        $(byText("Покинуть")).click();
        //Подтвердить действие
        switchTo().alert().accept();
        
        // Проверить, что у объекта курса пропали контролы
        // Assertions.assertEquals(0, $$(".course-widget__menu-toggler").size());
        $$(".course-widget__menu-toggler").shouldHave(size(0));
        //Обновить страницу
        refresh();
        // Проверить,что список курсов пустой
        // Assertions.assertEquals(0, $$(".course-widget__main-info").size());
        $$(".course-widget__main-info").shouldHave(size(0));
    }

    @AfterEach
    void exit() {
        /*$(".navbar__profile-img").click();
        $(".drop-down__body button", 1).click();
        switchTo().alert().accept();*/
        closeWindow();
    }
}
