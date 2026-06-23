# Dokumentacja projektu — Cafe Games Website

## 1. Wstęp

**Cafe Games Website** to aplikacja internetowa wykonana w technologii Java Servlet/JSP. Projekt przedstawia prosty serwis z grami kasynowymi, w którym użytkownik może założyć konto, zalogować się, korzystać z dostępnych gier, sprawdzać saldo konta oraz przeglądać historię rozegranych rozgrywek.

Projekt został przygotowany jako aplikacja webowa uruchamiana na serwerze Apache Tomcat. Warstwa backendowa została napisana w języku Java, widoki zostały przygotowane w JSP, wygląd strony w CSS, a dane użytkowników i historia gier są przechowywane w bazie danych MySQL/MariaDB.

Aplikacja posiada ciemny motyw graficzny nawiązujący do kasyna. Główne elementy strony wykorzystują czarne i ciemnoszare tła, złote obramowania oraz czerwone akcenty przy przyciskach i elementach interaktywnych.

---

## 2. Cel projektu

Celem projektu było stworzenie strony internetowej umożliwiającej użytkownikowi korzystanie z prostych gier kasynowych. Projekt pokazuje działanie aplikacji webowej z logowaniem, obsługą sesji, połączeniem z bazą danych oraz dynamicznym generowaniem stron JSP.

Główne cele aplikacji:

* umożliwienie rejestracji nowego użytkownika,
* umożliwienie logowania do konta,
* zapisanie danych użytkownika w bazie danych,
* bezpieczne przechowywanie hasła w postaci hashu,
* obsługa sesji zalogowanego użytkownika,
* wyświetlanie panelu głównego po zalogowaniu,
* możliwość gry w ruletkę, blackjacka i jednorękiego bandytę,
* aktualizacja salda użytkownika po grze,
* zapis historii rozegranych gier,
* wyświetlanie statystyk i historii aktywności gracza.

---

## 3. Technologie użyte w projekcie

W projekcie wykorzystano następujące technologie:

| Technologia     | Zastosowanie                                      |
| --------------- | ------------------------------------------------- |
| Java            | Logika aplikacji, modele danych, obsługa gier     |
| Jakarta Servlet | Obsługa żądań HTTP i tras aplikacji               |
| JSP             | Tworzenie dynamicznych widoków HTML               |
| HTML            | Struktura stron internetowych                     |
| CSS             | Wygląd aplikacji i motyw kasynowy                 |
| JavaScript      | Elementy interaktywne i animacje na stronach gier |
| Maven           | Budowanie projektu i zarządzanie zależnościami    |
| MySQL/MariaDB   | Baza danych użytkowników i historii gier          |
| JDBC            | Komunikacja aplikacji z bazą danych               |
| jBCrypt         | Hashowanie i sprawdzanie haseł                    |
| Apache Tomcat   | Serwer uruchamiający aplikację webową             |

Projekt jest skonfigurowany jako aplikacja typu `war`, co oznacza, że może zostać zbudowany i uruchomiony na serwerze aplikacyjnym zgodnym z technologią Servlet/JSP.

---

## 4. Struktura projektu

Projekt posiada strukturę typową dla aplikacji Maven Web Application.

Przykładowa struktura katalogów:

```text
Cafe-Games-Website/
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/example/
│       │   │   ├── server.java
│       │   │   ├── User.java
│       │   │   ├── Querysql.java
│       │   │   ├── Databaseconnection.java
│       │   │   ├── Gamehistory.java
│       │   │   ├── RouletteMethod.java
│       │   │   └── BlackjackMethod.java
│       │   │
│       │   └── utils/
│       │       ├── BlackjackCard.java
│       │       ├── Forward.java
│       │       ├── Gateway.java
│       │       ├── Handlelogin.java
│       │       ├── Handleregister.java
│       │       └── Handleusername.java
│       │
│       └── webapp/
│           ├── WEB-INF/
│           ├── css/
│           ├── images/
│           └── view/
│               ├── blackjack.jsp
│               ├── change-password.jsp
│               ├── history.jsp
│               ├── login.jsp
│               ├── main.jsp
│               ├── profile.jsp
│               ├── register.jsp
│               ├── roulette.jsp
│               ├── slots.jsp
│               └── username.jsp
│
├── kasynodb.sql
├── pom.xml
├── README.md
└── start-tomcat-debug.bat
```

Najważniejsze części projektu:

* `src/main/java/com/example` — główne klasy aplikacji, modele danych, logika gier i obsługa bazy danych,
* `src/main/java/utils` — klasy pomocnicze używane przez serwlet,
* `src/main/webapp/view` — widoki JSP,
* `src/main/webapp/css` — pliki stylów CSS,
* `src/main/webapp/images` — grafiki używane przez stronę,
* `kasynodb.sql` — plik z bazą danych,
* `pom.xml` — konfiguracja projektu Maven.

---

## 5. Architektura aplikacji

Aplikacja działa w architekturze klient-serwer.

Użytkownik korzysta z przeglądarki internetowej. Po wejściu na stronę wysyła żądania HTTP do serwera Tomcat. Główny serwlet `server.java` odbiera te żądania, sprawdza ścieżkę URL, wykonuje odpowiednią logikę i przekazuje użytkownika do właściwego widoku JSP.

Widoki JSP generują stronę HTML widoczną dla użytkownika. Dane takie jak saldo, nazwa użytkownika, historia gier lub wynik rozgrywki są przekazywane do JSP przez atrybuty żądania albo przez sesję.

Baza danych przechowuje dane użytkowników oraz historię rozegranych gier. Komunikacja z bazą odbywa się przez klasę `Querysql`, która korzysta z połączenia tworzonego w klasie `Databaseconnection`.

Ogólny schemat działania:

```text
Przeglądarka użytkownika
        ↓
Żądanie HTTP
        ↓
server.java
        ↓
Logika aplikacji / Querysql / klasy gier
        ↓
Baza danych MySQL/MariaDB
        ↓
Widok JSP
        ↓
Odpowiedź HTML dla użytkownika
```

---

## 6. Konfiguracja Maven

Projekt wykorzystuje Maven do budowania aplikacji i zarządzania zależnościami. Plik `pom.xml` określa między innymi:

* identyfikator grupy: `com.example`,
* nazwę artefaktu: `kasyno`,
* wersję: `1.0-SNAPSHOT`,
* typ pakowania: `war`,
* wersję Javy: 17,
* zależność do Jakarta Servlet API,
* zależność do jBCrypt,
* zależność do MySQL Connector/J,
* konfigurację pluginów Mavena,
* nazwę wynikową aplikacji jako `ROOT`.

Dzięki temu po zbudowaniu projektu można uruchomić aplikację na Tomcat jako aplikację webową.

---

## 7. Baza danych

Projekt korzysta z bazy danych o nazwie `kasynodb`. W bazie znajdują się dwie najważniejsze tabele:

* `users`,
* `game_history`.

### 7.1. Tabela `users`

Tabela `users` przechowuje dane kont użytkowników.

Pola tabeli:

| Pole       | Typ          | Opis                            |
| ---------- | ------------ | ------------------------------- |
| `id`       | int          | Identyfikator użytkownika       |
| `login`    | varchar(50)  | Login używany podczas logowania |
| `password` | varchar(255) | Hasło zapisane w postaci hashu  |
| `username` | varchar(50)  | Nazwa wyświetlana użytkownika   |
| `balance`  | int          | Aktualne saldo użytkownika      |

Tabela posiada klucz główny `id`. Login i nazwa użytkownika są oznaczone jako unikalne, dzięki czemu nie powinny się powtarzać.

### 7.2. Tabela `game_history`

Tabela `game_history` przechowuje historię rozegranych gier.

Pola tabeli:

| Pole             | Typ         | Opis                         |
| ---------------- | ----------- | ---------------------------- |
| `id`             | int         | Identyfikator wpisu historii |
| `user_id`        | int         | Identyfikator użytkownika    |
| `game_name`      | varchar(50) | Nazwa gry                    |
| `bet`            | int         | Wysokość zakładu             |
| `result`         | varchar(50) | Wynik gry                    |
| `balance_change` | int         | Zmiana salda po grze         |
| `created_at`     | timestamp   | Data i czas zapisania wpisu  |

Tabela `game_history` pozwala aplikacji wyświetlać historię gier, liczbę rozegranych gier, liczbę wygranych, liczbę przegranych oraz ostatnią aktywność użytkownika.

---

## 8. Opis najważniejszych klas

### 8.1. `server.java`

`server.java` jest głównym serwletem aplikacji. Odpowiada za obsługę żądań `GET` i `POST`.

Serwlet obsługuje między innymi ścieżki:

* `/` — panel główny użytkownika,
* `/login` — strona logowania,
* `/register` — strona rejestracji,
* `/username` — ustawianie nazwy użytkownika,
* `/profil` — profil użytkownika,
* `/history` — historia gier,
* `/roulette` — gra ruletka,
* `/slots` — gra jednoręki bandyta,
* `/blackjack` — gra blackjack,
* `/change-password` — zmiana hasła,
* `/buy-coins` — dodanie żetonów,
* `/logout` — wylogowanie użytkownika.

W metodzie `doGet()` serwlet wyświetla odpowiednie strony JSP. W metodzie `doPost()` obsługuje formularze, logikę gier, zmianę hasła, logowanie i rejestrację.

### 8.2. `Databaseconnection.java`

Klasa `Databaseconnection` odpowiada za utworzenie połączenia z bazą danych. W kodzie znajduje się adres połączenia JDBC do bazy `kasynodb`, użytkownik bazy oraz hasło.

Klasa ładuje sterownik MySQL:

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

Następnie zwraca połączenie przez:

```java
DriverManager.getConnection(...)
```

### 8.3. `Querysql.java`

`Querysql` jest klasą odpowiedzialną za wykonywanie zapytań SQL.

Najważniejsze zadania tej klasy:

* zapis nowego użytkownika,
* wyszukiwanie użytkownika po loginie,
* sprawdzanie, czy login istnieje,
* sprawdzanie, czy nazwa użytkownika istnieje,
* aktualizacja nazwy użytkownika,
* aktualizacja salda,
* zmiana hasła,
* zapis historii gry,
* pobranie historii gier użytkownika,
* pobranie liczby rozegranych gier,
* pobranie liczby wygranych gier,
* pobranie liczby przegranych gier,
* pobranie ostatniej aktywności.

Klasa wykorzystuje `PreparedStatement`, dzięki czemu dane przekazywane do zapytań SQL są ustawiane jako parametry.

### 8.4. `User.java`

`User` jest modelem użytkownika. Przechowuje informacje:

* `id`,
* `login`,
* `username`,
* `password`,
* `balance`.

Klasa posiada dwa konstruktory. Pierwszy służy do tworzenia użytkownika przy rejestracji i hashuje hasło za pomocą jBCrypt. Drugi służy do tworzenia obiektu użytkownika na podstawie danych pobranych z bazy.

Klasa zawiera również metody:

* pobierające dane użytkownika,
* ustawiające nowe hasło,
* dodające lub odejmujące saldo,
* aktualizujące nazwę użytkownika,
* sprawdzające poprawność hasła.

### 8.5. `Gamehistory.java`

`Gamehistory` jest modelem pojedynczego wpisu historii gry. Obiekt tej klasy przechowuje:

* identyfikator wpisu,
* identyfikator użytkownika,
* nazwę gry,
* zakład,
* wynik,
* zmianę salda,
* datę utworzenia wpisu.

Klasa posiada konstruktor oraz gettery umożliwiające odczyt danych.

### 8.6. `RouletteMethod.java`

`RouletteMethod` zawiera logikę ruletki. Metoda `roulette()` sprawdza wybrany typ zakładu i zwraca mnożnik wygranej.

Obsługiwane typy zakładów:

* `black` — czarne,
* `red` — czerwone,
* `even` — parzyste,
* `odd` — nieparzyste,
* `number` — dokładna liczba.

Metody pomocnicze `isRed()` i `isBlack()` sprawdzają kolor wylosowanej liczby.

### 8.7. `BlackjackMethod.java`

`BlackjackMethod` zawiera logikę blackjacka. Klasa pozwala:

* utworzyć talię kart,
* potasować talię,
* dobrać kartę,
* policzyć wynik ręki.

Wynik ręki jest liczony z uwzględnieniem asa. As domyślnie ma wartość 11, ale jeżeli wynik przekracza 21, jego wartość może zostać zmniejszona o 10.

### 8.8. `BlackjackCard.java`

`BlackjackCard` reprezentuje pojedynczą kartę w blackjacku. Karta posiada:

* wartość tekstową, np. `A`, `K`, `10`,
* symbol koloru, np. pik, kier, karo albo trefl,
* wartość punktową.

Klasa posiada metodę `getDisplay()`, która zwraca kartę w formie tekstowej, np. `A♠`.

### 8.9. Klasy pomocnicze z pakietu `utils`

Pakiet `utils` zawiera klasy pomocnicze:

* `Forward` — przekierowuje żądanie do wybranego pliku JSP w katalogu `/view/`,
* `Gateway` — sprawdza, czy użytkownik jest zalogowany i czy posiada ustawioną nazwę użytkownika,
* `Handlelogin` — obsługuje logowanie,
* `Handleregister` — obsługuje rejestrację,
* `Handleusername` — obsługuje ustawianie nazwy użytkownika.

Dzięki temu część logiki została wydzielona poza główny serwlet.

---

## 9. Widoki JSP

Widoki JSP znajdują się w katalogu `src/main/webapp/view`.

Najważniejsze widoki:

| Widok                 | Opis                                        |
| --------------------- | ------------------------------------------- |
| `login.jsp`           | Formularz logowania                         |
| `register.jsp`        | Formularz rejestracji                       |
| `username.jsp`        | Ustawianie nazwy użytkownika po rejestracji |
| `main.jsp`            | Panel główny użytkownika                    |
| `profile.jsp`         | Profil użytkownika                          |
| `history.jsp`         | Historia rozegranych gier                   |
| `roulette.jsp`        | Widok gry ruletka                           |
| `slots.jsp`           | Widok gry jednoręki bandyta                 |
| `blackjack.jsp`       | Widok gry blackjack                         |
| `change-password.jsp` | Formularz zmiany hasła                      |

Widoki JSP wyświetlają dane przekazane przez serwlet, np. nazwę użytkownika, saldo, wynik gry, listę historii albo komunikaty błędów.

---

## 10. Opis działania aplikacji

### 10.1. Rejestracja

Użytkownik może zarejestrować konto przez formularz rejestracyjny. Podaje login, hasło, powtórzenie hasła oraz potwierdza wymagane zgody. Formularz jest obsługiwany przez klasę `Handleregister`.

Podczas rejestracji aplikacja sprawdza:

* czy login nie jest pusty,
* czy hasło nie jest puste,
* czy oba hasła są takie same,
* czy hasło ma minimum 4 znaki,
* czy hasło zawiera dużą literę,
* czy hasło zawiera cyfrę,
* czy zaakceptowano regulamin,
* czy potwierdzono pełnoletność,
* czy login nie istnieje już w bazie.

Po poprawnej rejestracji użytkownik zostaje zapisany w bazie danych, a następnie przekierowany do ustawienia nazwy użytkownika.

### 10.2. Logowanie

Logowanie jest obsługiwane przez klasę `Handlelogin`. Użytkownik podaje login i hasło. Aplikacja sprawdza, czy użytkownik istnieje w bazie oraz czy podane hasło pasuje do zapisanego hasha.

Po poprawnym logowaniu obiekt użytkownika zostaje zapisany w sesji pod nazwą `loggedUser`. Jeżeli użytkownik nie ma ustawionej nazwy użytkownika, zostaje przekierowany do strony `username`.

### 10.3. Ustawianie nazwy użytkownika

Po rejestracji użytkownik może ustawić nazwę użytkownika. Logika znajduje się w klasie `Handleusername`.

Aplikacja sprawdza:

* czy użytkownik jest zalogowany,
* czy podana nazwa nie jest pusta,
* czy nazwa nie jest już zajęta.

Po poprawnym ustawieniu nazwy użytkownik zostaje przekierowany do panelu głównego.

### 10.4. Panel główny

Panel główny jest dostępny pod ścieżką `/`. Przed wyświetleniem panelu aplikacja sprawdza, czy użytkownik jest zalogowany. Następnie pobiera dane statystyczne z bazy danych:

* ostatnią grę,
* liczbę rozegranych gier,
* liczbę wygranych gier,
* liczbę przegranych gier.

Panel główny wyświetla również saldo i linki do dostępnych gier.

### 10.5. Profil użytkownika

Strona profilu pokazuje dane konta użytkownika, takie jak nazwa użytkownika i saldo. Z poziomu profilu można przejść do innych funkcji, np. historii gier albo zmiany hasła.

### 10.6. Historia gier

Historia gier jest dostępna pod ścieżką `/history`. Aplikacja pobiera z bazy maksymalnie 50 ostatnich wpisów historii dla zalogowanego użytkownika. W historii widoczna jest nazwa gry, zakład, wynik, zmiana salda i data rozegrania.

### 10.7. Zmiana hasła

Zmiana hasła jest obsługiwana przez ścieżkę `/change-password`. Użytkownik podaje stare hasło, nowe hasło i potwierdzenie nowego hasła.

Aplikacja sprawdza:

* czy użytkownik jest zalogowany,
* czy stare hasło jest poprawne,
* czy nowe hasło i potwierdzenie są takie same.

Po poprawnej zmianie hasło zostaje ponownie zahashowane i zapisane w bazie danych.

---

## 11. Gry dostępne w aplikacji

### 11.1. Ruletka

Ruletka jest dostępna pod ścieżką `/roulette`.

Użytkownik wybiera typ zakładu i kwotę. Możliwe typy zakładów to:

* kolor czarny,
* kolor czerwony,
* liczba parzysta,
* liczba nieparzysta,
* dokładna liczba.

Po wysłaniu formularza serwlet:

1. Pobiera dane zakładu.
2. Sprawdza, czy użytkownik jest zalogowany.
3. Sprawdza, czy zakład jest większy od zera.
4. Sprawdza, czy użytkownik ma wystarczające saldo.
5. Przy zakładzie na dokładny numer sprawdza, czy liczba mieści się w zakresie 0–36.
6. Losuje liczbę od 0 do 36.
7. Oblicza mnożnik wygranej.
8. Aktualizuje saldo użytkownika.
9. Zapisuje wynik do historii gier.
10. Przekazuje wynik do widoku JSP.

### 11.2. Jednoręki Bandyta

Gra `Jednoręki Bandyta` jest dostępna pod ścieżką `/slots`.

Użytkownik podaje kwotę zakładu. Następnie aplikacja losuje trzy wartości z zakresu od 1 do 7. Wynik zależy od zgodności wylosowanych wartości.

Zasady wyniku:

* trzy takie same wartości — jackpot,
* dwie takie same wartości — wygrana,
* brak pary — przegrana.

Po zakończeniu gry aplikacja aktualizuje saldo, przekazuje wynik do widoku i zapisuje wpis do historii gier.

### 11.3. Blackjack

Blackjack jest dostępny pod ścieżką `/blackjack`.

Gra wykorzystuje talię kart tworzoną w `BlackjackMethod`. Po rozpoczęciu gry użytkownik podaje zakład, a aplikacja tworzy talię, tasuje ją i rozdaje karty graczowi oraz krupierowi.

Dostępne akcje:

* `start` — rozpoczęcie gry,
* `hit` — dobranie karty,
* `stand` — zakończenie dobierania i porównanie wyniku,
* `reset` — rozpoczęcie nowej gry.

Podczas gry aplikacja liczy punkty gracza i krupiera. Jeżeli gracz przekroczy 21 punktów, przegrywa. Gdy użytkownik wybierze `stand`, krupier dobiera karty do minimum 17 punktów. Następnie wyniki są porównywane, saldo zostaje zaktualizowane, a wynik zapisany w historii gier.

---

## 12. Obsługa sesji

Aplikacja wykorzystuje sesję HTTP do przechowywania zalogowanego użytkownika. Po poprawnym logowaniu obiekt użytkownika jest zapisywany w sesji jako `loggedUser`.

Klasa `Gateway` sprawdza, czy:

* sesja istnieje,
* w sesji znajduje się zalogowany użytkownik,
* użytkownik posiada ustawioną nazwę użytkownika.

Jeżeli warunki nie są spełnione, użytkownik jest przekierowywany do logowania albo do ustawienia nazwy użytkownika.

---

## 13. Bezpieczeństwo i walidacja

Projekt zawiera podstawowe mechanizmy bezpieczeństwa:

* hasła są hashowane przy użyciu jBCrypt,
* logowanie sprawdza hasło za pomocą funkcji `BCrypt.checkpw`,
* chronione strony są dostępne tylko dla zalogowanego użytkownika,
* rejestracja sprawdza poprawność danych,
* aplikacja sprawdza, czy login i nazwa użytkownika są unikalne,
* przed grą sprawdzane jest saldo użytkownika,
* zakład nie może być mniejszy lub równy zero,
* historia gier jest przypisywana do konkretnego użytkownika przez `user_id`.

W projekcie zastosowano także `PreparedStatement` do wykonywania zapytań SQL, co ogranicza ryzyko bezpośredniego łączenia danych użytkownika z zapytaniem SQL.

---

## 14. Wygląd aplikacji

Warstwa wizualna znajduje się w katalogu `src/main/webapp/css`. Projekt posiada osobne pliki CSS dla różnych części aplikacji, między innymi:

* `MainStyle.css`,
* `style.css`,
* `profil.css`,
* `history.css`,
* `rouletteStyle.css`,
* `slots.css`,
* `blackjack.css`,
* `changepassword.css`.

Motyw graficzny opiera się na stylu kasynowym. Strony korzystają z ciemnego tła, złotych obramowań, jasnego tekstu i czerwonych przycisków. Każda gra posiada własną stronę JSP i własny styl CSS, dzięki czemu wygląd może być dostosowany do konkretnej gry.

---

## 15. Instrukcja uruchomienia projektu

Aby uruchomić projekt, należy przygotować środowisko:

1. Zainstalować Java JDK 17 lub nowszą.
2. Zainstalować Apache Maven.
3. Zainstalować Apache Tomcat obsługujący Jakarta Servlet.
4. Uruchomić MySQL lub MariaDB.
5. Utworzyć bazę danych `kasynodb`.
6. Zaimportować plik `kasynodb.sql`.
7. Sprawdzić dane połączenia w klasie `Databaseconnection`.
8. Zbudować projekt poleceniem:

```bash
mvn clean package
```

9. Wgrać wygenerowany plik `.war` na serwer Tomcat.
10. Otworzyć aplikację w przeglądarce.

Ponieważ projekt w konfiguracji Mavena ma nazwę wynikową `ROOT`, po wdrożeniu na Tomcat aplikacja może być dostępna pod adresem:

```text
http://localhost:8080/
```

---

## 16. Przykładowy scenariusz użycia

1. Użytkownik otwiera stronę aplikacji.
2. Jeżeli nie jest zalogowany, zostaje przekierowany do logowania.
3. Nowy użytkownik przechodzi do rejestracji.
4. Podaje login, hasło i wymagane potwierdzenia.
5. Po rejestracji ustawia nazwę użytkownika.
6. Po zalogowaniu trafia do panelu głównego.
7. W panelu widzi saldo, statystyki i listę gier.
8. Wybiera jedną z dostępnych gier.
9. Podaje kwotę zakładu.
10. Aplikacja sprawdza saldo i wykonuje losowanie.
11. Po zakończeniu gry saldo zostaje zmienione.
12. Wynik zostaje zapisany w historii.
13. Użytkownik może przejść do historii gier i sprawdzić wcześniejsze rozgrywki.
14. Po zakończeniu korzystania ze strony użytkownik może się wylogować.

---

## 17. Możliwości rozwoju projektu

Projekt można rozbudować o dodatkowe funkcje:

* ranking użytkowników,
* panel administratora,
* filtrowanie historii gier po typie gry,
* sortowanie historii gier,
* bardziej rozbudowane statystyki,
* responsywny wygląd na telefonach,
* dodatkowe gry,
* system osiągnięć,
* reset hasła,
* wiadomości informacyjne po każdej akcji,
* oddzielenie logiki biznesowej od serwletu,
* użycie wzorca MVC w pełniejszej formie,
* dodanie testów jednostkowych,
* poprawę obsługi błędów,
* dodanie konfiguracji bazy poza kodem źródłowym.

---

## 18. Podsumowanie

**Cafe Games Website** to edukacyjny projekt aplikacji webowej napisanej w Javie. Aplikacja umożliwia rejestrację, logowanie, ustawienie nazwy użytkownika, korzystanie z gier kasynowych, zmianę hasła, sprawdzanie salda i przeglądanie historii gier.

Projekt pokazuje praktyczne wykorzystanie technologii Java Servlet, JSP, Maven, JDBC, MySQL/MariaDB oraz CSS. Najważniejszym elementem backendu jest główny serwlet `server.java`, który obsługuje ścieżki aplikacji i kieruje użytkownika do odpowiednich widoków. Dane są przechowywane w bazie `kasynodb`, a historia rozgrywek pozwala tworzyć statystyki użytkownika.

Aplikacja spełnia podstawowe założenia szkolnego projektu webowego, ponieważ zawiera logowanie, obsługę użytkowników, komunikację z bazą danych, dynamiczne widoki JSP oraz kilka funkcjonalnych modułów gier.
