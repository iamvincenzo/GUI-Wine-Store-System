# GUI-Wine-Store-System
Java-Project

Assignement 3 - Software Engineering UniPR

L’obiettivo è definire i diagrammi UML dei casi di uso e delle classi, che documentano un sistema
software per la vendita online di bottiglie di vino, e implementare il sistema in Java, utilizzando in
modo appropriato le tecniche di riferimento della programmazione orientata agli oggetti.
I vini, sono identificati da: nome, produttore, anno, note tecniche, e i vitigni da cui derivano. Si noti
che il sistema deve anche tenere traccia del numero di bottiglie di ogni vino per ogni annata prodotta.
Il sistema interagisce con utenti (persone che vogliono acquistare del vino) e impiegati (persone che
gestiscono la vendita). Ogni persona è identificata da nome, cognome e indirizzo email.
Un utente si può registrare, fare ricerca dei vini per nome e anno di produzione e acquistare bottiglie
di vino dopo un accesso autenticato. Un dipendente può spedire le bottiglie di vino ai clienti e
rimpiazzare le bottiglie di vino vendute.
Ogni volta che un utente fa un ordine per un certo numero di bottiglie di un dato vino, l’operazione
deve essere memorizzata dal sistema che inoltre provvede a decrementare il numero di bottiglie che
possono essere messe in vendita. Se la giacenza diventa nulla, il sistema deve segnalare l’esaurimento
delle scorte e l’impiegato deve quindi acquisire un numero adeguato di bottiglie di quel tipo di vino
e, all’arrivo delle bottiglie, aggiornare i dati sul sistema.
Nel caso in cui le bottiglie di un particolare vino non sono disponibili, allora un utente può
sottoscrivere una richiesta di notifica sulla disponibilità del numero di bottiglie che vorrebbe
acquistare: quando le bottiglie richieste saranno disponibili, il sistema notificherà la loro disponibilità
all’utente.
Il sistema sarà gestito dalla figura dell’amministratore che avrà il compito di assegnare le
credenziali ai dipendenti e visualizzare le informazioni sui clienti, dipendenti, vini e ordini.
Il sistema ha una struttura client server. Un server centrale mantiene i dati su clienti, dipendenti, vini
e ordini in un database relazionale. I client permettono agli utenti e ai dipendenti di interagire con il
server tramite un’interfaccia grafica. Più utenti, clienti e amministratori possono accedere in
concorrenza al server. 
