# Proiect Programare Avansata pe Obiecte
## Simion Cristian
Am ales sa implementez o agenda personala.
 
## Descrierea sistemului
In cadrul sistemului am folosit urmatoarele clase:
1. Agenda
2. AgendaService
3. Contact
4. Note
5. Privacy
6. ScheduledTask
7. ScheduledTaskMeeting
8. Todo

### Functionalitati
1. O agenda (**Agenda**) are urmatoarele functionalitati:
    * permite salvarea mai multor contacte (**contacts**, clasa **Contact**), retinute in ordine alfabetica
    * are o lista To Do (**todoList**) unde se pot adauga sarcini ce pot fi bifate/debifate (clasa **Todo**)
    * retine evenimente (**schedule**) ce au asociate o data (**ScheduledTask**) si intalniri ce au in plus asociate si un contact (**ScheduledTaskMeeting**), salvate in ordine cronologica
    * poate retine notite (**notes**, clasa **Note**)
    * are o sectiune privata, blocata cu o parola (**privacy**, clasa **Secure**) ce contine un jurnal.
    * Aceasta clasa este cea mai complexa din sistem, voi parcurge pe scurt metodele:
        * **add\[Contact|Todo|ScheduledTask|Meeting|Note\]\(...\)**
        * **get\[Contact|ScheduledTask|Note\]\(...)** - extragere dupa un anumit criteriu
        * **checkTodo/uncheckTodo(int idx)** - bifare/debifare sarcina To Do dupa indice
        * **delete\[Contact|Todo|ScheduledTask|Note\]\(...\)** - stergere dupa un anumit criteriu
        * Functii de listare pentru Contacte, To Do, Evenimente, Notite
        * In cazul evenimentelor cu data, se pot extrage subsectiuni: **printScheduledTasksBefore/After/Interval(...)** pentru a afisa doar evenimentele inainte de o data/dupa o data/intre 2 date.
        * **getSecure()** - extrage sectiunea privata

2. **AgendaService** contine o lista de agende, aceasta are cateva functii de baza pentru adaugare, extragere si afisare.
3. Un contact (**Contact**) are 6 campuri: nume, prenume, si cate 2 campuri pentru email si numar de telefon. Contine setteri si getteri pentru fiecare camp, precum si implementarea interfetei **Comparator<>** prin compararea lexicografica pentru mentinerea in ordine.
4. O notita (**Note**) este un obiect ce are un titlu si text, initial gol.
5. Sectiunea privata (**Privacy**) are o parola* ce permite deblocarea sectiunii pentru a accesa campul `journal`.<br>
Functia **requestAccess()** cere tastarea parolei din nou si in cazul in care este egala cu parola setata, intoarce **true**, in caz contrar, afiseaza un mesaj si intoarce **false**<br> 
Functiile **lock()** si **unlock()** blocheaza, respectiv deblocheaza accesul la campul `journal`.
\*Nu am putut implementa introducerea de la tastatura cu mascare a tastelor, deoarece este nevoie de o consola dedicata, nu cea din IDE.
6. **ScheduledTask** contine numele sarcinii si data asociata acelei sarcini, de asemenea implementeaza interfata **Comparator<>** prin compararea datelor.
7. **ScheduledTaskMeeting** extinde clasa **ScheduledTask**, adaugand un camp `partner` de tip **Contact** pentru a fi contactata rapid.
8. O sarcina To Do (**Todo**) are un nume si un flag ce semnaleaza daca sarcina este finalizata sau nu.
