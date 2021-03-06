# You can do it!

![](https://media.giphy.com/media/XreQmk7ETCak0/giphy.gif)

# Nätverksprogrammering sammanfattning


## Mönster av teorin som kommer på del 1:

### Vad tillkom i java.nio paketet?

När java.nio tillkom så förändrades modellen för hur man läser/skriver till en socket en del. Det som förändrades var att man med det nya paketet kunde använda sig av buffertar (**BufferedInputStream, BufferedOutputStream**) jämför med (**InputStream, OutputStream**). Vid sändning av data så fyller man data i en buffert innan man skickar iväg datan, vid mottagning så begär man att fylla en buffert som man sedan läser ifrån. 

***Sidenote:*** *Anvädning av buffertar är mer effektivt, tänk ett exempel där man ska skicka iväg 100 byte av data. Om man skickar en byte i taget där man måste lägga till (e.g. 40 bytes) header data vilket motsvarar 41 bytes för varje byte av data 41 bytes * 100 = 4100 bytes. Om man istället buffrar 100 byte av data och sedan skickar iväg dom kommer man istället skicka 140 byte data.*

**Fördelar**: En betydande fördel med att använda den nya paketet är att med *.nio* så kan en enda singel tråd hantera flera förbindelser (strömmar).

**Selector:** Ett objekt av klassen selector används för att kunna registrera specifika förbindelser som man vill bevaka. När man anropar **select** på objektet så blockeras den körande tråden fram till att någon utav de förbinelserna man bevakar är tillängliga för I/O operationer.

### Mutual Exclusion

Mutual Exclusion innebär att endast en tråd åt gången kan befinna sig i en och samma **kritiska region**. Dvs det går ej att ha två trådar exekverandes i samma region vilket manipulerar data samtidigt, om en tråd är inne i den kritiska regionen så får den andra tråden vänta till att den första blir klar med sin exekvering och lämnar regionen.

### Busy-wait

Busy wait är när en tråd ligger och upprepandes testar ett villkor i en loop vilket tar processor tid som kunde användas åt andra trådar. 

    while(ItsNotMyTurn){
    // Kör denna loop fram tills att det blir min tur.
    }
    
Detta kan man lösa genom att använda sig utav wait() och notify(). Man har ofta ett monitor objekt där tråden anropar en metod på som är blockerande fram till att villkoret är uppfyllt, villkoret kan då ändras genom att t.ex. en annan tråd ändrar villkoret och anropar notifyAll();.

    // Tråden anropar
    monitor.IsItMyTurn(); // blockerande anrop
    
    // Metod inne i monitorn
    public sunchronized void IsItMyTurn(){
    while(!hisTurn){
        try{ wait();
        } catch(...){
        }
    }
    // Annan tråd som ändrar villkoret och väcker tråden
    public synchronized void changeTurn(){
        hisTurn = true;
        notifyAll();
    }

### Protokoll:

För att två program som kommunicerar över ett nätverk ska kunna förstå varandras meddelanden så behövs det ett gemensamt protokoll, man kan se detta som ett gemensamt (fördefinerat) språk.

HTTP - Är ett typiskt exempel på ett gemensamt protokoll.

### Transaktioner

Transaktioner är operationer/meddelanden som överförs mellan klient och server, typiskt skickas transaktioner från klient till server för att få servern att utföra en specifik operation.

Exempel: 
Klient skickar en begäran till servern.
Servern svarar genom att utföra specifik operation.

### Fysiskt överföringsmedium

Applikationer behöver ett medium för att kunna överföra data mellan varandra.
- H2H (Host to Host) - Omvandlar analoga och digitala signaler.

### Transportlagret

Tänk på att TCP och UDP ligger på transportlagret och det är transportlagret som ser till så att vi kan skicka bytes mellan två olika noder. Transportlagret ser till att data (i form av bytes) skickas till rätt applikation genom att hantera data till rätt port. Transportlagret gör ingen tolkning av datan utan det är sedan applikationslagret som vet hur datan faktiskt skall hanteras.

## URL Connections / Sockets

Dessa två kan ses som väldigt lika, båda erbjuder Input/Output strömmar. 
Nedan beskrivs användningsområden.

### URL Connections

Om vi t.ex. vill ansluta till en hemsida och därefter hämta data så som headers eller specifika fält,
då används URL connections där vi skapar en URL anslutning.  Sedan finns det metoder tillängliga för att enkelt hämta t.ex. sidans header.

### Sockets

Används då vi t.ex. vill skapa en förbindelse mellan en klient och server där vi själva definierar ett protokoll.
Vi skapar en Socket på client sidan och en ServerSocket på serversidan där kommunikation sker via Input respektive OutputStreams.

Skapa en Client Socket:
    
    Socket s = new Socket("ip address", "port");
    
Skapa en Server Socket:

    ServerSocket s = new ServerSocket("port");
    
***Kom ihåg***
- Tumregeln är att använda port 1337 :) (1-1023) är reserverade för standarprotokoll.

### UDP (User Datagram Protocol)

- Upp till 65507 byte stora datagram.
- UDP Garanterar korrekta meddelanden.
- UDP Garanter **INTE** leverans av meddelanden.
- UDP Garanterar **INTE** inbördes ordning av meddelanden.

### TCP (Transmission Control Protocol)

- Är en fast uppkoppling över nätet.
- Data skickas via strömmar -> Data behöver ej delas upp i paket.
- TCP sköter automatisk felkontroll/omsändning.
- TCP Garanterar leverans av meddelande.
- TCP Garanterar inbördes ordning.

### Nätverksportar

- (1-1023) - Är reserverade för standardtjänster.
- (1024-65535) - Är tillängliga för applikationer att använda.

### RFC (Request for comments)

Här definieras protokoll standarder för internet, dessa är definierade av IETF (Interenet Enginnering Task Force).

### Strömmar
- Bytes kan skrivas till strömmar.
- Bytes kan läsas från strömmar.
- En fil är en ström som lagrats i sekundärminnet.

Exempel på att läsa in 10 bytes från en InputStream:


    byte[] input = new byte[10];
    for(int i =0; i < input.length(); i++){
        int b = in.read();
        if(b == -1) break;
        input[i] = (byte) b;
    }
    
Att läsa in endast en byte i taget är ineffektivt, man brukar istället läsa in flera bytes genom att skapa en byte vektor som skickas till metoden read(vector[]). Funktionen returnerar hur många bytes som faktiskt skrevs till vektorn. 


    //read 100 bytes
    byte[] inputData = new byte[1024];
    int bytesRead = 0;
    int bytesToRead = 0;
    while(bytesRead < bytesToRead){
        int result = read(inputData,dataRead,inputData.length() - dataRead);
        if(result == -1) break;
        dataRead += result;
    }
    
read() returnerar -1 om det inte finns mer data att hämta.
Villkoret dataRead < 100 är till för att begränsa inläsning av data till 100 bytes.

#### InputStream och OutputStream
Dessa två är högst i kedjan där varje subklass till dessa bidrar till mer funktionalitet. T.ex. så har vi BufferedInputStream som bidrar till bättre prestanda vid inläsning från fil för att vi kan buffra data.

Exempel på hur man skapar en subbklass av dessa streams:

    InputStream is = ...;
    // Adds functionality to inputstream.
    BufferedInputStream bis = new BufferedInputStream(is);
    
    OutputStream os = ...;
    // Adds functionality to inputstream.
    BufferedOutputStream bos = new BufferedOutputStream(is);
    
Glöm ej att använda flush() vid anvädning av BufferedOutputStream, detta pga att buffrar kan fastna i ett läge där de väntar på att bli fulla innan data skickas iväg. För att garantera att data faktiskt skickas så anropar man flush() efter write().
    
    // using flush
    bos.write(data);
    bos.flush();


#### Reader / Writers

Dessa används när man vill ha en teckenström istället för en byte ström som i vanliga streams. 
T.ex. så har vi InputStreamReader som returnerar en karaktär istället för en byte om man anropar på reader() funktionen.

    // Create InputStreamReader
    Socket s = new Socket(ip,port);
    InputStream is = s.getInputStream();
    is = new InputStreamReader(is);
  
    
Exempel på inläsning av 100 bytes med InputStreamReader

    // Same as previously with bytes, just now with characters.
    char[] characters = new char[1024];
    int dataRead = 0;
    while(dataRead != -1 && dataRead < 100){
        dataRead = reader.is(characters,dataRead, characters.length - dataRead);
    }
    
Exempel på inläsning av en rad med BuffereReader

    // Create bufferedReader
    Socket s = new Socket(ip,port);
    InputStream is = s.getInputStream();
    Reader r = new InputStreamReader(is);
    String oneLine = r.getLine();
    
### Sockets

Sockets möjliggör anslutning mellan klient och program. 

Via sockets kan man ansluta input / outputstreams med varandra för inläsning av data.

InputStream på klienten motsvarar OutputStream hos servern och vice versa.

#### VIKTIGT 
Glöm ej att stänga öppna sockets.

### Unicast / Multicast

Är relaterat till UDP datagram och när man strömmar media, t.ex. film eller live-tv.

#### Unicast

Här skickas kopior av samma meddelande till olika mottagare med unika datagram.
Tänk ett exempel på tre användare inom samma nätverk som streamar en fotbollsmatch från samma server. 
Vid Unicast så kommer servern att skapa tre stycken olika datagram som skickas till nätverket där användarna befinner sig på, sedan kommer routern att skicka rätt varje datagram.


#### Multicast
Här används ett meddelande för att nå ut till flera mottagare, detta görs via stöd av routrar genom att man t.ex. skickar till en router som sedan skickar vidare detta meddelande till flera olika mottagare. 

Tänk exemplet tidigare, om routern stödjer multicast så hade servern skickat ett datagram till routern som sedan hade vidarebefodrat samma datagram till alla på nätverket som faktiskt streamar.

Addresser som används för multicast är 224.0.0.0/4, dvs det är de första fyra bitarna som är reserverade och därmed kan en multicast adress utsträcka sig mellan (224.0.0.0 - 239.255.255.255).


#### TTL (Time To Live)

Detta är ett headerfält i IP-datagrammen som meddelar hur många routrar ett IP paket får besöka innan det når mottagaren. Om antalet router-hop överstiger TTL fältet innan paketet kommit fram så "dör" paketet, dvs den skickas inte vidare. Detta används till att minska nätverkstrafik.

### Java server pages

- .jps filer är html filer med extra element inuti.
- jsp elementen anger vart dynamisk kod skall infogas.

Beskrivning av processen som sker när en klient efterfrågar en .jsp fil från en server:

**OBS**: Om det är första gången filen /Hello.jps efterfrågas så sker processen 1-5, annars hoppar man över steg 2 och 3.
1. Klienten skickar GET /Hello.jsp
2. Servern läser in filen Hello.jsp
3. Servern kompilerar en Servlet från .jsp filen, HelloServlet.java och exekverar denna.
4. Servern anropar service() som skapar HTML kod från servlet.
5. Denna HTML returneras till klienten.

### Parsing HTML

Det finns två stycken html parsing kit som nämnts i kursen: 
- HTMLEditorKit
- Jsoup.

#### HTMLEditorkit:
- **Advantage:**  Is a part of java, so its a standard library.
- **Disadvantage:** Has limited parsing capabilities, has limited or no documentation available.

#### Jsoup:
- **Advantage:**  Is easy to use and has high parsing capabilities, is well-documented.
- **Disadvantage:** Is an external library which has to be imported.

#### HTML

Från HTML är det möjligt att bygga upp en trädstruktur där rooten i trädet består av HTML och alla noder till trädet är element.

    <html>
        <head>
        </head>
        
        <body>
        </body>
    </html>
    
Här har vi rooten html, följt av två noder head och body.

### Enkel beskrivning på klient-server arkitetur för strömmande media

#### Server:
Servern kan skicka ljud eller bild med en fördefinierad kodning, t.ex. MPEG med en hastighet på 25 bilder / sek och en upplösning på (1280x720).

#### Client:
Klienten tar emot mediaströmmen, avkodar denna med den fördefinierade kodningen och spelar sedan upp den. För att minska effekten av jitter så buffrar klienten en viss mängd data som lagras i en buffert innan uppspelning.


#### UDP

UDP är snabbt men har inget inbyggt stöd för felhantering och brukar ha problem med blockerande brandväggar.

Med UDP paket så tillkommer information om:
- Sekvensnummer
- Tidsstämplar
- Mediatyp

UDP anses som ett okopplat för att det inte finns en förbindelse mellan två kommunikationsnoder,  varje meddelande som skickas mellan noderna är helt oberoende och därför måste destinationadressen anges explicit för varje unikt meddelande.

#### TCP 
Har inbyggt stöd för felhantering och att segmenten kommer i rätt ordning.

TCP anses som kopplat för att förbindelsen mellan två kommunikationsnoder är "fast", detta gör att man inte explicit måste ange destinationen för varje meddelande


#### RTP

Realtime transport protokoll - Stödjer mediaöverföring och bygger på UDP.
Dock behövs det stöd från applikatosprogrammet för att hantera så att paket kommer i rätt ordning,jitter samt hantering av paket förluster.

#### HTTP

Finns ett http protkoll som är baserat på TCP som är populärt. 
- DASH (Dynamic Adaptive Streaming over HTTP)
- apple HTTP live Streaming

### java.nio

I java 1.4 så tillkom det nya paket som erbjöd klasserna: SocketChannel, ServerSocketChannel, Selector, med flera.

Dessa möjliggör det för t.ex. en server att endast ha en tråd för att bevaka inkommande trafik på flera uppkopplingar.

#### Selector:
Denna klass används för att registrera ett antal sockets som man vill bevaka. Genom att anropa select() på objektet så kan man sedan blockera fram tills att det går att utföra någon typ av I/O operation på de bevakade uppkopplingarna.
    
### Executors

Exempel: Skapa en tråd pool med max antalet parallela trådar till (3), starta en given tråd klass som implementerar Runnable.

    ExecutorService pool = Executors.newFixedThreadPool(3);
    pool.submit(new Thread(new Runnable()));
    
Om man vill ha en tråd som returnerar ett värde ska klassen istället implementera Callable< value > och överlagra metoden call() istället för run().

Exempel:

    // Thread class
    public class myCallable implements Callable<String>{
    
         public String call(){
            return "hejsan";
        }
        
    }
    
    // Main class
    public class main{
        public static void main (String[]args){
            ExecutorService pool = Executors.newFixedThreadPool(3);
            Future<String> temp = pool.submit(new Thread(new Runnable()));
            // Temp stores the future, however get() method is 
            // blocking until the value is retrieved.
            System.out.print("
            Call temp.get(), this method is blocking until a  value is returned"
            +temp.get());
        }
    }
    
### Streaming TCP/UDP/HTTP

För att streama i realtid kan man använda sig utav antingen UDP eller TCP, UDP brukar vara snabbare men dock är det många brandväggar som blockerar UDP och därför är det det mindre vanligt.

HTTP streaming är mycket vanligt då HTTP kommer förbi brandväggar.
Exempel på HTTP streaming är: 
- DASH
- HTTPLiveStreaming
    
### RTP (Realtime Transport Protocol)

Är ett protokoll som är avsett för överföring av ljud och bild i realtid, dock garanterar protokollet inte realtidsöverföring. Protokollet är paketbaserat och innehåller indetifikation av:
- Mediatyp
- Tidsstämplar
- Sekvensnummer

Kräver dock ett tillägg från applikationslagret (nämnt tidigare) för att kunna hantera paket som ej kommer i rätt ordning, jitter samt kompensation av paketförluster.

RTP grundar sig på UDP.

### RTCP (Realtime Control Protocol)
RTCP är en del av RTP protokollet och används för att förmedla statistik över förbindelsen mellan användare och mottagare, statistik som erhålls är:

- Jitter.
- Antal Paketförluter.
- Antal sända paket.

### RTSP (Realtime Streaming protokoll)

Är ett HTTP liknande protokoll som används för att kunna kontrollera uppspelning av strömmande media likt en fjärkontroll, exempel: start, stop och pausa.


### DASH (Dynamic Adaptive Streaming over HTTP)

DASH är bra på att anpassa sig efter nätverket beroende på om man använder sig av Wi-Fi, mobilnät, fiber etc.
Servern brukar ha flera olika versioner av median som ska överföras och kan därför dynamiskt anpassa sig till bandbredden och väljer därefter version av media som beror på hur hög överföringshastigheten är.

