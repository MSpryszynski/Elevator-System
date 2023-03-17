### Opis algorytmu:
Mój algorytm wyboru windy, która ma obsłużyć żądanie użytkownika, polega na wybraniu najbliższej
windy, biorąc pod uwagę pewne ograniczenia:
- Jeśli winda będąca najbliżej jest aktualnie wolna, zwyczajnie ją zamawiamy.
- Jeśli winda posiada już osobę, na której zamówienie odpowiada, to odpowie na nasze żądanie wyłącznie,
  gdy wszystkie punkty postojowe (pobranie pierwszego użytkownika, dostarczenie pierwszego użytkownika, pobranie drugiego użytkownika, dostarczenie drugiego użytkownika)
  będą znajdowały się w jednej linii prostej. Np. winda aktualnie na 3 piętrze stary pickup: (6, UP), nowy pickup: (4, UP)
  ograniczy to przypadki, gdy np. ktoś na 10 piętrze chcący jechać w dół wsiądzie do windy z celem jechania w dół jednakże zobowiązanej najpierw zabrać kogoś z 15 piętra.
  
Algorytm ten może nie sprawdzić się w tłocznych budynkach, ponieważ z powodu dość restrykcyjnych kryteriów zatrzymywania się wind w przypadku
dużej liczby zgłoszeń system nie będzie w stanie wszystkich obsłużyć i zacznie je kolejkować (bardzo możliwe, że mój system kolejkowania jest nieoptymalny - na swoje usprawiedliwienie mogę stwierdzić,
że domyślnie nie powinien być on używany zbyt często, ponieważ decydując się na taki algorytm z góry założyłem, że będzie on używany przede wszystkim w budynkach, w których chcemy zaoszczędzić czas ludzi).

Dodatkowym usprawnieniem mojego systemu jest automatyczna konfiguracja miejsca dla wind, które są aktualnie nieużywane - chcemy aby potencjalne zgłoszenie miało statystycznie jak najbliżej jedną z wind (Z dokładnością,
że 1 z wind zawsze powinna być dostępna na parterze jako potencjalnie najbardziej obciążonym miejscu).

### Uruchomienie projektu:

Projekt został napisany z wykorzystaniem Javy 17, biblioteki JavaFx, frameworka Spring oraz narzędzia do automatyzacji kompilacji Gradle (lokalnie używano wersji 7.5.1).
Po zaciągnięciu projektu do odpowiedniego IDE mającego ustawioną Javę 17 z uruchomieniem nie powinno być problemu.
Obsługa aplikacji jest w 100% manualna włącznie z koniecznością przeklikiwania się przez wyskakujące okienka pytające użytkownika o dane/informujące o sukcesie/porażce wykonanej czynności.
W związku z tym nie zalecam klikania wszystkiego co popadnie dla dużej ilości wind (chyba że dla sprawdzenia, czy nie lecą wyjątki ;)).
