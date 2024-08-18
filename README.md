# Projects for the course - Java Advanced Features 2023

Description of each laboratory (polish):

***Lab01***

Napisz aplikację, która pozwoli na sprawdzania wskazanych katalogów pod kątem wystąpienia zmian w zawartych w nich plikach. Sprawdzenie zmian odbywać się ma poprzez wyliczanie funkcji skrótu MD5. 

Na początek alikacja powinna wyliczyć funkcję skrótu MD5 dla każdego badanego pliku i gdzieś ją zapamiętać. W ten sposób powstać ma "snapshot" bieżącej sytuacji. Następnie, podczas sprawdzania zmian, aplikacja powinna ponownie wyliczyć funkcję skrótu MD5 dla każdego badanego pliku i porównać ją z wartością wcześniej zapamiętaną. W ten sposób powstać ma "nowy snapshot". Na podstawie różnić między zapamiętanym "snapshotem" a "nowym snapshotem" można określić, czy w badanych plikach wprowadzono jakieś zmiany. Oczywiście po sprawdzeniu "snapshot" powinien być zastąpiony "nowym snapshotem".

Można przyjąć różne strategie co do sposobu zapamiętywania "snapshotu". Zalecane jest, by aplikacja składowała snapshoty w katalogu domowym użytkownika (np. w katalogu ".snapshot"). 
W ten sposób można zapamiętywać skróty plików w katalogach, które można "czytać", ale nie można w nich "pisać".

Innym sposób mógłby polegać na wstawianiu w przeglądanych katalogach dodatkowych plików ze skrótami o nazwach odpowiadających przeglądanym plikom (np. "oryginalna_nazwa_pliku.md5"). Jednak ten sposób nie pozwoli na przeglądanie katalogów tylko do "odczytu".

Aplikacja powinna być napisana z wykorzystaniem modułów (wprowadzonych w Javie od jdk 9). Powstać ma moduł biblioteki oraz moduł samej aplikacji (korzystający z modułu biblioteki). 

Powstałe moduły należy wyeksportować do plików jar.

Używając jlink należy przygotować minimalne środowisko uruchomieniowe, do którego podpięte zostaną wymienione wyżej moduły.

Aplikację powinno dać się uruchomić z linii komend, korzystając tylko z wygenerowanego środowiska uruchomieniowego. Sama aplikacja powinna oferować interfejs użytkownika (najlepiej graficzny, minimum - tekstowy).

Do operacji na plikach i katalogów należy wykorzystać pakiet klas java.nio. Ponadto zalecane jest napisanie programu w stylu funkcjonalnym.


***Lab02***

Napisz aplikację, która umożliwi przeglądanie danych pomiarowych przechowywanych na dysku w plikach csv (w wersji minimum).
Zakładamy, że pliki danych będą składowane w folderach o nazwach reprezentujących daty kampanii pomiarowych (np. nazwą folderu może być 07.03.2023), zaś nazwy samych plików będą odpowiadały identyfikatorowi stanowiska pomiarowego oraz godzinie rozpoczęcia pomiarów (np. nazwą pliku może być 0001_10:00). 
Zawartość plików powinna odpowiadać następującemu schematowi:

godzina pomiaru; ciśnienie [hPa];  temperatura [stopnie C]; wilgotność [%]
10:00; 960,34; -1,2; 60;
10:02; 960,34; -1,2; 60;
....

Pliki danych można stworzyć do testów posługując się generatorami losowymi czy innymi narzędziami.

Interfejs graficzny aplikacji powinien składać się z dwóch paneli - jednego, przeznaczonego do nawigacji po folderach i plikach, oraz drugiego, służącego do wyświetlania podglądu zawartości aktualnie wybranego pliku z danymi (wyświetlanych może być kilka pierwszych linijek) oraz wyników przetworzenia danych (wartości średnich dla kolejnych kolumn, poza kolumną z godziną pomiaru).

Aplikację należy zaprojektować z wykorzystaniem słabych referencji (ang. weak references). Zakładamy, że podczas przeglądania folderów z plikami danych pomiarowych zawartość aktualnie wybranego pliku będzie ładowana do odpowiedniej struktury oraz zostanie przetworzona (celem wyliczenia odpowiednich wartości). Słabe referencje powinny pozwolić na ominięcie konieczności wielokrotnego ładowania i przetwarzania tej samej zawartości - co może nastąpić podczas poruszanie się wprzód i wstecz po liście plików w folderach.

Aplikacja powinna wskazywać, czy zawartość pliku została załadowana ponownie, czy też została pobrana z pamięci. Wskazanie to może być zrealizowane za pomocą jakiegoś znacznika prezentowanego na interfejsie.

W celu oceny poprawności działania aplikację należy uruchamiać przekazując wirtualnej maszynie parametry ograniczające przydzielaną jej pamięć. Na przykład -Xms512m (co oznacza minimalnie 512 MB pamięci), -Xmx1024m (co oznacza maksymalnie 1GB).
Należy też przetestować możliwość regulowania zachowania się algorytmu odśmiecania, do czego przydają się opcje -XX:+ShrinkHeapInSteps, -XX:-ShrinkHeapInSteps. Proszę przestudiować, jakie inne atrybuty można przekazać do wirtualnej maszyny, w tym selekcji algorytmu -XX:+UseSerialGC, -XX:+UseParNewGC (deprecated), -XX:+UseParallelGC, -XX:+UseG1GC.

Architektura aplikacji powinna umożliwiać dołączanie różnych algorytów przetwarzania danych, jak również algorytmów renderujących fragment zawartości przeglądanego pliku (zakładamy, że aplikację będzie można rozbudować w celu przeglądania danych zapisanych w plikach o innym schemacie zawartości czy też innym formacie danych).

Proszę dodać do źródeł plik readme.md z wnioskami co do stosowalności opcji wirtualnej maszyny.

Proszę sięgnąć do materiałów http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/UnderstandingWeakReferences.pdf

***Lab03***

Napisz aplikację, która pozwoli skonsumować dane pozyskiwane z serwisu oferującego publiczne restowe API. Ciekawą listę serwisów można znaleźć pod adresem:
https://rapidapi.com/collection/list-of-free-apis (wymagają klucza API), czy też https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/ (te klucza API nie wymagają). W szczególności w tej drugiej grupie istnieje: https://musicbrainz.org/doc/MusicBrainz_API. Właśnie to api ma być użyte w aplikacji.

Bazując na wskazanym api należy zbudować aplikację z graficznym interfejsem użytkownika, pozwalającą na przeprowadzanie testów z ogólnej wiedzy o utworach muzycznych, ich wydaniach i twórcach itp. Renderowanie zapytań i odpowiedzi powinno być tak zaimplementowane, by dało się zmianić ustawienia językowe (lokalizacji) w oparciu o tzw. bundle (definiowane w plikach i klasach - obie te opcje należy przetestować). Wspierane mają być języki: polski i angielski. 

Proszę zapoznać się z api i zaproponować kilka schematów zapytań i pól odpowiedzi. Niech zapytania będą parametryzowane wartościami pochodzącymi z list wyboru wypełnionych treścią pozyskaną z serwisu, a odpowiedzi niech będą uzupełniane wolnym tekstem lub wartościami z list wyboru (jeśli "charakter" pytania jest, odpowiednio, otwarty lub zamknięty). 
Odpowiedzi podanawane przez użytkownika powinny być weryfikowane przez aplikację (aplikacja, po wysłaniu zapytania przez api powinna sprawdzić, czy wynik tego zapytania jest zgodny z odpowiedzią udzieloną przez użytkownika).

Przykłady szablonów zapytania i odpowiedzi: 
Przykład 1:
 Pole zapytania: "Kto jest autorem piosenki ... ?" (w miejsce kropek aplikacja powinna wstawić jakiś tytuł pobrany z serwisu)
 Pole odpowiedzi: "..."  (miejsce na wpisanie autora).
 Pole weryfikacji (dla poprawnej odpowiedzi): "Tak, masz rację. Autorem piosenki .... jest ..." (to ma wypełnić sama aplikacja, przy czym można się zastanowić nad tym, jak ma przebiegać weryfikacja, gdy np. autorów jest więcej niż jeden).

Przykład 2:
Na przykład dla szablonu zapytania zapytania: 
 Pole zapytania: "Ile albumów wydał zespół ... ?" (w miejsce kropek aplikacja powinna wstawić jakiś tytuł pobrany z serwisu)
 Pole odpowiedzi: "..."  (miejsce na wpisanie liczby).
 Pole weryfikacji (dla poprawnej odpowiedzi): "Tak, masz rację. Zespół ... wydał ... albumów" (to ma wypełnić sama aplikacja, przy czym trzeba obsłużyć odmianę przez liczby). 
 
Proszę pamiętać o obsłudze przynajmniej dwóch języków na interfejsie. Do tego proszę zastosować wariantowe pobieranie tekstów z bundli. Do tego przyda się klasa ChoiceFormat. 

Opcjonalnie można wykorzystać jakieś inne api, jeśli tylko zachowa się przedstawioną wyżej koncepcję (parametryzowane szablony zapytań, do wypełnienia pola odpowiedzi, linijka weryfikacji z odmianą przez liczby/osoby - wszystko przynajmniej z obsługą dwóch języków: polski i angielski).

W sieci można znaleźć implementacje klientów do MusicBrainz napisane w różnych językach (https://musicbrainz.org/doc/MusicBrainz_API). Klient napisany w języku Java (https://code.google.com/archive/p/musicbrainzws2-java/) jest już nieco stary, bo z 2012 roku. Dlatego warto na laboratoriach napisać własnego klienta, dostosowanego do treści zadania (nie trzeba implementować wszystkich możliwych funkcji, a jedynie te, które potrzebne są do pozyskania danych zamieszczanych w szablonach pytań).


***Lab04***

Napisz aplikację, która umożliwi zlecanie wykonywania zadań instancjom klas ładowanym własnym ładowaczem klas. Do realizacji tego ćwiczenia należy użyć Java Reflection API z jdk 17.

Tworzona aplikacja powinna udostępniać graficzny interfejs, na którym będzie można:
1. zdefiniować zadanie (zakładamy, że będzie można definiować "dowolne" zadania reprezentowane przez ciąg znaków),
2. załadować klasę wykonującą zadanie (zakładamy, że będzie można załadować więcej niż jedną taką klasę),
3. zlecić wykonanie wskazanego zadania wskazanej załadowanej klasie, monitorować przebieg wykonywania zadania, wyświetlić wynik zadania.
4. wyładować wybraną klasę z wcześniej załadowanych

Realizacja zadania powinna opierać się na wykorzystaniu API (klas i interfejsów) zdefiniowanych w archiwum Refleksja02.zip.

Należy dostarczyć przynajmniej 3 różne klasy implementujące interfejs Processor. Każda taka klasa po załadowaniu powinna oznajmić, poprzez wynik metody getInfo(), jakiego typu zadanie obsługuje. Na przykład pozyskana informacja w postaci "sumowanie" oznaczałaby, że zadanie można zdefiniować ciągiem znaków "1 + 2", gdzie oczekiwanym wynikiem byłoby "3". Informacja "zamiana małych liter na duże" oznaczałaby, że dla zadania "abc" oczekiwanym wynikiem ma być "ABC".

Klasy ładowane powinny być skompilowane w innym projekcie niż sama użytkowa aplikacja (podczas budowania aplikacja nie powinna mieć dostępu do tych klas). Można założyć, że kod bajtowy tych klas będzie umieszczany w katalogu, do którego aplikacja będzie miała dostęp. Ścieżka do tego katalogu powinna być parametrem ustawianym w aplikacji w trakcie jej działania. Wartością domyślną dla ścieżki niech będzie katalog, w którym uruchomiono aplikację. Aplikacja powinna odczytać zawartość tego katalogu i załadować własnym ładowaczem odnalezione klasy. Zakładamy, że podczas działania aplikacji będzie można "dorzucić" nowe klasy do tego katalogu (należy więc pomyśleć o pewnego rodzaju "odświeżaniu").

Wybieranie klas (a tym samym algorytmów przetwarzania) powinno odbywać się poprzez listę wyświetlającą nazwy załadowanych klas. Nazwom tym niech towarzyszą opisy pozyskane metodą getInfo() z utworzonych instancji tych klas.

Zlecanie zadań instancjom klas powinno odbywać się poprzez metodę submitTask(String task, StatusListner sl).  W metodzie tej należy podać słuchacza typu StatusListener, który będzie otrzymywał informacje o zmianie statusu przetwarzania. Do reprezentacji statusu przetwarzania służy klasa Status (klasę tę zadeklarowano tak, aby po utworzeniu statusu jego atrybuty były tylko do odczytu). 

Proszę przetwarzanie zaimplementować w wątku z opóźnieniami, by dało się monitorować bieżący status przetwarzania. Do monitorowania statusów przetwarzania i wyświetlania wyników można użyć osobnej listy (monitora zadań) wyświetlanej na interfejsie aplikacji.

Proszę napisać własny (!) ładowacz klas. Ładowacz ten może być klasą, do której przekazana zostanie ścieżka położenia kodów bajtowych ładowanych klas z algorytmami przetwarzania. Własny ładowacz nie może rozszerzać klasy URLClassLoader.

Ponieważ aplikacja ma być rozwijana w jdk17 należy zastanowić się, jak w takim przypadku obsłużyć warstwy modułowe (podpinając im ładowacze klas).

Klasy załadowane powinno dać się wyładować. Proszę pamiętać, że klasa zostanie wyładowana, gdy znikną wszystkie referencje od jej instancji oraz gdy zniknie referencja do ładowacza, który tę klasę załadował (i zadziała odśmiecanie). Można monitorować liczbę załadowanych i wyładowanych klas za pomocą jconsole lub jmc.

Proszę zajrzeć do materiałów wymienionych przy wykładzie o refleksji i ładowaczach klas.

***Lab05***

Zaimplementuj aplikację z graficznym interfejsem pozwalającą przeprowadzić analizę statystyczną wyników klasyfikacji zgromadzonych w tzw. tabelach niezgodności (ang. confusion matrix). Obliczać należy współczynnik kappa oraz przynajmniej trzy inne miary (np. miary opisane w artykule:
https://arxiv.org/pdf/2008.05756.pdf).
Aplikacja powinna pozwalać na:
- wyświetlanie/edytowanie danych tabelarycznych;
- wybranie algorytmu, jakim będą one przetwarzane;
- wyświetlenie wyników przetwarzania (obliczonej miary).
W trakcie implementacji należy wykorzystać interfejs dostarczyciela serwisu (ang. Service Provider Interface, SPI). Dokładniej, stosując podejście SPI należy zapewnić aplikacji możliwość załadowania klas implementujących zadany interfejs już po zbudowaniu samej aplikacji. 
Klasy te (z zaimplementowanymi algorytmami wyliczającymi miary służące do oceny wyników klasyfikacji) mają być dostarczane w plikach jar umieszczanych w ścieżce. Należy stworzyć dwie wersje projektu: standardową oraz modularną.

Aby zapoznać się z problemem proszę sięgnąć do przykładowych projektów w archiwum udostępnionym pod adresem:
    http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/WorkspaceServiceProviderInterface.zip

W implementacji należy wykorzystać pakiet ex.api, zawierającym klasy o kodzie źródłowy pokazanym poniżej.

Trochę informacji o SPI można znaleźć pod adresem:
    https://www.baeldung.com/java-spi
Porównanie SPI ze SpringBoot DI zamieszczono pod adresem:
    https://itnext.io/serviceloader-the-built-in-di-framework-youve-probably-never-heard-of-1fa68a911f9b

```java
--------------------------------
package ex.api;

/**
 * Interfejs serwisu pozwalającego przeprowadzić analizę statystyczną.
 * Zakładamy, że serwis działa asynchronicznie.
 * Na początek należy do serwisu załadować dane.
 * Potem można z serwisu pobrać wyniki analizy.
 * W przypadku niepowodzenia wykonania jakiejś metody wyrzucony zostanie wyjątek.
 * 
 * @author tkubik
 *
 */
public interface AnalysisService {
    // metoda ustawiająca opcje algorytmu (jeśli takowe są potrzebne)
	public void setOptions(String[] options) throws AnalysisException; 
	// metoda zwracająca nazwę algorytmu
	public String getName();                                   
	// metoda przekazująca dane do analizy, wyrzucająca wyjątek jeśli aktualnie trwa przetwarzanie danych
	public void submit(DataSet ds) throws AnalysisException; 
	// metoda pobierająca wynik analizy, zwracająca null jeśli trwa jeszcze przetwarzanie lub nie przekazano danych do analizy
	// wyrzucająca wyjątek - jeśli podczas przetwarzania doszło do jakichś błędów
	// clear = true - jeśli wyniki po pobraniu mają zniknąć z serwisu
    public DataSet retrieve(boolean clear) throws AnalysisException;   
}
--------------------------------

package ex.api;

public class AnalysisException extends Exception {
	private static final long serialVersionUID = 1L;
    AnalysisException(String msg){
    	super(msg);
    }
}

--------------------------------
package ex.api;
/**
 * Klasa reprezentująca zbiór danych w postaci tabelarycznej.
 * Przechowuje nagłówek (jednowymiarowa tablica z nazwami kolumn) 
 * oraz dane (dwuwymiarowa tablica, której wiersze reprezentują wektory danych).
 * Zakładamy, że będą zawsze istnieć przynajmniej dwie kolumny o nazwach:
 * "RecordId" - w kolumnie tej przechowywane są identyfikatory rekordów danych;
 * "CategoryId" - w kolumnie tej przechowywane są identyfikatory kadegorii rekordów danych (wynik analizy skupień).
*
 * @author tkubik
 *
 */
public class DataSet {
	private String[] header = {}; 
	private String[][] data = {{}};

	private <T> T[][] deepCopy(T[][] matrix) {
	    return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray(i -> matrix.clone());
	}
	
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header.clone();
	}
	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = deepCopy(data);
	}
}
--------------------------------
```

***Lab06***

Napisz program, który pozwoli zasymulować działanie narzędzia do obsługi klientów firmy będącej dostawcą Internetu (zakładamy, że program będzie działał w kontekście dostawcy Internetu).

Zadanie należy zrealizować wykorzystując relacyjną bazę danych. 
Zalecane jest użycie sqlite (jest to baza danych w pliku, nie ma potrzeby istalowania żadnego dodatkowego serwisu). 

Podczas realizacji zadania należy skorzystać z mapowania ORM (technologia JPA, do czego można użyć framework Hibernate). Wtedy jednak należy zastosować wzorzec projektowy z użyciem serwisów. Generalnie projekt może być zrealizowany z użyciem frameworka Spring Boot.

Zakładamy, że w bazie danych będą przechowywane następujące informacje (jest to model mocno uproszczony w porównaniu do modeli spotykanych w rzeczywistości):
    Kient - imię, nazwisko, numer klienta
    Instalacja - adres, numer routera, typ usługi
    Naliczone należności - termin płatności, kwota do zapłaty
    Dokonane wpłaty - termin wpłaty, kwota wpłaty
    Cennik - typ usługi, cena 

Dla jednego klienta może być założonych wiele instalacji. 
Należności i wpłaty dotyczą danej instalacji. 
Należności naliczane są w trybie miesięcznym. 
Cennik dotyczy wszystkich typów usługi (aktywnych, wygasłych, nowych).


Program powinien:
    - umożliwiać ręczne zarządzanie danymi klientów, danymi instalacji oraz cennikiem
    - automatycznie naliczać należności i wysyłać monity o kolejnych płatnościach (wystarczy, że będzie pisał do pliku z logami monitów, upływ czasu należy zasymulować).
    - automatycznie eskalować monity w przypadku braku terminowej wpłaty (wystarczy, że będzie pisał do pliku z logami ekalowanych monitów, upływ czasu należy zasymulować)
    - umożliwiać ręczne rejestrowanie wpłat oraz nanoszenie korekt
    - umożliwiać przeglądanie należności i wpłat
 
Program może działać w trybie konsolowym, choć dużo lepiej wyglądałoby stworzeni interfejsu graficznego.

***Lab07***

Zamień aplikację napisaną podczas poprzedniego labotatorium na usługę sieciową. Niech ta usługa będzie oferowała restowy interfejs pozwalający na wykonanie wszystkich operacji związanych z obsługą klientów firmy będącej dostawcą Internetu, a jej implementacja niech będzie wykonana z pomocą frameworka Spring Boot.

Implementacja może odbywać się według podejścia top-down (czyli najpierw definicja interfejsu wykonana w Swagger Editor, a potem wygenerowanie kodu źródłowego interfejsów i ich implementacja), albo według podejścia bottom-up (czyli najpierw redagowanie kodu źródłowego interfejsów i ich implementacja, a potem wygenerowanie swaggerowego opisu).

Efektem ma być tzw. backend, którego działanie będzie można:
* przetestować za pomocą swaggerowego interfejsu (automatycznie wygenerowanej strony internetowej, dającej możliwość sparametryzowania zapytań protokołu HTTP, ich wysłania na endpoint oraz wyświetlenia wyniku);
* przetestować za pomocą zewnętrznych narzędzi jak: Postman czy SoapUI.

Reszta wymagań zgodnie z ustaleniami poczynionymi na laboratorium.


***Lab08***

Napisz program, w którym wykorzystane zostanie JNI. Zadanie do wykonania polegać ma na zadeklarowaniu klasy Java z dwiema metodami służącymi do obliczania dyskretnej dwuwymiarowej funkcji splotu  (ang. 2D discrete convolution function): natywną oraz normalną, a następnie przetestowaniu ich działania.

Metoda do obliczania splotu powinna przyjmować na wejście dwie tablice dwuwymiarowe (jądro splotu oraz macierz przetwarzaną) oraz produkować na wyjściu wynik (macierz wynikową). O tym, czym jest funkcji splotu, poczytać sobie można w wielu tutorialach, np. w dokumencie https://staff.uz.zgora.pl/agramack/files/conv/Splot.pdf 

Podczas testowania należy wygenerować odpowiednio duży problem obliczeniowy, a potem spróbować go rozwiązać korzystająć z obu zaimplementowanych metod. Testowanie powinno dać odpowiedź na pytanie, która z implementacji jest wydajniejsza. Stąd uruchamianiu metod powinno towarzyszyć mierzenie czasu ich wykonania (czyli coś na kształt testów wydajnościowych). Sposób wykonania takich testów jest dowolny, jednak warto spróbować wykorzystać do tego np. framework Mockito.

Jako wynik zadania, oprócz kodów źródłowych Java oraz kodu zaimplementowanej natywnej biblioteki, należy dostarczyć również raport z omówieniem wyników testowania. Proszę zwrócić uwagę na to, czy podczas testów nie uwidoczniło się działanie JIT.


***Lab09***

Podczas laboratoriów należy przećwiczyć różne sposoby przetwarzania dokumentów XML. Aby zadanie nie było zbyt skomplikowane, a jednocześnie umożliwiało zapoznanie się z różnymi technologiami, zdefiniowano je w następujący sposób:

Napisz program pozwalający:
1. wczytać dane XML korzystając z JAXB i wyrenderować je na ekranie (tutaj należy zdefiniować klasę mapującą się do struktury dokumentu XML oraz skorzystać z serializatora/deserializatora).
2. wczytać dane XML korzystając z JAXP i wyrenderować je na ekranie (tutaj należy uruchomić parsery SaX i DOM).
3. przetworzyć dane z dokumentu XML za pomocą wybranego arkusza transformacji XSLT i wyświetlić wynik tego przetwarzania. Przetwarzanie może polegać na wygenerowaniu i wyrenderowaniu htmla powstałego na skutek zaaplikowania wybranego arkusza XSLT do dokumentu XML (arkusz może "wycinać" niektóre elementy, "wyliczać" jakieś wartości, ostylowywać tekst itp.). Program powinien umożliwić wybór używanego arkusza spośród kilku arkuszy składowanych w zadanym miejscu. Chodzi tu, między innymi, o to, by dało się zmienić "działanie programu" już po jego kompilacji (poprzez podmianę/wskazanie używanego arkusza).

Dane XML do przetwarzania mają pochodzić z publicznie dostępnych repozytoriów (z licencjami pozwalającymi na ich użytkowanie przynajmniej do celów edukacyjnych). Na przykład mogą do być dane opublikowane pod linkami:
https://bip.poznan.pl/api-xml/bip/dane-o-srodowisku-i-ochronie/A/,
https://catalog.data.gov/dataset/popular-baby-names,
https://www.floatrates.com/feeds.html,
Dane mogą dotyczyć np. wyników piłkarskich rozgrywek ligowych (często dane te udostępniane są na komercyjnych zasadach, ale istnieją też darmowe przykłady do pobrania, jak: http://xmldocs.sports.gracenote.com/XML-Downloads-and-Samples_1048597.html)

Istnieje coś takiego jak "xml feeds"(strumienie danych XML), w szczególności "atom feeds" - ale to jest temat do osobnego omówienia (patrz https://www.ibm.com/docs/en/cics-ts/5.5?topic=support-overview-atom-feeds).

***Lab10***

Zaimplementuj aplikację pozwalającą na szyfrowanie/deszyfrowanie plików (taka aplikacja mogłaby, na przykład pełnić, rolę narzędzia służącego do szyfrowania/odszyfrowywania załączników do e-maili). 
Na interfejsie graficznym aplikacji użytkownik powinien mieć możliwość wskazania plików wejściowych oraz wyjściowych, jak również algorytmu szyfrowania/deszyfrowania oraz wykorzystywanych kluczy: prywatnego (do szyfrowania) i publicznego (do deszyfrowania).
Cała logika związana z szyfrowaniem/deszyfrowaniem powinna być dostarczona w osobnej bibliotece, spakowanej do podpisanego cyfrowo pliku jar.
Aplikacja powinna być wyeksportowana do wykonywalnego pliku jar podpisanego cyfrowo.
(do przedyskutowania na początku zajęć jest, czy działać w niej ma menadżer bezpieczeństwa korzystający z dostarczonego pliku polityki).
Projekt opierać ma się na technogiach należących do Java Cryptography Architecture (JCA) i/lub Java Cryptography Extension (JCE).
Proszę zwrócić uwagę na ograniczenia związane z rozmiarem szyfrowanych danych narzuczane przez wybrane algorytmu (zależy nam, by zaszyfrować dało się pliki o dowolnym rozmiarze).
W trakcie realizacji laboratorium będzie trzeba skorzystać z repozytoriów kluczy i certyfikatów.  Ponadto proszę zapoznać się z zasadami korzystania z narzędzia jarsigner. 
Proszę w gitowym repozytorium kodu w gałęzi sources/releases stworzyć osobne podkatalogi: na bibliotekę (biblioteka) oraz na aplikację (aplikacja).

***Lab11***

Bazując na kodzie stworzonym w trakcie poprzednich laboratoriów przygotuj: a) wielowydaniowy jar, b) instalator aplikacji. Opis czym jest wielowydaniowy jar pojawi się na wykładzie. Towarzyszące mu materiały znaleźć można na stronie poświęconej kursowi. Instalator aplikacji może być wykonany dowolnym narzędziem. Ponadto dla ciekawych polecane jest sprawdzenie, jak działa narzędzie jpackage. 

Oprócz źródeł kodu w gitlabowym repozytorium proszę również umieścić krótki raport z realizacji zadania.


***Lab12***

Celem laboratorium jest przetrenowanie możliwości dynamicznego rozszerzania funkcji programu Java przez ładowanie i wyładowywanie skryptów JavaScript (na podobieństwo ładowania klas własnym ładowaczem). Ponadto chodzi w nim o opanowanie technik przekazywania obiektów pomiędzy wirtualną maszyną Java a silnikiem JavaScript.

Na początek proszę przeczytać parę uwag:

Rozwój silnika JavaScript, odbywający się w ramach rozwoju JDK, został w pewnym momencie zatrzymany przez Oracle. Stwierdzono bowiem, że zadanie to realizowane jest w projekcie GraalVM, a szkoda poświęcać zasoby na powielanie prac. Efekt podjętej wtedy decyzji widać było już w JDK 11, gdzie odpowiedni moduł dostarczający rzeczony silnik otagowano:
    @Deprecated(since="11", forRemoval=true)
    Module jdk.scripting.nashorn
W wersji JDK 17 tego modułu już nie ma. Przy okazji usunięto też niektóre pomocnicze narzędzia, jak uruchamiane z linii komend interpreter jjs. Co więcej, dla aplikacji JavaFX skryptowanie zostało domyślnie wyłączone. Aby je włączyć, należy przekazać wirtualnej maszynie odpowiednią opcję: -Djavafx.allowjs=true

Aby dało się "skryptować" w nowszych wersjach JDK, oprócz włączenia odpowiednich opcji, można:
1. umieścić we własnym projekcie zależność do rozwijanego niezależnie silnika nashorn (czyli do JDK dołożyć silnik nashorn)
2. umieścić we własnym projekcie zależność do rozwijanego niezależnie silnika graal.js (czyli do JDK dołożyć silnik graal.js - możliwe jest też przy okazji dołączenie kompilatora JIT z dystrybucji GraalVM)
2. stworzyć projekt wykorzystując GraalVM zamiast JDK

GraalVM Community Edition wersja 22.3.2 (obecnie najnowsza, patrz https://www.graalvm.org/downloads/) zbudowano na bazie OpenJDK 11.0.17 oraz OpenJDK 17.0.5. 

Niezależna implementacja silnika nashorn - patrz https://github.com/openjdk/nashorn

Archiwym z czterema projektami: skryptowanie.zip (http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/skryptowanie.zip)

Zadanie do wykonania polega na napisaniu programu w języku Java pozwalającego na wizualizację działania automatów komórkowych, przy czym logika działania tych automatów powinna być zaimplementowana za pomocą ładowanych dynamicznie skryptów JavaScript zapisanych w plikach o znanej lokalizacji. Nazwy plików ze skryptami powinny odpowiadać nazwom automatów - by było wiadomo, co robi ładowany skrypt. Załadowane skrypty powinno dać się wyładować.

Opis przykładowych automatów komórkowych opublikowano na wiki: https://pl.wikipedia.org/wiki/Automat_kom%C3%B3rkowy.
Materiały pomocnicze można znaleźć pod adresami:
http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html
https://www.n-k.de/riding-the-nashorn/

Proszę w gitowym repozytorium kodu w gałęzi sources umieścić wszystkie wykorzystywane artefakty (skrypty JavaScript oraz źródła kodu Java). Proszę też zamieścić instrukcję użycia programu wraz z udokumentowanym wynikiem jego działania (plik Readme.md z dołączomymi zrzutami z ekranu).


***Lab13***

Znając zasady korzystania ze skryptów JS w programach napisanych w języku JavaFX można pójść o krok dalej w zabawie z tymi technologiami. Zdobytą wiedzę można wykorzystać podczas budowy aplikacji z graficznym interfejsem użytkownika opierającym się na klasach JavaFX.

Zadanie polega na zaimplementowaniu aplikacji korzystającej z JavaFX w niestandardowy sposób. Ma to polegać na oskryptowaniu całej logiki w pliku fxml zamiast w kontrolerze napisanym w języku Java. W języku Java ma być zaimplementowane tylko ładowanie fxmla. Proszę spojrzeć na przykłady zamieszczone poniżej (Sample1.fxml, Sample1.java, application.css). 

Sama aplikacja ma pozwalać na generowanie wpisów do pamiętnika na podstawie spreparowanych wcześniej szablonów uzupełnianych niezbędnymi atrybutami (datą, imieniem, ...). Wygenerowane wpisy mogą pojawiać się na interfejsie użytkownika jako tekst, który powinno dać się skopiować. Szablony powinny być zapisane w plikach konfiguracyjnych.

Do przemyślenia jest, w jaki sposób użytkownik ma przekazywać do aplikacji niezbędne atrybuty (liczba i typ atrybutów może zależeć od rodzaju szablonu).

W gitowym repozytorium w gałęzi sources należy umieścić wszystkie źródła plus plik Readme.md (z dołączonymi zrzutami i opisem, jak uruchomić aplikację), w gałęzi releases - wykonywalny plik jar (z przygotowaniem jara może być problem - trzeba sprawdzić, czy pliki fxml, css oraz szablonów będą się ładować z tego jara, ostatecznie można je dystrybuować osobno).

```java
########### Sample1.fxml  ###########
<?xml version="1.0" encoding="UTF-8"?>
<?language javascript?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<VBox fx:id="vbox" layoutX="10.0" layoutY="10.0" prefHeight="250.0"
	prefWidth="300.0" spacing="10" xmlns:fx="http://javafx.com/fxml/1"
	xmlns="http://javafx.com/javafx/2.2">
	
	<fx:script>
	var System = Java.type("java.lang.System")
		function buttonAction(event){
		java.lang.System.out.println("finally we get to print something.");
		outputLbl.setText("Your Input please:");
		}
	</fx:script>
	<children>

		<Label fx:id="inputLbl" alignment="CENTER_LEFT" cache="true"
			cacheHint="SCALE" prefHeight="30.0" prefWidth="200.0"
			text="Please insert Your Input here:" textAlignment="LEFT" />
		<TextField fx:id="inputText" prefWidth="100.0" />
		<Button fx:id="okBtn" alignment="CENTER_RIGHT"
			contentDisplay="CENTER" mnemonicParsing="false"
			onAction="buttonAction(event);" text="OK" textAlignment="CENTER" />

		<Label fx:id="outputLbl" alignment="CENTER_LEFT" cache="true"
			cacheHint="SCALE" prefHeight="30.0" prefWidth="200.0"
			text="Your Input:" textAlignment="LEFT" />
		<TextArea fx:id="outputText" prefHeight="100.0"
			prefWidth="200.0" wrapText="true" />
	</children>
</VBox>

########### Sample1.java  ###########
package application;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sample1 extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		String fxmlDocPath = "src/Sample1.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

		VBox root = (VBox) loader.load(fxmlStream);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("A FXML Example without any Controller");
		stage.show();

	}
}
########### application.css  ###########
/* JavaFX CSS - Leave this comment until you have at least create one rule which uses -fx-Property */
.pane {
    -fx-background-color: #1d1d1d;
}

.root {
		-fx-padding: 10;
		-fx-border-style: solid inside;
		-fx-border-width: 2;
		-fx-border-insets: 5;
		-fx-border-radius: 5;
		-fx-border-color: #2e8b57;
		-fx-background-color: #1d1d1d;
}
.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 0.6;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.button {
    -fx-padding: 5 22 5 22;   
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color,30%);
}

```



***Lab14***

Napisz program, który będzie grał rolę sterownik zapalającego światła w pomieszczeniach budynku (np. ramka z kilkoma kółkami reprezentującymi punkty oświetlenia). Zadaniem sterownika jest symulowanie "aktywności" podczas nieobecności gospodarzy (czyli odpalanie świateł w jakichś sekwencjach).
Zanurz w tym programie agenta obsługującego ziarenko JMX, które pozwala na zmianę sekwencji, uruchamianie lub zatrzymywanie symulacji, bądź też na indywidualne włączane/wyłączanie świateł. Ziarenko powinno generować notyfikacje przy włączeniu/wyłączeniu każdego punktu oświetlenia.
Niech więc ziareno ma:
- właściwość (pozwalającą na ustawianie/odczytywanie sekwencji),
- metodę (włączającą/wyłączającą dane źródło światła),
- metodę (uruchamiającą/zatrzymującą symulację)
- notyfikację (generowaną przy włączeniu/wyłączeniu punktów oświetlenia - i to zarówno przez użytkownika jak i przez ziarenko).
Uruchom program oraz podłącz się do niego z jmc lub jconsole by wykorzystać agenta.
