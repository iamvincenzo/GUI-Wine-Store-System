ISTRUZIONI PER UTILIZZO:

	1. Importare libreria javaFX15 come libreria utente.
	2. Inserire i seguenti Arguments da Run configurations:
		
		--module-path "C:\Program Files\Java\javafx-sdk-15.0.1/lib" 
		--add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web 
		--add-reads javafx.graphics=ALL-UNNAMED 
		--add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED 
		--add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED 
		--add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED 
		--add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED 
		--add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED
	
	3. Importare il file jar del connettore mysql su javabuildpath. 

	4. Modificare in MonitorDB la porta di ascolto del server del database se necessario. (default 3306)
	5. Modificare credenziali di accesso al database se necessario. (default user: root, password: root)
	6. Avviare servizio MySQL80 se non attivo nel CMD da amministratore con il comando: net start mysql80
	7. Avvviare WineStoreServer.
	8. Avviare WineStoreClientApplication.


STRUTTURA DATABASE:

	1. Tabella wines: contiene tutti i vini del sistema
	2. Tabella administrator: contiene tutti gli amministratori del sistem.
	3. Tabella employee: contiene tutti gli impiegati del sistema.
	4. Tabella customer: contiene tutti i clienti del sistema.
	5. Tabella orders: contiene tutti gli ordini di tutti i clienti del sistema.
	6. Tabella notification: contiene tutte le notifiche di disponibilità sottoscritte dai clienti del sistema.
	7. Tabella orderedwineemployee: contiene tutti gli ordini dei vini fatti dagli impiegati del sistema.


ADMINISTRATOR:

Dopo aver selezionato il radio button che identifica tale figura, appare una schermata di login. 
Inserendo le credenziali appropriate, si passa alla dashboard degli amministratori.

In tale dashboard si hanno a disposizione quattro tab.

Orders tab: 
è usata dall’amministratore per controllare gli ordini e il loro stato. Per mezzo del tasto 
“view” si possono visualizzare tutti gli ordini oppure gli ordini secondo i criteri di ricerca
solo data, solo id utente o entrambi.

Customers tab: 
consente di visualizzare tutti i clienti registrati nel sistema. 
Consente inoltre di aggiornare i dati dei clienti registrati, di aggiungerne di nuovi o di rimuoverli.

Employees tab: 
consente di visualizzare tutti gli impiegati del sistema. Consente inoltre di aggiornare i dati 
degli impiegati, di aggiungerne di nuovi (assegnando le credenziali) o di rimuoverli.

Wines tab:  
consente di visualizzare tutti i vini del sistema. Consente inoltre di aggiornare i dati dei vini, 
di aggiungere nuovi vini (non duplicati) o di rimuovere dei vini.


EMPLOYEE:

Dopo aver selezionato il radio button che identifica tale figura, appare una schermata di login. 
Inserendo le credenziali appropriate, si passa alla dashboard degli impiegati.

In tale dashboard si hanno a disposizione quattro tab.

Delivery tab: 
consente di visualizzare e spedire gli ordini dei clienti. Il tasto “view” permette di 
visualizzare tutti gli ordini da spedire. Se ci sono ordini da spedire si attiva il tasto 
“send all” per spedirli tutti.
Il tasto “view” insieme ai campi data e id permettono di visualizzare gli ordini da 
spedire in base alla data o all’id del cliente o entrambi.
Il tasto "send select" permette di spedire solo l'ordine selezionato.

Buy finished wine tab: 
questa tab si apre automaticamente dopo il login nel caso in cui sono presenti nel sistema vini 
con giacenza nulla. Selezionando la riga e cliccando su “buy” finished wine si compra il vino 
con giacenza nulla (occorre inserire il numero di bottiglie desiderate nell'opportuno campo). 
Con il tasto “view all” si vedono i vini con giacenza nulla nel caso in cui il vino termina quando 
l’impiegato è già loggato. Occorre inserire il numero di bottiglie da comprare di quel 
vino tramite l’apposito campo.

Buy new wine tab: 
permette di comprare un nuovo vino non ancora presente nel sistema (inserendo tutti i dati
necessari negli appositi campi). Permette anche di 
comprare vini già esistenti nel negozio selezionando una riga della tabella e inserendo 
il numero di bottiglie desiderate.

Update tab: 
Permette di visualizzare i vini comprati dagli impiegati. Quando i vini arrivano in magazzino 
allora l’impiegato seleziona la riga del vino comprato e clicca su upload per caricare il nuovo 
vino o aggiornare il numero di bottiglie del vino esistente.


CUSTOMER:

Dopo aver selezionato il radio button che identifica tale figura, appare una schermata di login.
L’utente non registrato può cercare i vini oppure può registrarsi tramite gli appositi tasti.
Inserendo le credenziali appropriate, si passa alla dashboard degli utenti registrati.

In tale dashboard si hanno a disposizione quattro tab.

Shop tab: 
consente di cercare dei vini tramite gli appositi campi e tasti. Se il vino è disponibile 
o anche non disponibile si attiva il tasto “buy”. Se il numero di bottiglie è adeguato si 
attiva la procedura per l’acquisto. Se non è disponibile viene richiesto all’utente di 
sottoscrivere la notifica di disponibilità.
Inoltre se il cliente non ha idea di che vino cercare può pigiare il tasto "view all" per 
vedere tutti i vini disponibili nel negozio.

History tab: 
questa sezione consente di visualizzare tutto lo storico degli ordini del cliente. 
Il tasto “view” permette di visualizzare tutto lo storico. Tale bottone
consente inoltre di visualizzare lo storico degli ordini in base alla data o al nome 
del vino o entrambi.

Notification tab: 
questa sezione si apre automaticamente se sono disponibili dei vini per cui il cliente 
ha sottoscritto una notifica. Se l’utente ha già effettuato il login allora tramite il tasto 
“view” è in grado di visualizzare tutte le notifiche di disponibilità che il sistema 
gli invia quando il vino è effettivamente disponibile e, consente di 
visualizzare una notifica di disponibilità (quando il vino è disponibile) in base alla data o 
al nome del vino o entrambi. Il tasto “delete notification”, permette di eliminare una notifica. 

Account tab: 
in questa sezione l’utente può modificare i propri dati.


I tasti logout ed exit situati in tutte le schermate in alto a destra/sinistra servono per effettuare 
la disconnessione dei client.