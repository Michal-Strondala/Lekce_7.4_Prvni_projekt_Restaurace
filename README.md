# Restaurace
První projekt pro Engeto Academy Java kurz - Projekt Restaurace
#
Cíl projektu: Model pro objednávky v restauraci
Připrav třídy pro realizaci datového modelu sledování objednávek v restauraci. Vytvořený kód bude součástí backendu aplikace pro terminály, které ovládají číšníci a kuchaři.

Celý systém má kromě evidence čekajících a vyřízených objednávek poskytovat i informace pro účetnictví a podklady pro hledání optimalizací a slabých míst v přípravě a vydávání jídel.

Úkol: Vytvořit datový model a zpracovat základní statistické metody.

# Zásobník receptů
Kuchaři mají připraven zásobník jídel (dish + cook book). U každého jídla/receptu ukládáme název (title), cenu (price), přibližnou dobu přípravy (preparation time) v minutách a URL odkaz na fotografii (image) či několik fotografií. Fotografie samotná je na serveru, její ukládání tedy neřešíš. Ulož pouze název souboru bez přípony — například bolonske-spagety-01. (Všechny obrázky jsou ve formátu .PNG, ani příponu tedy nemusíme ukládat.). U jednoho jídla může být více fotografií, ale jedna z nich je vždy hlavní.

Kuchaři mají možnost některá jídla ze zásobníku vyřadit, přidat, nebo upravit. Má také jít přidat nebo odebrat fotografie, vždy by ale alespoň jedna měla zůstat. V systému kvůli budoucímu zobrazování nesmí být jídlo/recept bez fotografie, ale na serveru je speciální fotografie s názvem blank, kterou použij jako výchozí pro recepty, které zatím fotografii nemají.

Jídla v zásobníku lze zařadit do kategorie (category), podle kterých je pak může front-end systému řadit. (Front-end programují kolegové z webového týmu, nemusíme se tedy o něj starat. My pouze uložíme informaci o tom, do které kategorie jídlo patří.)

# Aktuální menu
Do menu (menu) zařazujeme jídla ze zásobníku receptů. Počet není omezen, ale bude nejspíš mnohem nižší, než je počet jídel v zásobníku. Kuchař má možnost všechna jídla z menu vymazat naráz.

Objednávat jdou pouze jídla, která jsou v aktuálním menu — ostatní jídla ze zásobníku objednat nelze.

# Objednávky (orders)
Restaurace má očíslované stoly (table). Objednávky se vztahují vždy ke konkrétnímu stolu — resp. k zákazníkům, kteří u stolu sedí. U každé objednávky sledujeme, kdy byla zadána, co jsme objednali, který číšník (waiter) ji zadal a kdy byla vyřízena (orderedTime a fulfilmentTime).

Fungování restaurace znáš: číšník (waiter) do tabletu zadá objednaná jídla a pití a poté je (někdy zaráz a někdy postupně) přináší. Lidé si ale mezitím můžou objednávat další položky.

Ke konkrétnímu zákazníkovi (stolu) lze připojit poznámku. Jakmile zákazník odchází, poznámka pozbývá na platnosti, ale uchová se pro zpětné zpracování. V poznámce například může být uvedeno, s čím byli hosté u tohoto stolu obzvláště spokojeni či naopak nespokojeni.

# Ukládání dat
Údaje ukládej do textového souboru či textových souborů. (Není nutné, aby vše bylo v jednom souboru.) Celá evidence by měla jít uložit a následně obnovit do stejného stavu. (Zásobník receptů, aktuální menu, objednávky a jejich stav.)

Připrav metody pro načtení a pro uložení celé evidence do souboru/souborů. Pokud soubory dosud neexistují, aplikace by měla nastartovat a běžet, jen zatím nebudou zadaná k dispozici žádná jídla, objednávky atd. #Ošetření chybových stavů

V případě, že jsou soubory poškozeny, aplikace se musí zachovat korektně (nesmí havarovat) a musí vhodně zareportovat chybu tak, aby bylo možné opravit poškozená místa. Stačí reportovat jedno poškozené místo — v případě jakékoli chyby aplikace nemusí pokračovat v načítání souborů.

Chybové stavy deleguj do třídy Main, kde vypíšeš chybové hlášení na chybový výstup.

# Požadované výstupy
Management restaurace chce mít kdykoli k dispozici informace a možnosti vyjmenované dále. Připrav třídu RestaurantManager s metodami pro získání těchto informací.

Požadované informace:

1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
2. Možnost seřadit objednávky podle číšníka nebo času zadání.
3. Celkovou cenu objednávek pro jednotlivé číšníky (u každého číšníka bude počet jeho zadaných objednávek).
4. Průměrnou dobu zpracování objednávek, které byly zadány v určitém časovém období.
5. Seznam jídel, které byly dnes objednány. Bez ohledu na to, kolikrát byly objednány.
6. Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku):
   #
1. Kofola velká 4x (130 Kč): 15:25-15:29 číšník č. 3
2. Pizza Grande (130 Kč): 15:29-16:10 číšník č. 4
3. Nanuk Míša (30 Kč): 17:12-17:18 číšník č. 2

# TESTOVACÍ SCÉNÁŘ
Připrav testovací scénář a zapiš do třídy Main kód tohoto scénáře.

Pro každý úkol připrav jednu metodu. Pokud lze daný úkol vyřešit na max. třech řádcích, nemusíš vytvářet metodu a můžeš kód přímo zapsat do metody main.

V metodě main pak zavolej jednotlivé metody a u každé uveď v komentáři číslo úkolu, který řeší. Úkoly spouštěj v pořadí, jak jsou uvedeny:

1. Načti stav evidence z disku. Pokud se aplikace spouští poprvé a soubory neexistují, budou veškeré seznamy a repertoár zatím prázdné. (Aplikace nepotřebuje předvytvořené žádné soubory.)

2. Připrav testovací data. Vlož do systému:

 tři jídla:

1. Kuřecí řízek obalovaný 150 g
2. Hranolky 150 g
3. Pstruh na víně 200 g
   
 - První a třetí jídlo zařaď do aktuálního menu, druhé jídlo nikoli. Případné další můžeš zařadit dle potřeby.

 - Vytvoř alespoň tři objednávky pro stůj číslo 15 a jednu pro stůj číslo 2. Objednávky řeší alespoň dva různí číšníci.

 - Min. dvě objednávky jsou již uzavřené, minimálně dvě ještě nikoli.

3. Vyzkoušej přidat objednávku jídla, které není v menu — aplikace musí ohlásit chybu.

4. Proveď uzavření objednávky.

5. Použij všechny připravené metody pro získání informací pro management — údaje vypisuj na obrazovku. Pokud potřebuješ, přidej si do systému další informace (jídla, číšníky, objednávky atd.).

6. Změněná data ulož na disk. Po spuštění aplikace musí být data opět v pořádku načtena.
#

Načtení neplatného souboru:

7. Připrav do složky projektu poškozený vstupní soubor/poškozené vstupní soubory, které se nepodaří načíst. Aplikace se při spuštění s těmito soubory musí zachovat korektně — nesmí spadnout..
   
8. Pokud tyto soubory posléze smažeme, aplikace musí fungovat a můžeme pokračovat v testování.
